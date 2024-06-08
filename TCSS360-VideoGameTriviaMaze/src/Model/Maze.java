package Model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
 * @version 0.0.4 April 20, 2024
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
     * Private constructor for creating a Maze instance from a file.
     *
     * @param theFileName the name of the file containing the maze layout
     */
    private Maze(String theFileName) {
        Scanner fileScan;
        myRandom = null;
        try {
            InputStream inputStream = Maze.class.getResourceAsStream("/Resource/MazeLayouts/" + theFileName);
            if (inputStream != null) {
                fileScan = new Scanner(inputStream);
            } else {
                throw new FileNotFoundException("Resource file not found: " + theFileName);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        myLayout = theFileName;
        myMaze = fileMazeInstantiate(fileScan);
        checkOOBMaze();
    }

    /**
     * Private constructor for creating a Maze instance with a given layout and rooms.
     *
     * @param theLayout the layout of the maze
     * @param theRooms the rooms of the maze
     */
    private Maze(String theLayout, Room[][] theRooms) {
        myLayout = theLayout;
        myRandom = null;
        myMaze = theRooms;
    }

    /**
     * Resets the maze with a new layout from a file.
     *
     * @param theFileName the name of the file containing the new maze layout
     */
    public static synchronized void resetMaze(String theFileName) {
        mySingleton = new Maze(theFileName);
    }

    /**
     * Resets the maze with a given layout and rooms.
     *
     * @param theLayout the layout of the maze
     * @param theRoom the rooms of the maze
     */
    public static synchronized void resetMaze(String theLayout, Room[][] theRoom) {
        mySingleton = new Maze(theLayout, theRoom);
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
     * Instantiates the maze with default rooms.
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
     * Checks if rooms on the edge of the maze are out-of-bounds.
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
     * Instantiates the maze from a file using a Scanner.
     *
     * @param theScan the Scanner to read the file
     * @return a 2D array of Room objects representing the maze
     */
    Room[][] fileMazeInstantiate(Scanner theScan) {
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
     * Reads the maze layout from a char array and creates the maze.
     *
     * @param theInputMaze the input char array representing the maze layout
     * @param theOutputMaze the output Room array representing the maze
     * @return a 2D array of Room objects representing the maze
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
     * Sets the doors of a room to be non-passable if they are adjacent to out-of-bounds areas.
     *
     * @param theMaze the maze layout
     * @param theRoom the room to update
     * @param theRow the row index of the room
     * @param theCol the column index of the room
     * @return the updated room
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
     * Ensures that the entrance and exit of the maze are not the same.
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
     * @param theRow the room's row location
     * @param theCol the room's column location
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
     * Gets the singleton instance of the Maze class, initializing it if necessary.
     *
     * @param theFileName the name of the file containing the maze layout
     * @return the singleton instance of the Maze class
     */
    public static synchronized Maze getInstance(String theFileName) {
        if (mySingleton == null) {
            mySingleton = new Maze(theFileName);
        }
        return mySingleton;
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
     * Gets the layout of the maze.
     *
     * @return the layout of the maze
     */
    public String getMyLayout() {
        return myLayout;
    }

    /**
     * Sets the entrance column of the maze.
     *
     * @param theEntranceColumn the entrance column of the maze
     */
    public void setMyEntranceColumn(int theEntranceColumn) {
        myEntranceColumn = theEntranceColumn;
    }

    /**
     * Sets the entrance row of the maze.
     *
     * @param theEntranceRow the entrance row of the maze
     */
    public void setMyEntranceRow(int theEntranceRow) {
        myEntranceRow = theEntranceRow;
    }

    /**
     * Sets the exit column of the maze.
     *
     * @param theExitColumn the exit column of the maze
     */
    public void setMyExitColumn(int theExitColumn) {
        myExitColumn = theExitColumn;
    }

    /**
     * Sets the exit row of the maze.
     *
     * @param theExitRow the exit row of the maze
     */
    public void setMyExitRow(int theExitRow) {
        myExitRow = theExitRow;
    }

    /**
     * Sets the singleton instance of the Maze class.
     *
     * @param theMaze the new singleton instance of the Maze class
     */
    public static synchronized void setMySingleton(Maze theMaze) {
        mySingleton = theMaze;
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

    /**
     * Serializer for the Maze class to convert Maze objects into JSON format.
     */
    public static class MazeSerializer extends StdSerializer<Maze> {

        /**
         * Default constructor for MazeSerializer.
         */
        public MazeSerializer() {
            this(null);
        }

        /**
         * Constructor for MazeSerializer with a specified class type.
         *
         * @param the the class type
         */
        public MazeSerializer(Class<Maze> the) {
            super(the);
        }

        /**
         * Serializes a Maze object into JSON format.
         *
         * @param theMaze the Maze object to serialize
         * @param theJgen the JSON generator
         * @param theProvider the serializer provider
         * @throws IOException if an I/O error occurs
         */
        @Override
        public void serialize(Maze theMaze, JsonGenerator theJgen, SerializerProvider theProvider) throws IOException {
            theJgen.writeStartObject();
            theJgen.writeStringField("myLayout", theMaze.getMyLayout());
            theJgen.writeNumberField("myMazeRows", theMaze.getMyMazeRows());
            theJgen.writeNumberField("myMazeCols", theMaze.getMyMazeCols());
            theJgen.writeNumberField("myEntranceRow", theMaze.getMyEntranceRow());
            theJgen.writeNumberField("myEntranceColumn", theMaze.getMyEntranceColumn());
            theJgen.writeNumberField("myExitRow", theMaze.getMyExitRow());
            theJgen.writeNumberField("myExitColumn", theMaze.getMyExitColumn());

            theJgen.writeArrayFieldStart("rooms");
            for (int i = 0; i < theMaze.getMyMazeRows(); i++) {
                for (int j = 0; j < theMaze.getMyMazeCols(); j++) {
                    theJgen.writeObject(theMaze.getMyRoom(i, j));
                }
            }
            theJgen.writeEndArray();
            theJgen.writeEndObject();
        }
    }

    /**
     * Deserializer for the Maze class to convert JSON format into Maze objects.
     */
    public static class MazeDeserializer extends JsonDeserializer<Maze> {

        /**
         * Deserializes a JSON object into a Maze object.
         *
         * @param theJsonParser the JSON parser
         * @param theDeserialization the deserialization context
         * @return the deserialized Maze object
         * @throws IOException if an I/O error occurs
         */
        @Override
        public Maze deserialize(JsonParser theJsonParser, DeserializationContext theDeserialization) throws IOException {
            JsonNode node = theJsonParser.getCodec().readTree(theJsonParser);
            String layout = node.get("myLayout").asText();
            int rows = node.get("myMazeRows").asInt();
            int cols = node.get("myMazeCols").asInt();

            // Initialize the room arrays
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
