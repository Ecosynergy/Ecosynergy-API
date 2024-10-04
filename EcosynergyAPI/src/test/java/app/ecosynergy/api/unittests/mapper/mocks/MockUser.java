package app.ecosynergy.api.unittests.mapper.mocks;

import app.ecosynergy.api.data.vo.v1.UserVO;
import app.ecosynergy.api.models.User;

import java.util.ArrayList;
import java.util.List;

public class MockUser {
    public User mockEntity(){
        return mockEntity(0);
    }

    public UserVO mockUserVO(){
        return mockUserVO(0);
    }

    public User mockEntity(Integer number){
        User entity = new User();
        entity.setId(number.longValue());
        entity.setUserName("user" + number);
        entity.setFullName("User" + number);
        entity.setEmail("email" + number);
        entity.setPassword("Password" + number);
        entity.setGender(number % 2 == 0 ? "Male" : "Female");
        entity.setNationality("Brazilian" + number);
        entity.setEnabled(true);
        entity.setAccountNonExpired(true);
        entity.setAccountNonLocked(true);
        entity.setCredentialsNonExpired(true);

        return entity;
    }

    public UserVO mockUserVO(Integer number){
        UserVO entity = new UserVO();
        entity.setKey(number.longValue());
        entity.setUserName("user" + number);
        entity.setFullName("User" + number);
        entity.setEmail("email" + number);
        entity.setPassword("Password" + number);
        entity.setGender(number % 2 == 0 ? "Male" : "Female");
        entity.setNationality("Brazilian" + number);
        entity.setEnabled(true);
        entity.setAccountNonExpired(true);
        entity.setAccountNonLocked(true);
        entity.setCredentialsNonExpired(true);

        return entity;
    }

    public List<User> mockEntityList(){
        List<User> entityList = new ArrayList<>();
        for(int i = 0; i < 14; i++){
            entityList.add(mockEntity(i));
        }

        return entityList;
    }

    public List<UserVO> mockVOList() {
        List<UserVO> voList = new ArrayList<>();
        for(int i = 0; i < 14; i++){
            voList.add(mockUserVO(i));
        }

        return voList;
    }
}
