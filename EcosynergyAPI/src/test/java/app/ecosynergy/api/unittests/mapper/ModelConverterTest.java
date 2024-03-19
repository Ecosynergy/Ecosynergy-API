package app.ecosynergy.api.unittests.mapper;

import app.ecosynergy.api.data.vo.v1.UserVO;
import app.ecosynergy.api.mapper.ModelMapper;
import app.ecosynergy.api.models.User;
import app.ecosynergy.api.unittests.mapper.mocks.MockUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ModelConverterTest {
    MockUser input;
    @BeforeEach
    public void setUp(){
        input = new MockUser();
    }

    @Test
    public void parseEntityToVOTest(){
        UserVO vo = ModelMapper.parseObject(input.mockEntity(), UserVO.class);

        assertEquals(Long.valueOf(0L), vo.getKey());
        assertEquals("User0", vo.getName());
        assertEquals("Email0", vo.getEmail());
        assertEquals("Password0", vo.getPassword());
        assertEquals("Male", vo.getGender());
        assertEquals("Brazilian0", vo.getNationality());
    }

    @Test
    public void parseEntityListToVOList(){
        List<User> entityList = input.mockEntityList();

        List<UserVO> voList = ModelMapper.parseListObject(entityList, UserVO.class);

        voList.forEach(vo -> {
            assertEquals("User" + vo.getKey(), vo.getName());
            assertEquals("Email" + vo.getKey(), vo.getEmail());
            assertEquals("Password" + vo.getKey(), vo.getPassword());
            assertEquals(vo.getKey() % 2 == 0 ? "Male" : "Female", vo.getGender());
            assertEquals("Brazilian" + vo.getKey(), vo.getNationality());
        });
    }

    @Test
    public void parseVOToEntity(){
        User vo = ModelMapper.parseObject(input.mockUserVO(), User.class);

        assertEquals(Long.valueOf(0L), vo.getId());
        assertEquals("UserVO0", vo.getName());
        assertEquals("Email0", vo.getEmail());
        assertEquals("Password0", vo.getPassword());
        assertEquals("Male", vo.getGender());
        assertEquals("Brazilian0", vo.getNationality());
    }

    @Test
    public void parseVOListToEntityList(){
        List<UserVO> entityList = input.mockVOList();

        List<User> voList = ModelMapper.parseListObject(entityList, User.class);

        voList.forEach(vo -> {
            assertEquals("UserVO" + vo.getId(), vo.getName());
            assertEquals("Email" + vo.getId(), vo.getEmail());
            assertEquals("Password" + vo.getId(), vo.getPassword());
            assertEquals(vo.getId() % 2 == 0 ? "Male" : "Female", vo.getGender());
            assertEquals("Brazilian" + vo.getId(), vo.getNationality());
        });
    }
}
