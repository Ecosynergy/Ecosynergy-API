package app.ecosynergy.api.mapper;

import app.ecosynergy.api.data.vo.v1.MQ7ReadingVO;
import app.ecosynergy.api.data.vo.v1.UserVO;
import app.ecosynergy.api.models.MQ7Reading;
import app.ecosynergy.api.models.User;

import java.util.ArrayList;
import java.util.List;

public class ModelMapper {
    private static final org.modelmapper.ModelMapper mapper = new org.modelmapper.ModelMapper();

    static {
        mapper.createTypeMap(
                User.class,
                UserVO.class
        ).addMapping(User::getId, UserVO::setKey);
        mapper.createTypeMap(
                UserVO.class,
                User.class
        ).addMapping(UserVO::getKey, User::setId);
        mapper.createTypeMap(
                MQ7Reading.class,
                MQ7ReadingVO.class
        ).addMapping(MQ7Reading::getId, MQ7ReadingVO::setKey);
        mapper.createTypeMap(
                MQ7ReadingVO.class,
                MQ7Reading.class
        ).addMapping(MQ7ReadingVO::getKey, MQ7Reading::setId);
    }

    public static <O,D> D parseObject(O origin, Class<D> destination){
        return mapper.map(origin, destination);
    }

    public static <O,D> List<D> parseListObject(List<O> origin, Class<D> destination){
        List<D> destinationObjects = new ArrayList<>();

        origin.forEach(object -> {
            destinationObjects.add(mapper.map(object, destination));
        });

        return destinationObjects;
    }
}
