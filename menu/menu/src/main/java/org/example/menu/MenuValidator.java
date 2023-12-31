package org.example.menu;

/**
 * Utility class for validating menu-related input.
 */
public class MenuValidator {

    /**
     * Validates the format of a username.
     * @param username The username to validate.
     * @return `true` if the username is valid; otherwise, `false`.
     */
    public static boolean isValidUsername(final String username) {
        if (username == null) {
            return false;
        }
        int length = username.length();
        return length >= 3 && length <= 12 && !username.contains(" ");
    }
}
