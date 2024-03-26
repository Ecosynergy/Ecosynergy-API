package app.ecosynergy.api.services;

import app.ecosynergy.api.controllers.UserController;
import app.ecosynergy.api.data.vo.v1.UserVO;
import app.ecosynergy.api.exceptions.RequiredObjectIsNullException;
import app.ecosynergy.api.exceptions.ResourceNotFoundException;
import app.ecosynergy.api.mapper.ModelMapper;
import app.ecosynergy.api.models.User;
import app.ecosynergy.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class UserServices {
    @Autowired
    UserRepository repository;

    private static final Logger logger = Logger.getLogger(UserServices.class.getName());

    public List<UserVO> findAll(){
        logger.info("Finding all Users!");

        List<UserVO> usersList = ModelMapper.parseListObject(repository.findAll(), UserVO.class);
        usersList.forEach(user -> {
            user.add(linkTo(methodOn(UserController.class).findById(user.getKey())).withSelfRel());
        });

        return usersList;
    }

    public UserVO findById(Long id){
        if(id == null) throw new RequiredObjectIsNullException();

        logger.info("Finding User by Id!");

        User entity = repository.findById(id)
                .orElseThrow();

        UserVO vo = ModelMapper.parseObject(entity, UserVO.class);
        vo.add(linkTo(methodOn(UserController.class).findById(id)).withSelfRel());
        return vo;
    }

    public UserVO create(UserVO user){
        if(user == null) throw new RequiredObjectIsNullException();

        User entity = ModelMapper.parseObject(user, User.class);

        logger.info("Creating user!");

        UserVO vo = ModelMapper.parseObject(repository.save(entity), UserVO.class);
        vo.add(linkTo(methodOn(UserController.class).findById(vo.getKey())).withSelfRel());

        return vo;
    }

    public UserVO update(UserVO user){
        if(user == null) throw new RequiredObjectIsNullException();

        User entity = repository.findById(user.getKey())
                .orElseThrow();
        entity.setFullName(user.getFullName());
        entity.setEmail(user.getEmail());
        entity.setGender(user.getGender());
        entity.setNationality(user.getNationality());

        logger.info("Updating user!");

        UserVO vo = ModelMapper.parseObject(repository.save(entity), UserVO.class);
        vo.add(linkTo(methodOn(UserController.class).findById(vo.getKey())).withSelfRel());

        return vo;
    }

    public void delete(Long id){
        if(id == null) throw new RequiredObjectIsNullException();

        User entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(""));

        logger.info("Deleting user: " + entity.getId());

        repository.delete(entity);
    }
}
