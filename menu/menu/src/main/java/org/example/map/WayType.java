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
     * @param wayKey The character wayKey representing the direction.
     * @param toLeft The character wayKey
     * representing the toLeft direction when turning.
     * @param toRight The character wayKey
     * representing the toRight direction when turning.
     * @param horizontalX Indicates whether the direction is horizontalX.
     * @param toAdd Indicates whether adding the direction is allowed.
     */
    WayType(
            final char wayKey,
            final char toLeft,
            final char toRight,
            final boolean horizontalX,
            final boolean toAdd) {
        this.key = wayKey;
        this.left = toLeft;
        this.right = toRight;
        this.horizontal = horizontalX;
        this.add = toAdd;
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
     * @throws IllegalArgumentException if the key
     * does not match any WayType.
     */
    public static WayType byKey(final char key) {
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
