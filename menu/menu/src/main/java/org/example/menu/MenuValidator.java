package org.example.menu;

public class MenuValidator {

    public static boolean isValidUsername(String username) {
        if (username == null) {
            return false;
        }
        int length = username.length();
        return length >= 3 && length <= 12 && !username.contains(" ");
    }
}
