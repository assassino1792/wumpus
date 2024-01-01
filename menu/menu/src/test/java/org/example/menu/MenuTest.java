package org.example.menu;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MenuTest {

    @Test
    public void testIsValidUsernameValid() {
        assertTrue(MenuValidator.isValidUsername("ValidUser"));
    }


}


