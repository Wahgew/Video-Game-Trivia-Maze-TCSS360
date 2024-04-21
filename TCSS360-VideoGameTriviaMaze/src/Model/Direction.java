package Model;

enum Direction {
    NORTH(0),
    EAST(1),
    SOUTH(2),
    WEST(3);
    private final int myValue;
    Direction(int theValue) {
        this.myValue = theValue;
    }
    int getMyValue() {
        return myValue;
    }

    /**
     * Returns the direction in which the player is moving to move to
     * parameter room.
     * @param theRow the row of the room player is moving to.
     * @param theCol the column of the room player is moving to.
     * @return the enum Direction, the direction the player is moving to.
     */
    static Direction getPlayerDirection(int theRow, int theCol) {
        int playerRow = Player.getInstance().getMyLocationRow();
        int playerCol = Player.getInstance().getMyLocationCol();
        if (theRow < playerRow && theCol == playerCol) {
            return NORTH;
        } else if (theRow == playerRow && theCol > playerCol) {
            return EAST;
        } else if (theRow > playerRow && theCol == playerCol) {
            return SOUTH;
        } else {
            return WEST;
        }
    }
}
