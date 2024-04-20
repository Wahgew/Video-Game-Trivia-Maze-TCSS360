package Model;

import java.util.Random;

public class Maze {
    private static Maze mySingleton;
    private final Room[][] myMaze;
    private final int myEntranceX;
    private final int myEntranceY;
    private final int myExitX;
    private final int myExitY;
    private final Random myRandom;

    private Maze(int theXSize, int theYSize) {
        myMaze = new Room[theXSize][theYSize];
        myRandom = new Random();
        myEntranceX = generateNumber(theXSize);
        myEntranceY = generateNumber(theYSize);
        myExitX = generateNumber(theXSize);
        myExitY = generateNumber(theYSize);
    }
    public static synchronized Maze getInstance() {
        if (mySingleton == null) {
            mySingleton = new Maze();
        }
        return mySingleton;
    }

    /**
     * Generates an int, greater than or equal to zero and less than the upper bound.
     * @param theUpperBound is the exclusive upperbound for generation.
     * @return a generated int.
     */
    private int generateNumber(int theUpperBound) {
        return myRandom.nextInt(theUpperBound);
    }

    public int getMyEntranceX() {
        return myEntranceX;
    }

    public int getMyEntranceY() {
        return myEntranceY;
    }

    public int getMyExitX() {
        return myExitX;
    }

    public int getMyExitY() {
        return myExitY;
    }
    public Room getMyRoom(int theRoomX, int theRoomY) {
        if ((theRoomX >= 0 && theRoomX < myMaze.length) && (theRoomY >= 0 && theRoomY < myMaze[0].length)) {
            return myMaze[theRoomX][theRoomY];
        }
        else {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
    }
}
