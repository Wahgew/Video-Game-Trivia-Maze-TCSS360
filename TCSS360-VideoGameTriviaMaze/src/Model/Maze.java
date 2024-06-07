package Model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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

    /**
     * Constructs a new Maze object with the specified layout.
     * @param theFileName the layout .txt file's name.
     */
    private Maze(String theFileName) {
        Scanner fileScan;
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
     * Maze constructor, used with resetMaze method.
     * @param theLayout the layout to set.
     * @param theRooms the 2d array of Rooms to set.
     */
    private Maze(String theLayout, Room[][] theRooms) {
        myLayout = theLayout;
        myRandom = null;
        myMaze = theRooms;
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
     * Getter for Singleton instance of Maze,
     * will instantiate a maze using layout if null.
     * @param theFileName the layout .txt name.
     * @return Singleton instance of Maze.
     */
    public static synchronized Maze getInstance(String theFileName) {
        if (mySingleton == null) {
            mySingleton = new Maze(theFileName);
        }
        return mySingleton;
    }

    /**
     * Resets the Maze.
     * @param theFileName the Maze layout to reset Maze to.
     */
    public static synchronized void resetMaze(String theFileName) {
        mySingleton = new Maze(theFileName);
    }

    public static synchronized void resetMaze(int theXsize, int theYsize) {
        mySingleton = new Maze(theXsize, theXsize);
    }

    /**
     * Resets the Maze, constructing a new maze w/ parameters.
     * @param theLayout the layout of the maze, .txt file.
     * @param theRoom the 2d array of Rooms.
     */
    public static synchronized void resetMaze(String theLayout, Room[][] theRoom) {
        mySingleton = new Maze(theLayout, theRoom);
    }

    /**
     * Setter for Singleton instance.
     * @param theMaze the Maze to set Singleton to.
     */
    public static synchronized void setMySingleton(Maze theMaze) {
        mySingleton = theMaze;
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

    /**
     * Returns the Maze's layout.
     * @return String of .txt file name.
     */
    public String getMyLayout() {
        return myLayout;
    }

    /**
     * Setter for Maze's entrance column position.
     * @param theEntranceColumn the Column where entrance is located.
     */
    public void setMyEntranceColumn(int theEntranceColumn) {
        myEntranceColumn = theEntranceColumn;
    }

    /**
     * Setter for Maze's entrance row position.
     * @param theEntranceRow the Row where entrance is located.
     */
    public void setMyEntranceRow(int theEntranceRow) {
        myEntranceRow = theEntranceRow;
    }

    /**
     * Setter for Maze's exit column position.
     * @param theExitColumn the Column where exit is located.
     */
    public void setMyExitColumn(int theExitColumn) {
        myExitColumn = theExitColumn;
    }

    /**
     * Setter for Maze's exit row position.
     * @param theExitRow the Row where entrance is located.
     */
    public void setMyExitRow(int theExitRow) {
        myExitRow = theExitRow;
    }

    /**
     * Default Maze instantiator, used w/ default constructor.
     */
    void mazeInstantiate() {
        for (int i = 0; i < getMyMazeRows(); i++) {
            for (int j = 0; j < getMyMazeCols(); j++) {
                myMaze[i][j] = new Room();
                if (i == 0 || i == getMyMazeRows() - 1 || j == 0 || j == getMyMazeCols() - 1) { // check if on edge of maze rows.
                    roomOutOfBounds(i, j);
                }
            }
        }
    }

    /**
     * Calls roomOutOfBounds on rooms on edge of maze.
     */
    void checkOOBMaze() {
        for (int i = 0; i < getMyMazeRows(); i++) {
            for (int j = 0; j < getMyMazeCols(); j++) {
                if (i == 0 || i == getMyMazeRows() - 1 || j == 0 || j == getMyMazeCols() - 1) { // check if on edge of maze rows.
                    roomOutOfBounds(i, j);
                }
            }
        }
    }

    /**
     * Instantiates maze, reading from input file.
     * @param theScan the Scanner reading input file.
     * @return 2d array of Room, representing Maze.
     */
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

    /**
     * Turns fileMazeInstantiate inputMaze into usable Maze.
     * @param theInputMaze the room of Char read by Scanner.
     * @param theOutputMaze the empty Maze w/ final return size.
     * @return 2d array of Room, representing Maze in .txt file.
     */
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

    /**
     * Checks if Room's door in direction is passable.
     * @param theMaze the Maze, 2d array of char.
     * @param theRoom the Room that is being checked.
     * @param theRow the Row location of the Room.
     * @param theCol the Column location of the Room.
     * @return the Room with Doors set accordingly.
     */
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

    /**
     * Generates a new Entrance/Exit if
     * they both are overlapping positions.
     */
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

    public static class MazeSerializer extends StdSerializer<Maze> {
        public MazeSerializer() {
            this(null);
        }

        public MazeSerializer(Class<Maze> t) {
            super(t);
        }

        @Override
        public void serialize(Maze maze, JsonGenerator theJgen, SerializerProvider theProvider) throws IOException {
            theJgen.writeStartObject();
            theJgen.writeStringField("myLayout", maze.getMyLayout());
            theJgen.writeNumberField("myMazeRows", maze.getMyMazeRows());
            theJgen.writeNumberField("myMazeCols", maze.getMyMazeCols());
            theJgen.writeNumberField("myEntranceRow", maze.getMyEntranceRow());
            theJgen.writeNumberField("myEntranceColumn", maze.getMyEntranceColumn());
            theJgen.writeNumberField("myExitRow", maze.getMyExitRow());
            theJgen.writeNumberField("myExitColumn", maze.getMyExitColumn());

            theJgen.writeArrayFieldStart("rooms");
            for (int i = 0; i < maze.getMyMazeRows(); i++) {
                for (int j = 0; j < maze.getMyMazeCols(); j++) {
                    theJgen.writeObject(maze.getMyRoom(i, j));
                }
            }
            theJgen.writeEndArray();
            theJgen.writeEndObject();
        }
    }

    public static class MazeDeserializer extends JsonDeserializer<Maze> {
        @Override
        public Maze deserialize(JsonParser theJsonParser, DeserializationContext theDeserialization) throws IOException {
            JsonNode node = theJsonParser.getCodec().readTree(theJsonParser);
            String layout = node.get("myLayout").asText();
            int rows = node.get("myMazeRows").asInt();
            int cols = node.get("myMazeCols").asInt();


            // Initialize the rooms array
            Room[][] rooms = new Room[rows][cols];
            JsonNode roomsNode = node.get("rooms");
            if (roomsNode == null) {
                throw new IOException("Missing 'rooms' node in JSON data.");
            }

            // Deserialize each room from the rooms array
            int index = 0;
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (index < roomsNode.size()) {
                        rooms[i][j] = theJsonParser.getCodec().treeToValue(roomsNode.get(index++), Room.class);
                    } else {
                        rooms[i][j] = null; // Handle missing room entries if necessary
                    }
                }
            }

            // Reset the maze singleton with the deserialized data
            Maze.resetMaze(layout, rooms);
            Maze.getInstance().setMyEntranceRow(node.get("myEntranceRow").asInt());
            Maze.getInstance().setMyEntranceColumn(node.get("myEntranceColumn").asInt());
            Maze.getInstance().setMyExitRow(node.get("myExitRow").asInt());
            Maze.getInstance().setMyExitColumn(node.get("myExitColumn").asInt());

            return Maze.getInstance();
        }
    }
}
