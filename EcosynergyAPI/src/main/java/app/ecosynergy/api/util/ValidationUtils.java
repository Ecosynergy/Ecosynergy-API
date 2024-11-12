package app.ecosynergy.api.util;

import java.util.regex.Pattern;

public class ValidationUtils {
    private static final String EMAIL_REGEX = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        return pattern.matcher(email).matches();
    }

    public static boolean isFullNameValid(String fullName) {
        return fullName != null && fullName.matches("[a-zA-Z\\s]+");
    }

    public static String formatFullName(String fullName) {
        if (fullName == null || fullName.isEmpty()) return fullName;

        String[] words = fullName.trim().split("\\s+");
        StringBuilder formattedName = new StringBuilder();

        for (String word : words) {
            formattedName.append(Character.toUpperCase(word.charAt(0)))
                    .append(word.substring(1).toLowerCase())
                    .append(" ");
        }
        return formattedName.toString().trim();
    }
}
