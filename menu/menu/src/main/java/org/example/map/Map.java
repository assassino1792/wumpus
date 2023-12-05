package org.example.map;

public class Map {

        private MapID mapid;

        private MapType maptype;

        public MapID getMapID() {
            return mapid;
        }

        public void getMapID(MapID mapid) {
            this.mapid = mapid;
        }

        public MapType getMapType() {
            return maptype;
        }

        public void setMapType(MapType maptype) {
            this.maptype = maptype;
        }

    }

