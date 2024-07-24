package app.ecosynergy.api.mapper;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

import java.util.ArrayList;
import java.util.List;

public class DozerMapper {

    private static final Mapper mapper = DozerBeanMapperBuilder.buildDefault();

    /**
     * Mapeia um objeto de origem para o tipo de destino.
     *
     * @param origin      o objeto de origem a ser mapeado
     * @param destination a classe do tipo de destino
     * @param <O>         o tipo de origem
     * @param <D>         o tipo de destino
     * @return o objeto mapeado para o tipo de destino
     */
    public static <O, D> D parseObject(O origin, Class<D> destination) {
        if (origin == null) {
            return null;
        }
        try {
            return mapper.map(origin, destination);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao mapear objeto", e);
        }
    }

    /**
     * Mapeia uma lista de objetos de origem para uma lista de objetos do tipo de destino.
     *
     * @param origin      a lista de objetos de origem a ser mapeada
     * @param destination a classe do tipo de destino
     * @param <O>         o tipo de origem
     * @param <D>         o tipo de destino
     * @return a lista de objetos mapeados para o tipo de destino
     */
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