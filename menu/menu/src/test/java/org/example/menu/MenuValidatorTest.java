package org.example.menu;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
public class MenuValidatorTest {

    @Test
    void testValidUsername() {
        assertTrue(MenuValidator.isValidUsername("ValidUser123"));

    }

    @Test
    void testInvalidUsername() {
        assertFalse(MenuValidator.isValidUsername(""));
        assertFalse(MenuValidator.isValidUsername(" "));
        assertFalse(MenuValidator.isValidUsername("ToLongInvalidUsername"));
    }
}
