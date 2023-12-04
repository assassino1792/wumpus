package org.wumpus.model;


public enum HeroActionType {
    STEP(1, "1. Step ahead."),
    TURN_LEFT(2, "2. Turn left."),
    TURN_RIGHT(3, "3. Turn right."),
    SHOOT(4, "4. Shoot arrow."),
    SUSPEND(5, "5. Suspend game, you can continue later."),
    GIVE_UP(6, "6. Give up game.");

    private final int number;
    private final String commandDescription;

    HeroActionType(int number, String commandDescription) {
        this.number = number;
        this.commandDescription = commandDescription;
    }


    public int getNumber() {
        return number;
    }

    public String getCommandDescription() {
        return commandDescription;
    }

    public static HeroActionType byNumber(int number) {
        for (HeroActionType way : HeroActionType.values()) {
            if (way.number == number) {
                return way;
            }
        }
        return null;
    }
}
