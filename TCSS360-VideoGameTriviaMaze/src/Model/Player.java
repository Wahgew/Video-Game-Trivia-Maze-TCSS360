package Model;

public class Player {
    private static Player mySingleton;
    private int myLocationRow;
    private int myLocationCol;
    private int myScore;
    private int myCorrectAns;
    private Player() {
        myLocationRow = Maze.getInstance().getMyEntranceRow();
        myLocationCol = Maze.getInstance().getMyEntranceColumn();
        myScore = 0;
        myCorrectAns = 0;
    }
    public static synchronized Player getInstance() {
        if (mySingleton == null) {
            mySingleton = new Player();
        }
        return mySingleton;
    }

    /**
     * Checks if the player can attempt to move towards the room corresponding to the parameter.
     * E.g, the door in the direction player is moving toward is not locked or hasn't attempted to answer.
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
    public int getMyLocationRow() {
        return myLocationRow;
    }
    public int getMyLocationCol() {
        return myLocationCol;
    }
    public int getMyCorrectAns() {
        return myCorrectAns;
    }
    public int getMyScore() {
        return myScore;
    }
}
