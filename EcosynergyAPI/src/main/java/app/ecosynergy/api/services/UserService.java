package app.ecosynergy.api.services;

import app.ecosynergy.api.controllers.UserController;
import app.ecosynergy.api.data.vo.v1.UserVO;
import app.ecosynergy.api.exceptions.InvalidUserDataException;
import app.ecosynergy.api.exceptions.RequiredObjectIsNullException;
import app.ecosynergy.api.exceptions.ResourceAlreadyExistsException;
import app.ecosynergy.api.exceptions.ResourceNotFoundException;
import app.ecosynergy.api.mapper.DozerMapper;
import app.ecosynergy.api.models.User;
import app.ecosynergy.api.repositories.NotificationPreferenceRepository;
import app.ecosynergy.api.repositories.UserRepository;
import app.ecosynergy.api.security.jwt.JwtTokenProvider;
import app.ecosynergy.api.util.PasswordUtils;
import app.ecosynergy.api.util.UserPreferenceUtils;
import app.ecosynergy.api.util.ValidationUtils;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class UserService implements UserDetailsService {
    private static final Logger logger = Logger.getLogger(UserService.class.getName());
    private final UserRepository repository;
    private final NotificationPreferenceRepository notificationPreferenceRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final HttpServletRequest request;
    private final PagedResourcesAssembler<UserVO> assembler;

    @Autowired
    public UserService(UserRepository repository, NotificationPreferenceRepository notificationPreferenceRepository, @Lazy JwtTokenProvider jwtTokenProvider, HttpServletRequest request, PagedResourcesAssembler<UserVO> assembler) {
        this.repository = repository;
        this.notificationPreferenceRepository = notificationPreferenceRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.request = request;
        this.assembler = assembler;
    }

    public PagedModel<EntityModel<UserVO>> findAll(Pageable pageable) {
        logger.info("Finding all Users!");

        Page<User> userPage = repository.findAll(pageable);
        Page<UserVO> voPage = userPage.map(u -> DozerMapper.parseObject(u, UserVO.class));

        voPage.map(user -> {
            try {
                return user.add(linkTo(methodOn(UserController.class).findById(user.getKey())).withSelfRel());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        Link link = linkTo(methodOn(UserController.class)
                .findAll(pageable.getPageNumber(),
                        pageable.getPageSize(),
                        pageable.getSort().toString()
                ))
                .withSelfRel();

        return assembler.toModel(voPage, link);
    }

    public UserVO findById(Long id) {
        if (id == null) throw new RequiredObjectIsNullException();

        logger.info("Finding User by Id!");

        User entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with given ID: " + id));

        UserVO vo = DozerMapper.parseObject(entity, UserVO.class);
        vo.add(linkTo(methodOn(UserController.class).findById(id)).withSelfRel());
        return vo;
    }

    public UserVO findByEmail(String email) {
        if (email == null) throw new RequiredObjectIsNullException();

        logger.info("Finding User by Email!");

        User entity = repository.findByEmail(email);

        if (entity == null) throw new ResourceNotFoundException("User not found with given E-mail: " + email);

        UserVO vo = DozerMapper.parseObject(entity, UserVO.class);
        vo.add(linkTo(methodOn(UserController.class).findById(vo.getKey())).withSelfRel());

        return vo;
    }

    public UserVO findByUsername(String username) {
        if (username == null) throw new RequiredObjectIsNullException();

        logger.info("Finding User by Username!");

        User entity = loadUserByUsername(username);

        UserVO vo = DozerMapper.parseObject(entity, UserVO.class);
        vo.add(linkTo(methodOn(UserController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public User getCurrentUser() {
        String token = jwtTokenProvider.resolveToken(request);

        DecodedJWT decodedJWT = jwtTokenProvider.decodedToken(token);

        return loadUserByUsername(decodedJWT.getSubject());
    }

    public List<UserVO> findByIdentifierContaining(String identifier) {
        if (identifier == null) throw new RequiredObjectIsNullException();

        logger.info("Searching Users by Identifier (username or email)!");

        List<User> entities = repository.findByIdentifierContaining(identifier);

        return entities.stream().map(entity -> {
            UserVO userVO = DozerMapper.parseObject(entity, UserVO.class);
            userVO.add(linkTo(methodOn(UserController.class).findById(userVO.getKey())).withSelfRel());
            return userVO;
        }).toList();
    }

    @Transactional
    public UserVO create(UserVO user) {
        if (user == null) throw new RequiredObjectIsNullException();

        boolean isAlreadyExists = repository.existsByUserName(user.getUserName());

        if (isAlreadyExists)
            throw new ResourceAlreadyExistsException("User with username '" + user.getUserName() + "' already exists");

        isAlreadyExists = repository.existsByEmail(user.getEmail());

        if (isAlreadyExists)
            throw new ResourceAlreadyExistsException("User with email '" + user.getEmail() + "' already exists");

        User entity = DozerMapper.parseObject(user, User.class);

        logger.info("Creating user!");

        entity = repository.save(entity);

        notificationPreferenceRepository.saveAll(UserPreferenceUtils.createDefaultPreferences(entity));

        UserVO vo = DozerMapper.parseObject(entity, UserVO.class);
        vo.add(linkTo(methodOn(UserController.class).findById(vo.getKey())).withSelfRel());

        return vo;
    }

    public UserVO update(Long id, UserVO user) {
        if (user == null) throw new RequiredObjectIsNullException();

        User entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with the given ID: " + id));

        if(!ValidationUtils.isValidEmail(user.getEmail())) {
            throw new InvalidUserDataException("Invalid email format");
        }

        if(!ValidationUtils.isFullNameValid(user.getFullName())) {
            throw new InvalidUserDataException("Full name contais special characters or digits");
        }

        boolean isAlreadyExists = repository.existsByUserName(user.getUserName());

        if (isAlreadyExists && !Objects.equals(entity.getUserName(), user.getUserName()))
            throw new ResourceAlreadyExistsException("The username: '" + user.getUserName() + "' is already in use");

        isAlreadyExists = repository.existsByEmail(user.getEmail());

        if (isAlreadyExists && !Objects.equals(entity.getEmail(), user.getEmail()))
            throw new ResourceAlreadyExistsException("The email: '" + user.getEmail() + "' is already in use");

        entity.setUserName(user.getUserName() != null ? user.getUserName().toLowerCase(Locale.ROOT) : entity.getUserName());
        entity.setFullName(user.getFullName() != null ? ValidationUtils.formatFullName(user.getFullName()) : entity.getFullName());
        entity.setEmail(user.getEmail() != null ? user.getEmail().toLowerCase() : entity.getEmail());
        entity.setGender(user.getGender() != null ? user.getGender() : entity.getGender());
        entity.setNationality(user.getNationality() != null ? user.getNationality() : entity.getNationality());

        logger.info("Updating user!");

        UserVO vo = DozerMapper.parseObject(repository.save(entity), UserVO.class);
        vo.add(linkTo(methodOn(UserController.class).findById(vo.getKey())).withSelfRel());

        return vo;
    }

    public void delete(Long id) {
        if (id == null) throw new RequiredObjectIsNullException();

        User entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(""));

        logger.info("Deleting user: " + entity.getId());

        repository.delete(entity);
    }

    public UserVO resetPassword(UserVO user) {
        if (user.getPassword() == null) throw new RequiredObjectIsNullException();

        logger.info("Recovering password");

        User entity = getCurrentUser();

        entity.setPassword(PasswordUtils.passwordEncode(user.getPassword()));

        UserVO vo = DozerMapper.parseObject(repository.save(entity), UserVO.class);

        vo.add(linkTo(methodOn(UserController.class).findById(vo.getKey())).withSelfRel());

        return vo;
    }

    public boolean existsByUsername(String username) {
        if (username == null) throw new RequiredObjectIsNullException();

        return repository.existsByUserName(username);
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Finding one User by name " + username + "!");

        User user = repository.findByUsername(username);

        if (user != null) {
            return user;
        } else {
            throw new UsernameNotFoundException("Username " + username + " not found!");
        }
    }
}
