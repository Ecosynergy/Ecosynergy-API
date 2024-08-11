package app.ecosynergy.api.converter;

import app.ecosynergy.api.models.Activity;
import app.ecosynergy.api.models.Sector;
import com.github.dozermapper.core.CustomConverter;

public class SectorConverter implements CustomConverter {
    @Override
    public Object convert(Object destination, Object source, Class<?> destClass, Class<?> sourceClass) {
        if (source instanceof String) {
            Sector sector = new Sector();
            sector.setName((String) source);
            return sector;
        }

        if (source instanceof Sector) {
            return ((Sector) source).getName();
        }

        if (source instanceof Activity) {
            return ((Activity) source).getSector().getName();
        }

        throw new IllegalArgumentException("Unsupported type for Sector conversion: " + source.getClass().getName());
    }
}
