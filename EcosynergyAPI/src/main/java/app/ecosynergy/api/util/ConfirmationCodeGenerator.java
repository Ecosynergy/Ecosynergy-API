package app.ecosynergy.api.util;

import java.util.UUID;

public class ConfirmationCodeGenerator {
    public static String generateCode() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 6).toUpperCase();
    }
}
