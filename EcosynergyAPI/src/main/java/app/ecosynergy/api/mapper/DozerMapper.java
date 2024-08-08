package app.ecosynergy.api.mapper;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

import java.util.ArrayList;
import java.util.List;

public class DozerMapper {

    private static final Mapper mapper = DozerBeanMapperBuilder.create()
            .withMappingFiles("dozerBeanMapping.xml")
            .build();

    public static <O, D> D parseObject(O origin, Class<D> destination) {
        if (origin == null) {
            return null;
        }
        try {
            return mapper.map(origin, destination);
        } catch (Exception e) {
            throw new RuntimeException("Object Mapping Error: ", e);
        }
    }

    public static <O, D> List<D> parseListObjects(List<O> origin, Class<D> destination) {
        if (origin == null) {
            return new ArrayList<>();
        }

        List<D> destinationObjects = new ArrayList<>();
        for (O o : origin) {
            destinationObjects.add(parseObject(o, destination));
        }
        return destinationObjects;
    }
}