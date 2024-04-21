package Model;

import java.util.Random;

public class Maze {
    private static Maze mySingleton;
    private final Room[][] myMaze;
    private final int myEntranceRow;
    private final int myEntranceCol;
    private final int myExitRow;
    private final int myExitCol;
    private final Random myRandom;

    private Maze(int theXSize, int theYSize) {
        myMaze = new Room[theXSize][theYSize];
        myRandom = new Random();
        myEntranceRow = generateNumber(theXSize);
        myEntranceCol = generateNumber(theYSize);
        myExitRow = generateNumber(theXSize);
        myExitCol = generateNumber(theYSize);
    }

    /**
     * Getter for Singleton instance of Maze,
     * with no parameter creates a default size of 4x4 rooms if
     * Singleton doesn't currently exist.
     * @return Singleton instance of Maze.
     */
    public static synchronized Maze getInstance() {
        if (mySingleton == null) {
            mySingleton = new Maze(4, 4);
        }
        return mySingleton;
    }

    public static synchronized Maze getInstance(int theX, int theY) {
        if (mySingleton == null) {
            mySingleton = new Maze(theX, theY);
        }
        return mySingleton;
    }

    /**
     * Generates an int, greater than or equal to zero and less than the upper bound.
     * Used for generating random entrance and random exit location.
     * @param theUpperBound is the exclusive upperbound for generation.
     * @return a generated int.
     */
    private int generateNumber(int theUpperBound) {
        return myRandom.nextInt(theUpperBound);
    }

    public int getMyEntranceRow() {
        return myEntranceRow;
    }

    public int getMyEntranceColumn() {
        return myEntranceCol;
    }

    public int getMyExitRow() {
        return myExitRow;
    }

    public int getMyExitCol() {
        return myExitCol;
    }
    public Room getMyRoom(int theRoomX, int theRoomY) {
        if ((theRoomX >= 0 && theRoomX < myMaze.length) && (theRoomY >= 0 && theRoomY < myMaze[0].length)) {
            return myMaze[theRoomX][theRoomY];
        }
        else {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
    }
    public int getMyMazeRows() {
        return myMaze.length;
    }
    public int getMyMazeCols() {
        return myMaze[0].length;
    }
    @Override
    public String toString() {
        String mazeString = "";
        for (int i = 0; i < myMaze.length; i++) {
            for (int j = 0; j < myMaze[0].length; j++) {
                boolean specialChar = false;
                if (Player.getInstance().getMyLocationRow() == i && Player.getInstance().getMyLocationCol() == j) {
                    mazeString += "▣";
                    specialChar = true;
                }
                if (myExitRow == i && myExitCol == j){
                    mazeString += "▨";
                    specialChar = true;
                }
                if (!specialChar) {
                    mazeString += "□";
                }
            }
            mazeString += "\n";
        }
        return mazeString;
    }
}
