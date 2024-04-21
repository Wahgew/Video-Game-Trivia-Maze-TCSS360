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
    boolean validPlayerMove(int theX, int theY) { // use direction enum
        boolean moveAllowed = false;
        if (theX >= 0 && theX < Maze.getInstance().getMyMazeRows()
                && theY >= 0 && theY < Maze.getInstance().getMyMazeCols()) {
            if (!Maze.getInstance().getMyRoom(theX, theY)
                    .getMyDoor(Direction.getPlayerDirection(theX, theY)).getMyLockStatus()) {
                moveAllowed = true;
            }
        }
        return moveAllowed;
    }
    void movePlayer(int theX, int theY) {
        if (validPlayerMove(theX, theY)) {
            myLocationRow = theX;
            myLocationCol = theY;
        }
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
