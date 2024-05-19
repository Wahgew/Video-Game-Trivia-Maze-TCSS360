package Model;


import java.awt.image.BufferedImage;

/**
 * Room class represents a room withing a maze
 * Each room has multiple doors max 4, allowing access to adjective rooms.
 * TODO: Currently, all rooms are created with four doors. Each "doorway" technically has two doors,
 *  * but this behavior may need to be adjusted.
 * This class is used in conjunction with other classes to model the layout and structure of a maze.
 *
 * @author Ken Egawa
 * @author Peter Madin
 * @author Sopheanith Ny
 * @version 0.0.1 April 20, 2024
 */

public class Room {
    /**
     * Array of doors in the room.
     */
    private final Door[] myDoors;

    /**
     * Constructs a new Room object with four doors.
     *
     * <p>TODO: Currently, all rooms are created with four doors. Each "doorway" technically has two doors,
     *      but this behavior may need to be adjusted.</p>
     */
    Room() {
        myDoors = new Door[4];
        for (int i = 0; i < 4; i++) {
            myDoors[i] = new Door();
        }
    }
    /**
     * Returns the door in the specified direction.
     *
     * @param theDirection the direction of the door to retrieve
     * @return the door in the specified direction
     */
    public Door getMyDoor(Direction theDirection) {
        return myDoors[theDirection.getMyValue()];
    }

    @Override
    public String toString() {
        StringBuilder roomString = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            roomString.append(i).append(" = ").append("Door ").append(Direction.getDirectionInt(i))
                    .append(" Attempt Status: ").append(getMyDoor(Direction.getDirectionInt(i))).append("\n");
        }
        return roomString.toString();
    }

    public String getRoomFileName() {
        String mazeFile = "src/Resource/MazeRooms/maze_";
        if (!getMyDoor(Direction.NORTH).getMyLeadsOutOfBounds()) {
            mazeFile += "N";
        }
        if (!getMyDoor(Direction.EAST).getMyLeadsOutOfBounds()) {
            mazeFile += "E";
        }
        if (!getMyDoor(Direction.SOUTH).getMyLeadsOutOfBounds()) {
            mazeFile += "S";
        }
        if (!getMyDoor(Direction.WEST).getMyLeadsOutOfBounds()) {
            mazeFile += "W";
        }
        mazeFile += ".png";
        return mazeFile;
    }
    public void setImage(BufferedImage read) {

    }
}
