package org.example.map;

public enum WayType {
    NORTH('N', 'W', 'E', false, false),
    SOUTH('S', 'E', 'W', false, true),
    EAST('E', 'N', 'S', true, true),
    WEST('W', 'S', 'N', true, false);

    private final char key;
    private final char left;
    private final char right;
    private final boolean horizontal;
    private final boolean add;

    WayType(char key, char left, char right, boolean horizontal, boolean add) {
        this.key = key;
        this.left = left;
        this.right = right;
        this.horizontal = horizontal;
        this.add = add;
    }

    public char getKey() {
        return key;
    }

    public char getLeft() {
        return left;
    }

    public char getRight() {
        return right;
    }

    public boolean isHorizontal() {
        return horizontal;
    }

    public boolean isAdd() {
        return add;
    }

    public static WayType byKey(char key) {
        for (WayType way : WayType.values()) {
            if (way.key == key) {
                return way;
            }
        }
        throw new IllegalArgumentException(String.format("Invalid way type: %s", key));
    }
}
