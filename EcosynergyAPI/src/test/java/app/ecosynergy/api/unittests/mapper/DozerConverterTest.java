package app.ecosynergy.api.unittests.mapper;

import app.ecosynergy.api.data.vo.v1.UserVO;
import app.ecosynergy.api.mapper.DozerMapper;
import app.ecosynergy.api.models.User;
import app.ecosynergy.api.unittests.mapper.mocks.MockUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DozerConverterTest {
    MockUser input;

    @BeforeEach
    public void setUp(){
        input = new MockUser();
    }

    @Test
    public void parseEntityToVOTest(){
        UserVO vo = DozerMapper.parseObject(input.mockEntity(), UserVO.class);

        assertNotNull(vo);
        assertNotNull(vo.getKey());
        assertNotNull(vo.getUserName());
        assertNotNull(vo.getFullName());
        assertNotNull(vo.getEmail());
        assertNotNull(vo.getPassword());
        assertNotNull(vo.getGender());
        assertNotNull(vo.getNationality());
        assertNotNull(vo.getTimeZone());

        assertEquals("user0", vo.getUserName());
        assertEquals("User0", vo.getFullName());
        assertEquals("Email0", vo.getEmail());
        assertEquals("Password0", vo.getPassword());
        assertEquals("Male", vo.getGender());
        assertEquals("Brazilian0", vo.getNationality());
        assertEquals(ZoneId.of("America/Sao_Paulo"), vo.getTimeZone());

        assertTrue(vo.getEnabled());
        assertTrue(vo.getAccountNonExpired());
        assertTrue(vo.getAccountNonLocked());
        assertTrue(vo.getCredentialsNonExpired());
    }

    @Test
    public void parseEntityListToVOList(){
        List<User> entityList = input.mockEntityList();

        List<UserVO> voList = DozerMapper.parseListObjects(entityList, UserVO.class);

        voList.forEach(vo -> {
            assertNotNull(vo);
            assertNotNull(vo.getKey());
            assertNotNull(vo.getUserName());
            assertNotNull(vo.getFullName());
            assertNotNull(vo.getEmail());
            assertNotNull(vo.getPassword());
            assertNotNull(vo.getGender());
            assertNotNull(vo.getNationality());
            assertNotNull(vo.getTimeZone());

            assertEquals("User" + vo.getKey(), vo.getFullName());
            assertEquals("Email" + vo.getKey(), vo.getEmail());
            assertEquals("Password" + vo.getKey(), vo.getPassword());
            assertEquals(vo.getKey() % 2 == 0 ? "Male" : "Female", vo.getGender());
            assertEquals("Brazilian" + vo.getKey(), vo.getNationality());
            assertEquals(ZoneId.of("America/Sao_Paulo"), vo.getTimeZone());

            assertTrue(vo.getEnabled());
            assertTrue(vo.getAccountNonExpired());
            assertTrue(vo.getAccountNonLocked());
            assertTrue(vo.getCredentialsNonExpired());
        });
    }

    @Test
    public void parseVOToEntity(){
        User entity = DozerMapper.parseObject(input.mockUserVO(), User.class);

        assertNotNull(entity);
        assertNotNull(entity.getId());
        assertNotNull(entity.getUserName());
        assertNotNull(entity.getFullName());
        assertNotNull(entity.getEmail());
        assertNotNull(entity.getPassword());
        assertNotNull(entity.getGender());
        assertNotNull(entity.getNationality());
        assertNotNull(entity.getTimeZone());

        assertEquals("user0", entity.getUserName());
        assertEquals("User0", entity.getFullName());
        assertEquals("Email0", entity.getEmail());
        assertEquals("Password0", entity.getPassword());
        assertEquals("Male", entity.getGender());
        assertEquals("Brazilian0", entity.getNationality());
        assertEquals(ZoneId.of("America/Sao_Paulo"), entity.getTimeZone());

        assertTrue(entity.getEnabled());
        assertTrue(entity.getAccountNonExpired());
        assertTrue(entity.getAccountNonLocked());
        assertTrue(entity.getCredentialsNonExpired());
    }

    @Test
    public void parseVOListToEntityList(){
        List<UserVO> voList = input.mockVOList();

        List<User> entityList = DozerMapper.parseListObjects(voList, User.class);

        entityList.forEach(entity -> {
            assertNotNull(entity);
            assertNotNull(entity.getId());
            assertNotNull(entity.getUserName());
            assertNotNull(entity.getFullName());
            assertNotNull(entity.getEmail());
            assertNotNull(entity.getPassword());
            assertNotNull(entity.getGender());
            assertNotNull(entity.getNationality());
            assertNotNull(entity.getTimeZone());

            assertEquals("User" + entity.getId(), entity.getFullName());
            assertEquals("Email" + entity.getId(), entity.getEmail());
            assertEquals("Password" + entity.getId(), entity.getPassword());
            assertEquals(entity.getId() % 2 == 0 ? "Male" : "Female", entity.getGender());
            assertEquals("Brazilian" + entity.getId(), entity.getNationality());
            assertEquals(ZoneId.of("America/Sao_Paulo"), entity.getTimeZone());

            assertTrue(entity.getEnabled());
            assertTrue(entity.getAccountNonExpired());
            assertTrue(entity.getAccountNonLocked());
            assertTrue(entity.getCredentialsNonExpired());
        });
    }
}
