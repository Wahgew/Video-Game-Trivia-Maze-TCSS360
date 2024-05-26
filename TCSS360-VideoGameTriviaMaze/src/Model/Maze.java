package Model;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

/**
 * The Maze class represents a maze composed of interconnected rooms.
 * It provides methods for accessing information about the maze, such as
 * size, entrance and exit locations, and individual rooms.
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
    private int myEntranceRow;

    /**
     * Row index of the entrance location in the maze.
     */
    private int myEntranceColumn;

    /**
     * Column index of the entrance location in the maze.
     */
    private int myExitRow;

    /**
     * Row index of the exit location in the maze.
     */
    private int myExitColumn;

    /**
     * Column index of the exit location in the maze.
     */
    private final Random myRandom;
    private final String myLayout;

    /**
     * Constructs a new Maze object with the specified dimensions.
     *
     * @param theXSize the number of rows in the maze
     * @param theYSize the number of columns in the maze
     */
    private Maze(int theXSize, int theYSize) {
        if (theXSize < 4 || theYSize < 4) {
            throw new IllegalArgumentException();
        }
        myMaze = new Room[theXSize][theYSize];
        myRandom = new Random();
        myEntranceRow = generateNumber(theXSize);
        myEntranceColumn = generateNumber(theYSize);
        myExitRow = generateNumber(theXSize);
        myExitColumn = generateNumber(theYSize);
        myLayout = "default";
        checkEntExitGen();
        mazeInstantiate();
    }
    private Maze(String theFileName) {
        Scanner fileScan = null;
        myRandom = null;
        try {
            fileScan = new Scanner(new File("src/Resource/MazeLayouts/" + theFileName));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        myLayout = theFileName;
        myMaze = fileMazeInstantiate(fileScan);
        checkOOBMaze();
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
    public static synchronized Maze getInstance(String theFileName) {
        if (mySingleton == null) {
            mySingleton = new Maze(theFileName);
        }
        return mySingleton;
    }

    public static synchronized void resetMaze(String theFileName) {
        mySingleton = new Maze(theFileName);
    }

    public static synchronized void resetMaze(int theXsize, int theYsize) {
        mySingleton = new Maze(theXsize, theXsize);
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
        return myEntranceColumn;
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
    public int getMyExitColumn() {
        return myExitColumn;
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
     * "Gets" the room that is adjacent to parameter location based on a given Direction.
     * @param theDirection the direction of the room to return.
     * @param theRow the row of the room to calculate the adjacency from.
     * @param theCol the column of the room to calculate the adjacency from.
     * @return the room that is adjacent in the parameter direction to the room specified by row and column.
     */
    public Room getMyAdjacentRoom(Direction theDirection, int theRow, int theCol) {
        switch (theDirection) {
            case NORTH -> {
                return myMaze[theRow - 1][theCol];
            }
            case SOUTH -> {
                return myMaze[theRow + 1][theCol];
            }
            case EAST -> {
                return myMaze[theRow][theCol + 1];
            }
            case WEST -> {
                return myMaze[theRow][theCol - 1];
            }
        }
        throw new IllegalArgumentException();
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

    void mazeInstantiate() { //
        for (int i = 0; i < getMyMazeRows(); i++) {
            for (int j = 0; j < getMyMazeCols(); j++) {
                myMaze[i][j] = new Room();
                if (i == 0 || i == getMyMazeRows() - 1 || j == 0 || j == getMyMazeCols() - 1) { // check if on edge of maze rows.
                    roomOutOfBounds(i, j);
                }
            }
        }
    }
    void checkOOBMaze() {
        for (int i = 0; i < getMyMazeRows(); i++) {
            for (int j = 0; j < getMyMazeCols(); j++) {
                if (i == 0 || i == getMyMazeRows() - 1 || j == 0 || j == getMyMazeCols() - 1) { // check if on edge of maze rows.
                    roomOutOfBounds(i, j);
                }
            }
        }
    }
    private Room[][] fileMazeInstantiate(Scanner theScan) {
        int mazeRow, mazeCol;
        mazeRow = theScan.nextInt();
        mazeCol = theScan.nextInt();
        char[][] inputMaze = new char[(mazeRow * 2) + 1][(mazeCol * 2) + 1];
        Room[][] outputMaze = new Room[mazeRow][mazeCol];
        String inputRow;
        theScan.nextLine();
        int inputRowCounter = -1;
        while (theScan.hasNextLine()) {
            inputRow = theScan.nextLine();
            inputRowCounter++;
            for (int i = 0; i < inputRow.length(); i++) {
                inputMaze[inputRowCounter][i] = inputRow.charAt(i);
            }
        }
        return readLayoutMaze(inputMaze, outputMaze);
    }
    private Room[][] readLayoutMaze(char[][] theInputMaze, Room[][] theOutputMaze) {
        int mazeRowCount = -1;
        for (int i = 0; i < theInputMaze.length; i++) {
            boolean rowHasRooms = false;
            int mazeColCount = -1;
            for (int j = 0; j < theInputMaze[0].length; j++) {
                char charGrab = theInputMaze[i][j];
                switch (charGrab) {
                    case '□' -> {
                        if (!rowHasRooms) {
                            mazeRowCount++;
                        }
                        mazeColCount++;
                        rowHasRooms = true;
                        theOutputMaze[mazeRowCount][mazeColCount] = roomAdjacent(theInputMaze, new Room(), i, j);
                    }
                    case 'E' -> {
                        if (!rowHasRooms) {
                            mazeRowCount++;
                        }
                        rowHasRooms = true;
                        mazeColCount++;
                        theOutputMaze[mazeRowCount][mazeColCount] = roomAdjacent(theInputMaze, new Room(), i, j);
                        myEntranceRow = mazeRowCount;
                        myEntranceColumn = mazeColCount;
                    }
                    case '▨' -> {
                        if (!rowHasRooms) {
                            mazeRowCount++;
                        }
                        rowHasRooms = true;
                        mazeColCount++;
                        theOutputMaze[mazeRowCount][mazeColCount] = roomAdjacent(theInputMaze, new Room(), i, j);
                        myExitRow = mazeRowCount;
                        myExitColumn = mazeColCount;
                    }
                }
            }
        }
        return theOutputMaze;
    }

    private Room roomAdjacent(char[][] theMaze, Room theRoom, int theRow, int theCol) {
        if (theRow - 1 < 0 || theMaze[theRow - 1][theCol] != '|') { // north door
            theRoom.getMyDoor(Direction.NORTH).setNonPassable(true);
        }
        if (theRow + 1 >= theMaze.length || theMaze[theRow + 1][theCol] != '|') { // south door
            theRoom.getMyDoor(Direction.SOUTH).setNonPassable(true);
        }
        if (theCol - 1 < 0 || theMaze[theRow][theCol - 1] != '-') { // west door
            theRoom.getMyDoor(Direction.WEST).setNonPassable(true);
        }
        if (theCol + 1 >= theMaze[0].length || theMaze[theRow][theCol + 1] != '-') { // east door
            theRoom.getMyDoor(Direction.EAST).setNonPassable(true);
        }
        return theRoom;
    }

    void checkEntExitGen() {
        while (myEntranceRow == myExitRow && myEntranceColumn == myExitColumn) {
            myExitRow = generateNumber(myMaze.length);
            myExitColumn = generateNumber(myMaze[0].length);
        }
    }

    /**
     * If the room at parameter row and column is on the edge of the maze,
     * sets the doors leading out-of-bounds state to true.
     * @param theRow
     * @param theCol
     */
    void roomOutOfBounds(int theRow, int theCol) {
        if (theRow - 1 < 0) {
            myMaze[theRow][theCol].getMyDoor(Direction.NORTH).setNonPassable(true);
        } else if (theRow + 1 >= myMaze.length) {
            myMaze[theRow][theCol].getMyDoor(Direction.SOUTH).setNonPassable(true);
        }
        if (theCol - 1 < 0) {
            myMaze[theRow][theCol].getMyDoor(Direction.WEST).setNonPassable(true);
        } else if (theCol + 1 >= myMaze[0].length) {
            myMaze[theRow][theCol].getMyDoor(Direction.EAST).setNonPassable(true);
        }
    }

    /**
     * Getter method for the Tile
     * @param num index of Tile
     * @return Tile
     */
    public Room[] getTile(final int num) {
        if (myMaze[num] == null) {
            throw new IllegalArgumentException("Tile cannot be null");
        }
        return myMaze[num];
    }

    /**
     * Returns a string representation of the maze.
     * The player's location is represented by "▣", the exit by "▨", and empty rooms by "□".
     *
     * @return a string representation of the maze
     */
    @Override
    public String toString() {
        StringBuilder mazeString = new StringBuilder();
        for (int i = 0; i < myMaze.length; i++) {
            for (int j = 0; j < myMaze[0].length; j++) {
                boolean specialChar = false;
                if (Player.getInstance().getMyLocationRow() == i && Player.getInstance().getMyLocationCol() == j) {
                    mazeString.append("▣");
                    specialChar = true;
                }
                if (myExitRow == i && myExitColumn == j && !specialChar){
                    mazeString.append("▨");
                    specialChar = true;
                }
                if (myEntranceRow == i && myEntranceColumn == j && !specialChar) {
                    mazeString.append("E");
                    specialChar = true;
                }
                if (!specialChar) {
                    mazeString.append("□");
                }
            }
            mazeString.append("\n");
        }
        return mazeString.toString();
    }
}
