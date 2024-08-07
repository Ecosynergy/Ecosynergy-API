package app.ecosynergy.api.integrationtests.deserializers;

import app.ecosynergy.api.integrationtests.vo.UserVO;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class UserVODeserializer extends StdDeserializer<UserVO> {
    public UserVODeserializer() {
        super(UserVO.class);
    }

    @Override
    public UserVO deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        UserVO user = new UserVO();
        p.skipChildren();
        return user;
    }
}
