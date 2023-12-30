package org.example.map;

/**
 * Represents a map in the game.
 */
public class Map {
        /** The ID of the map. */
        private MapID mapid;
        /** The type of the map. */
        private MapType maptype;

        /**
         * Gets the ID of the map.
         * @return The ID of the map.
         */
        public MapID getMapID() {
            return mapid;
        }
        /**
         * Sets the ID of the map.
         * @param mapID The new map ID to set.
         */
        public void setMapID(final MapID mapID) {
            this.mapid = mapID;
        }

        /**
         * Gets the type of the map.
         * @return The type of the map.
         */
         public MapType getMapType() {
            return maptype;
         }
        /**
         * Sets the type of the map.
         * @param mapType The new map type to set.
         */
         public void setMapType(final MapType mapType) {
            this.maptype = mapType;
         }

    }

