package app.ecosynergy.api.unittests.mockito.services;

import app.ecosynergy.api.data.vo.v1.UserVO;
import app.ecosynergy.api.exceptions.RequiredObjectIsNullException;
import app.ecosynergy.api.models.User;
import app.ecosynergy.api.repositories.UserRepository;
import app.ecosynergy.api.services.UserServices;
import app.ecosynergy.api.unittests.mapper.mocks.MockUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class UserServicesTest {
    MockUser input;

    @InjectMocks
    private UserServices service;

    @Mock
    UserRepository repository;

    @BeforeEach
    void setUpMocks() {
        input = new MockUser();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById() {
        User entity = input.mockEntity(1);

        when(repository.findById(entity.getId())).thenReturn(Optional.of(entity));
        UserVO result = service.findById(entity.getId());

        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getUserName());
        assertNotNull(result.getFullName());
        assertNotNull(result.getEmail());
        assertNotNull(result.getPassword());
        assertNotNull(result.getGender());
        assertNotNull(result.getNationality());
        assertNotNull(result.getLinks());

        assertTrue(result.toString().contains("links: [</api/user/v1/findId/1>;rel=\"self\"]"));
        assertEquals("user1", result.getUserName());
        assertEquals("User1", result.getFullName());
        assertEquals("Email1", result.getEmail());
        assertEquals("Password1", result.getPassword());
        assertEquals("Female", result.getGender());
        assertEquals("Brazilian1", result.getNationality());

        assertTrue(result.getEnabled());
        assertTrue(result.getAccountNonExpired());
        assertTrue(result.getAccountNonLocked());
        assertTrue(result.getCredentialsNonExpired());
    }

    @Test
    void findByUsername() {
        User entity = input.mockEntity(1);

        when(repository.findByUsername(entity.getUserName())).thenReturn(entity);
        UserVO result = service.findByUsername(entity.getUsername());

        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getUserName());
        assertNotNull(result.getFullName());
        assertNotNull(result.getEmail());
        assertNotNull(result.getPassword());
        assertNotNull(result.getGender());
        assertNotNull(result.getNationality());
        assertNotNull(result.getLinks());

        assertTrue(result.toString().contains("links: [</api/user/v1/findId/1>;rel=\"self\"]"));
        assertEquals("user1", result.getUserName());
        assertEquals("User1", result.getFullName());
        assertEquals("Email1", result.getEmail());
        assertEquals("Password1", result.getPassword());
        assertEquals("Female", result.getGender());
        assertEquals("Brazilian1", result.getNationality());

        assertTrue(result.getEnabled());
        assertTrue(result.getAccountNonExpired());
        assertTrue(result.getAccountNonLocked());
        assertTrue(result.getCredentialsNonExpired());
    }

    @Test
    void create() {
        User entity = input.mockEntity(1);

        UserVO vo = input.mockUserVO(1);

        when(repository.save(any(User.class))).thenReturn(entity);

        UserVO result = service.create(vo);

        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getUserName());
        assertNotNull(result.getFullName());
        assertNotNull(result.getEmail());
        assertNotNull(result.getPassword());
        assertNotNull(result.getGender());
        assertNotNull(result.getNationality());
        assertNotNull(result.getLinks());

        assertTrue(result.toString().contains("links: [</api/user/v1/findId/1>;rel=\"self\"]"));
        assertEquals("user1", result.getUserName());
        assertEquals("User1", result.getFullName());
        assertEquals("Email1", result.getEmail());
        assertEquals("Password1", result.getPassword());
        assertEquals("Female", result.getGender());
        assertEquals("Brazilian1", result.getNationality());

        assertTrue(result.getEnabled());
        assertTrue(result.getAccountNonExpired());
        assertTrue(result.getAccountNonLocked());
        assertTrue(result.getCredentialsNonExpired());
    }

    @Test
    void createWithNullUser() {
        Exception exception= assertThrows(RequiredObjectIsNullException.class, () -> service.create(null));

        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void update(){
        User entity = input.mockEntity(1);

        UserVO vo = input.mockUserVO(1);

        when(repository.findById(entity.getId())).thenReturn(Optional.of(entity));
        when(repository.save(any(User.class))).thenReturn(entity);

        UserVO result = service.update(vo);

        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getUserName());
        assertNotNull(result.getFullName());
        assertNotNull(result.getEmail());
        assertNotNull(result.getPassword());
        assertNotNull(result.getGender());
        assertNotNull(result.getNationality());
        assertNotNull(result.getLinks());

        assertTrue(result.toString().contains("links: [</api/user/v1/findId/1>;rel=\"self\"]"));
        assertEquals("user1", result.getUserName());
        assertEquals("User1", result.getFullName());
        assertEquals("Email1", result.getEmail());
        assertEquals("Password1", result.getPassword());
        assertEquals("Female", result.getGender());
        assertEquals("Brazilian1", result.getNationality());

        assertTrue(result.getEnabled());
        assertTrue(result.getAccountNonExpired());
        assertTrue(result.getAccountNonLocked());
        assertTrue(result.getCredentialsNonExpired());
    }

    @Test
    void updateWithNullUser() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> service.update(null));

        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void delete() {
        User entity = input.mockEntity(0);

        when(repository.findById(entity.getId())).thenReturn(Optional.of(entity));
        service.delete(entity.getId());
        assertTrue(true);
    }
}