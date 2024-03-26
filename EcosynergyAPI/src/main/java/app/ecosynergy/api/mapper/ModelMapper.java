package app.ecosynergy.api.mapper;

import app.ecosynergy.api.data.vo.v1.FireReadingVO;
import app.ecosynergy.api.data.vo.v1.MQ135ReadingVO;
import app.ecosynergy.api.data.vo.v1.MQ7ReadingVO;
import app.ecosynergy.api.data.vo.v1.UserVO;
import app.ecosynergy.api.models.FireReading;
import app.ecosynergy.api.models.MQ135Reading;
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
        mapper.createTypeMap(
                MQ135Reading.class,
                MQ135ReadingVO.class
        ).addMapping(MQ135Reading::getId, MQ135ReadingVO::setKey);
        mapper.createTypeMap(
                MQ135ReadingVO.class,
                MQ135Reading.class
        ).addMapping(MQ135ReadingVO::getKey, MQ135Reading::setId);
        mapper.createTypeMap(
                FireReading.class,
                FireReadingVO.class
        ).addMapping(FireReading::getId, FireReadingVO::setKey);
        mapper.createTypeMap(
                FireReadingVO.class,
                FireReading.class
        ).addMapping(FireReadingVO::getKey, FireReading::setId);
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
