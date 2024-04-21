package Model;

/**
 * The Maze class represents a maze composed of interconnected rooms.
 * It provides methods for accessing information about the maze, such as
 * size, entrance and exit locations, and individual rooms.
 *
 * The maze generates randomly upon instantiation, with specified size and
 * random entrance and exit locations.
 * The rooms in the maze are represented by
 * a two-dimensional array of room objects.
 *
 * @author Ken Egawa
 * @author Peter Madin
 * @author Sopheanith Ny
 * @version 0.0.1 April 20, 2024
 */
import java.util.Random;

public class Maze {
    //TODO:
    // Define a minimum and maximum maze size.

    /**
     * Singleton instance of Maze.
     */
    private static Maze mySingleton;

    /**
     * Two-dimensional array representing the rooms in the maze.
     */
    private final Room[][] myMaze;

    /**
     * Random object used for generating random numbers.
     */
    private final int myEntranceRow;

    /**
     * Row index of the entrance location in the maze.
     */
    private final int myEntranceCol;

    /**
     * Column index of the entrance location in the maze.
     */
    private final int myExitRow;

    /**
     * Row index of the exit location in the maze.
     */
    private final int myExitCol;

    /**
     * Column index of the exit location in the maze.
     */
    private final Random myRandom;

    /**
     * Constructs a new Maze object with the specified dimensions.
     *
     * @param theXSize the number of rows in the maze
     * @param theYSize the number of columns in the maze
     */
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
     *
     * @return Singleton instance of Maze.
     */
    public static synchronized Maze getInstance() {
        if (mySingleton == null) {
            mySingleton = new Maze(4, 4);
        }
        return mySingleton;
    }

    /**
     * Returns the Singleton instance of Maze with custom dimensions.
     * If no instance exists, a maze with the specified dimensions is created.
     *
     * @param theX the number of rows in the maze
     * @param theY the number of columns in the maze
     * @return Singleton instance of Maze with custom dimensions
     */
    public static synchronized Maze getInstance(int theX, int theY) {
        if (mySingleton == null) {
            mySingleton = new Maze(theX, theY);
        }
        return mySingleton;
    }

    /**
     * Generates an int, greater than or equal to zero and less than the upper bound.
     * Used for generating random entrance and random exit location.
     *
     * @param theUpperBound is the exclusive upperbound for generation.
     * @return a generated int.
     */
    private int generateNumber(int theUpperBound) {
        return myRandom.nextInt(theUpperBound);
    }

    /**
     * Gets the row index of the entrance location in the maze.
     *
     * @return the row index of the entrance
     */
    public int getMyEntranceRow() {
        return myEntranceRow;
    }

    /**
     * Gets the column index of the entrance location in the maze.
     *
     * @return the row index of the entrance.
     */
    public int getMyEntranceColumn() {
        return myEntranceCol;
    }

    /**
     * Gets the row index of the exit location in the maze.
     *
     * @return the row index of the exit
     */
    public int getMyExitRow() {
        return myExitRow;
    }

    /**
     * Gets the column index of the exit location in the maze.
     *
     * @return the column index of the exit
     */
    public int getMyExitCol() {
        return myExitCol;
    }

    /**
     * Gets the room object located at the specified position in the maze.
     *
     * @param theRoomX the row index of the room
     * @param theRoomY the column index of the room
     * @return the Room object at the specified position
     * @throws IndexOutOfBoundsException if the specified position is out of bounds
     */
    public Room getMyRoom(int theRoomX, int theRoomY) {
        if ((theRoomX >= 0 && theRoomX < myMaze.length) && (theRoomY >= 0 && theRoomY < myMaze[0].length)) {
            return myMaze[theRoomX][theRoomY];
        }
        else {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
    }

    /**
     * Gets the number of rows in the maze.
     *
     * @return the number of rows in the maze
     */
    public int getMyMazeRows() {
        return myMaze.length;
    }

    /**
     * Gets the number of columns in the maze.
     *
     * @return the number of columns in the maze
     */
    public int getMyMazeCols() {
        return myMaze[0].length;
    }

    /**
     * Returns a string representation of the maze.
     * The player's location is represented by "▣", the exit by "▨", and empty rooms by "□".
     *
     * @return a string representation of the maze
     */
    @Override
    public String toString() {
        String mazeString = "";
        for (int i = 0; i < myMaze.length; i++) {
            for (int j = 0; j < myMaze[0].length; j++) {
                boolean specialChar = false;
                if (Player.getInstance().getMyLocationRow() == i && Player.getInstance().getMyLocationCol() == j) {
                    mazeString += "▣"; //▣
                    specialChar = true;
                }
                if (myExitRow == i && myExitCol == j){
                    mazeString += "▨"; //▨
                    specialChar = true;
                }
                if (!specialChar) {
                    mazeString += "□"; //□
                }
            }
            mazeString += "\n";
        }
        return mazeString;
    }
}
