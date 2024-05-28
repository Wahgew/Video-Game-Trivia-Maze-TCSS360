package Model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.awt.image.BufferedImage;
import java.io.IOException;

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

    private static boolean mySoftLock;
    /**
     * Constructs a new Room object with four doors.
     *
     * <p>TODO: Currently, all rooms are created with four doors. Each "doorway" technically has two doors,
     *      but this behavior may need to be adjusted.</p>
     */
    Room() {
        myDoors = new Door[4];
        for (int i = 0; i < 4; i++) {
            myDoors[i] = new Door(Direction.getDirectionInt(i));
        }
        mySoftLock = false;
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
    public boolean softLockCheck() {
        boolean softLocked = true;
        for (int i = 0; i < myDoors.length; i++) {
            if (Player.getInstance().validPlayerMove(Direction.getDirectionInt(i))) {
                softLocked = false;
                break;
            }
        }
        mySoftLock = softLocked;
        return softLocked;
    }

    public static boolean getSoftLock() {
        return mySoftLock;
    }

    public void setImage(BufferedImage read) { // TODO: DO WE NEED TO REMOVE THIS???

    }

    public void setDoors(Door[] doors) {
        if (doors.length != 4) {
            throw new IllegalArgumentException("A room must have exactly 4 doors.");
        }
        System.arraycopy(doors, 0, myDoors, 0, doors.length);
    }

    public static class RoomSerializer extends StdSerializer<Room> {
        public RoomSerializer() {
            this(null);
        }

        public RoomSerializer(Class<Room> t) {
            super(t);
        }

        @Override
        public void serialize(Room theRoom, JsonGenerator theGen, SerializerProvider theProvider) throws IOException {
            theGen.writeStartObject();
            theGen.writeStringField("roomFileName", theRoom.getRoomFileName());
            theGen.writeArrayFieldStart("doors");
            for (Direction direction : Direction.values()) {
                Door door = theRoom.getMyDoor(direction);
                theGen.writeObject(door);
            }
            theGen.writeEndArray();
            theGen.writeEndObject();
        }
    }

    public static class RoomDeserializer extends StdDeserializer<Room> {
        public RoomDeserializer() {
            this(null);
        }

        public RoomDeserializer(Class<?> vc) {
            super(vc);
        }

        @Override
        public Room deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
            JsonNode node = jp.getCodec().readTree(jp);
            JsonNode doorsNode = node.get("doors");
            if (doorsNode == null || doorsNode.size() != 4) {
                throw new IOException("Invalid 'doors' node in JSON data.");
            }

            Door[] doors = new Door[4];
            for (int i = 0; i < 4; i++) {
                JsonNode doorNode = doorsNode.get(i);
                doors[i] = jp.getCodec().treeToValue(doorNode, Door.class);
            }

            Room room = new Room();
            room.setDoors(doors);
            return room;
        }
    }
}
