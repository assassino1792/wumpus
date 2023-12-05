package org.example.map;


    public enum ActionType {
        MAP_EDITOR(1, "1. Map editor."),
        READ_FILE(2, "2. Read map from file."),
        READ_DB(3, "3. Read from db."),
        SAVE_DB(4, "4. Save into db."),
        GAME(5, "5. Game."),
        EXIT(6, "6. Exit.");

        private final int number;
        private final String commandDescription;

        ActionType(int number, String commandDescription) {
            this.number = number;
            this.commandDescription = commandDescription;
        }


        public int getNumber() {
            return number;
        }

        public String getCommandDescription() {
            return commandDescription;
        }

        public static ActionType byNumber(int number) {
            for (ActionType way : ActionType.values()) {
                if (way.number == number) {
                    return way;
                }
            }
            return null;
        }
    }

