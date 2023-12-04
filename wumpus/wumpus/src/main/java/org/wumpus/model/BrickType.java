package org.wumpus.model;



public enum BrickType {

    WALL('W', "wall"),
    HERO('H', "hero"),
    WUMPUS('U', "wumpus"),
    PIT('P', "pit"),
    GOLD('G', "gold"),
    EMPTY('_', "empty");


    private final char key;
    private final String name;

    BrickType(char key, String name) {
        this.key = key;
        this.name = name;
    }

    public char getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public static BrickType byKey(char key) {
        for (BrickType brickType : BrickType.values()) {
            if (brickType.key == key) {
                return brickType;
            }
        }
        throw new IllegalArgumentException(String.format("Invalid brick type: %s", key));
    }
}
