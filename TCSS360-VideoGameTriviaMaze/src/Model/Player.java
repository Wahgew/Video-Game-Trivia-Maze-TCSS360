package Model;

public class Player {
    private int myLocationX;
    private int myLocationY;
    private int myScore;
    private int myCorrectAns;
    Player() {
        myLocationX = Maze.getInstance().getMyEntranceX();
        myLocationY = Maze.getInstance().getMyEntranceY();
        myScore = 0;
        myCorrectAns = 0;
    }
    boolean validPlayerMove(int theX, int theY) { // use direction enum
        boolean moveAllowed = false;
        // TODO: validate somehow with Maze, Room, Door, need to check if
        //    door is unlocked and not out of bounds.
        return moveAllowed;
    }
    void movePlayer(int theX, int theY) {
        if (validPlayerMove(theX, theY)) {
            myLocationX = theX;
            myLocationY = theY;
        }
    }
    public int getMyLocationX() {
        return myLocationX;
    }
    public int getMyLocationY() {
        return myLocationY;
    }
    public int getMyCorrectAns() {
        return myCorrectAns;
    }
    public int getMyScore() {
        return myScore;
    }
}
