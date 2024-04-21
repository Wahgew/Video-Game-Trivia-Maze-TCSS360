package Model;

/**
 * The player class represents a player in the game.
 *
 * Each player has a current location, score, and number of correct answers.
 * The player's current location is represented by row and column indices.
 * The score indicates the player's progress or performance in the game,
 * while the number of correct answers reflects their success rate.
 * This class implements the Singleton design pattern, ensuring that only one instance
 * of the player exists throughout the program.
 * The Player class provides methods for moving the player within the game environment,
 * checking if a move is valid, and accessing player information such as location, score, and
 * number of correct answers.
 *
 * @author Ken Egawa
 * @author Peter Madin
 * @author Sopheanith Ny
 * @version 0.0.1 April 20, 2024
 */
public class Player {

    /**
     * Singleton instance of Player.
     */
    private static Player mySingleton;

    /**
     * Row index of the player's current location.
     */
    private int myLocationRow;

    /**
     * Column index of the player's current location.
     */
    private int myLocationCol;

    /**
     * Player's current score.
     */
    private int myScore;

    /**
     * Number of correct answers given by the player.
     */
    private int myCorrectAns;

    /**
     * Constructs a new Player object with default attributes.
     * The player's initial location is set to the maze entrance,
     * and both the score and number of correct answers are initialized to zero.
     */
    private Player() {
        myLocationRow = Maze.getInstance().getMyEntranceRow();
        myLocationCol = Maze.getInstance().getMyEntranceColumn();
        myScore = 0;
        myCorrectAns = 0;
    }

    /**
     * Returns the Singleton instance of Player.
     * If no instance exists, a new player is created.
     *
     * @return Singleton instance of Player
     */
    public static synchronized Player getInstance() {
        if (mySingleton == null) {
            mySingleton = new Player();
        }
        return mySingleton;
    }

    /**
     * Checks if the player can attempt to move towards the room corresponding to the parameter.
     * E.g, the door in the direction the player is moving toward is not locked or hasn't attempted to answer.
     * @param theX the row the player is attempting to move to.
     * @param theY the column the player is attempting to move to.
     * @return if the movement is a valid player move.
     */
    boolean validPlayerMove(int theX, int theY) { // TODO: need to change, read TODO located in Room class.
        boolean moveAllowed = false;
        if (theX >= 0 && theX < Maze.getInstance().getMyMazeRows()
                && theY >= 0 && theY < Maze.getInstance().getMyMazeCols()) {
            if (!Maze.getInstance().getMyRoom(theX, theY) // if door is not locked
                    .getMyDoor(Direction.getPlayerDirection(theX, theY)).getMyLockStatus()) {
                moveAllowed = true;
            } else if (Maze.getInstance().getMyRoom(theX, theY) // if door hasn't been attempted yet.
                    .getMyDoor(Direction.getPlayerDirection(theX, theY)).getMyAttemptStatus()) {
                moveAllowed = true;
            }
        }
        return moveAllowed;
    }

    /**
     * NEED TO VALIDATE THE MOVE BEFORE USING THIS IS JUST A SETTER.
     * @param theRow the row moving to.
     * @param theCol the column moving to.
     */
    void movePlayer(int theRow, int theCol) {
        myLocationRow = theRow;
        myLocationCol = theCol;
    }

    /**
     * Gets the row index of the player's current location.
     *
     * @return the row index of the player's current location
     */
    public int getMyLocationRow() {
        return myLocationRow;
    }

    /**
     * Gets the column index of the player's current location.
     *
     * @return the column index of the player's current location
     */
    public int getMyLocationCol() {
        return myLocationCol;
    }

    /**
     * Gets the number of correct answers given by the player.
     *
     * @return the number of correct answers given by the player
     */
    public int getMyCorrectAns() {
        return myCorrectAns;
    }

    /**
     * Gets the player's current score.
     *
     * @return the player's current score
     */
    public int getMyScore() {
        return myScore;
    }
}
