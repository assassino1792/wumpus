package org.example.map;

import java.util.Objects;

/**
 * Represents a unique identifier for a map location.
 */
public class MapID {
    /** The horizontal coordinate of the map location. */
    private int horizontal;
    /** The vertical coordinate of the map location. */
    private int vertical;

    /**
     * Initializes a new instance of the MapID
     * class with specified horizontalX and verticalY coordinates.
     * @param horizontalX The horizontalX coordinate.
     * @param verticalY The verticalY coordinate.
     */
    public MapID(final int horizontalX, final int verticalY) {
        this.horizontal = horizontalX;
        this.vertical = verticalY;
    }
    /**
     * Gets the horizontal coordinate of the map location.
     * @return The horizontal coordinate.
     */
    public int getHorizontal() {
        return horizontal;
    }
    /**
     * Gets the vertical coordinate of the map location.
     * @return The vertical coordinate.
     */
    public int getVertical() {
        return vertical;
    }

    /**
     * Compares this MapID object to another object for equality.
     * @param o The object to compare.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MapID newMapID = (MapID) o;
        return horizontal == newMapID.horizontal
                && vertical == newMapID.vertical;
    }
    /**
     * Computes a hash code for this MapID object.
     * @return The computed hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(horizontal, vertical);
    }
}


