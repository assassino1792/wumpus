package org.example.map;

/**
 * Represents different cardinal directions for navigation.
 */
public enum WayType {
    /** Represents the North direction. */
    NORTH('N', 'W', 'E', false, false),
    /** Represents the South direction. */
    SOUTH('S', 'E', 'W', false, true),
    /** Represents the East direction. */
    EAST('E', 'N', 'S', true, true),
    /** Represents the West direction. */
    WEST('W', 'S', 'N', true, false);

    /** The character key representing the direction. */
    private final char key;
    /** The character key representing the left direction when turning. */
    private final char left;
    /** The character key representing the right direction when turning. */
    private final char right;
    /** Indicates whether the direction is horizontal. */
    private final boolean horizontal;
    /** Indicates whether adding the direction is allowed. */
    private final boolean add;

    /**
     * Initializes a new instance of the WayType enum.
     * @param key The character key representing the direction.
     * @param left The character key representing the left direction when turning.
     * @param right The character key representing the right direction when turning.
     * @param horizontal Indicates whether the direction is horizontal.
     * @param add Indicates whether adding the direction is allowed.
     */
    WayType(char key, char left, char right, boolean horizontal, boolean add) {
        this.key = key;
        this.left = left;
        this.right = right;
        this.horizontal = horizontal;
        this.add = add;
    }

    /**
     * Gets the character key representing the direction.
     * @return The character key.
     */
    public char getKey() {
        return key;
    }
    /**
     * Gets the character key representing the left direction when turning.
     * @return The character key for the left direction.
     */
    public char getLeft() {
        return left;
    }
    /**
     * Gets the character key representing the right direction when turning.
     * @return The character key for the right direction.
     */
    public char getRight() {
        return right;
    }
    /**
     * Checks if the direction is horizontal.
     * @return true if the direction is horizontal, false otherwise.
     */
    public boolean isHorizontal() {
        return horizontal;
    }
    /**
     * Checks if adding the direction is allowed.
     * @return true if adding the direction is allowed, false otherwise.
     */
    public boolean isAdd() {
        return add;
    }
    /**
     * Retrieves a WayType enum value based on its character key.
     * @param key The character key to look up.
     * @return The corresponding WayType value.
     * @throws IllegalArgumentException if the key does not match any WayType.
     */
    public static WayType byKey(char key) {
        for (WayType way : WayType.values()) {
            if (way.key == key) {
                return way;
            }
        }
        throw new IllegalArgumentException(String.format(
                "Invalid way type: %s", key)
        );
    }
}
