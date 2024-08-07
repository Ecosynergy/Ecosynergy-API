package app.ecosynergy.api.integrationtests.deserializers;

import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.ZoneId;

public class ZoneIdDeserializer extends StdDeserializer<ZoneId> {

    public ZoneIdDeserializer() {
        super(ZoneId.class);
    }

    @Override
    public ZoneId deserialize(com.fasterxml.jackson.core.JsonParser p, com.fasterxml.jackson.databind.DeserializationContext ctxt) throws IOException {
        return ZoneId.of(p.getValueAsString());
    }
}
