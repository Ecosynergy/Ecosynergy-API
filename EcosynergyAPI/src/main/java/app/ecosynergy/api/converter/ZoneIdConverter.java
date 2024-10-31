package app.ecosynergy.api.converter;

import com.github.dozermapper.core.CustomConverter;

import java.time.ZoneId;

public class ZoneIdConverter implements CustomConverter {

    @Override
    public Object convert(Object destination, Object source, Class<?> destClass, Class<?> sourceClass) {
        return switch (source) {
            case null -> null;
            case String s -> ZoneId.of(s);
            case ZoneId zoneId -> source;
            default ->
                    throw new IllegalArgumentException("Unsupported type for ZoneId conversion: " + source.getClass().getName());
        };
    }
}