package org.example.map;


public enum MapType {

    WALL('W', "wall"),
    HERO('H', "hero"),
    WUMPUS('U', "wumpus"),
    PIT('P', "pit"),
    GOLD('G', "gold"),
    EMPTY('_', "empty");


    private final char key;
    private final String name;

    MapType(char key, String name) {
        this.key = key;
        this.name = name;
    }

    public char getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public static MapType byKey(char key) {
        for (MapType maptype : MapType.values()) {
            if (maptype.key == key) {
                return maptype;
            }
        }
        throw new IllegalArgumentException(String.format("Invalid map type: %s", key));
    }
}

