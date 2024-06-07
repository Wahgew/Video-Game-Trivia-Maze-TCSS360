package Model;

/**
 * The Direction enum represents the four cardinal directions:
 * North, East, South, and West
 * Each direction is associated with an integer value.
 *
 * @author Ken Egawa
 * @author Peter Madin
 * @author Sopheanith Ny
 * @version 0.0.1 April 20, 2024
 */
public enum Direction {
    /**
     * Represents the direction North with an integer value of 0.
     */
    NORTH(0),

    /**
     * Represents the direction East with an integer value of 1.
     */
    EAST(1),

    /**
     * Represents the direction South with an integer value of 2.
     */
    SOUTH(2),

    /**
     * Represents the direction West with an integer value of 3.
     */
    WEST(3);

    /**
     * Direction value to be passed.
     */
    private final int myValue;

    /**
     * Constructs a Direction object with the specified integer value.
     *
     * @param theValue the integer value associated with the direction
     * @throws IllegalArgumentException if theValue is not between 0 and 3
     */
    Direction(int theValue) {
        if (theValue < 0 || theValue > 3)  {
            throw new IllegalArgumentException("Direction value must be between 0 and 3"
            + "current value is: " + theValue);
        }
        this.myValue = theValue;
    }

    /**
     * Returns the integer value associated with this direction.
     * @return the integer value of this direction
     */
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
    /**
     * Returns the direction in which the player is moving to move to
     * parameter room.
     * @return the enum Direction, the direction the player is moving to.
     */
    static Direction getPlayerDirection(Direction theDirection) {
        switch (theDirection) {
            case NORTH -> {
                return SOUTH;
            }
            case SOUTH -> {
                return NORTH;
            }
            case EAST -> {
                return WEST;
            }
            case WEST -> {
                return EAST;
            }
        }
        throw new IllegalArgumentException();
    }

    /**
     * Change int into the corresponding Direction.
     * @param theInt
     * @return
     */
    public static Direction getDirectionInt(int theInt) {
        switch (theInt) {
            case 0 -> {
                return NORTH;
            }
            case 1 -> {
                return EAST;
            }
            case 2 -> {
                return SOUTH;
            }
            case 3 -> {
                return WEST;
            }
        }
        throw new IllegalArgumentException();
    }

    /**
     * toString override, intended to be used for getting icon file path.
     * @return the naming convention for movement button file path.
     */
    @Override
    public String toString() {
        if (this == NORTH) {
            return "up";
        } else if (this == EAST) {
            return "right";
        } else if (this == SOUTH) {
            return "down";
        } else if (this == WEST) {
            return "left";
        }
        throw new IllegalArgumentException("Direction toString is not working");
    }
}
