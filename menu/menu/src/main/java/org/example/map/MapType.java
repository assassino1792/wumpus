package org.example.map;

/**
 * Represents different types of elements that can appear on the game map.
 */
public enum MapType {
    /** Represents a wall element on the map. */
    WALL('W', "wall"),
    /** Represents the hero character on the map. */
    HERO('H', "hero"),
    /** Represents the Wumpus creature on the map. */
    WUMPUS('U', "wumpus"),
    /** Represents a pit on the map. */
    PIT('P', "pit"),
    /** Represents a gold piece on the map. */
    GOLD('G', "gold"),
    /** Represents an empty space on the map. */
    EMPTY('_', "empty");

    /** The character key representing the map element. */
    private final char key;
    /** The human-readable name of the map element. */
    private final String name;

    /**
     * Initializes a new instance of the MapType enum.
     * @param key The character key representing the map element.
     * @param name The human-readable name of the map element.
     */
    MapType(final char key, final String name) {
        this.key = key;
        this.name = name;
    }
    /**
     * Gets the character key representing the map element.
     * @return The character key.
     */
    public char getKey() {
        return key;
    }
    /**
     * Gets the human-readable name of the map element.
     * @return The name of the map element.
     */
    public String getName() {
        return name;
    }
    /**
     * Retrieves a MapType enum value based on its character key.
     * @param key The character key to look up.
     * @return The corresponding MapType value.
     * @throws IllegalArgumentException if the key does not match any MapType.
     */
    public static MapType byKey(final char key) {
        for (MapType maptype : MapType.values()) {
            if (maptype.key == key) {
                return maptype;
            }
        }
        throw new IllegalArgumentException(String.format(
                "Invalid map type: %s", key)
        );
    }
}

