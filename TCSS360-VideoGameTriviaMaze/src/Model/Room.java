package Model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;

/**
 * Room class represents a room withing a maze
 * Each room has multiple doors max 4, allowing access to adjective rooms.
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
     * The soft lock status of the door.
     */
    private static boolean mySoftLock;

    /**
     * Constructs a new Room object with four doors.
     */
    Room() {
        myDoors = new Door[4];
        for (int i = 0; i < 4; i++) {
            myDoors[i] = new Door(Direction.getDirectionInt(i));
        }
        mySoftLock = false;
    }

    /**
     * Checks if the room is soft locked, i.e., all doors are inaccessible.
     *
     * @return true if the room is soft locked, false otherwise
     */
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

    /**
     * Returns the door in the specified direction.
     *
     * @param theDirection the direction of the door to retrieve
     * @return the door in the specified direction
     */
    public Door getMyDoor(Direction theDirection) {
        return myDoors[theDirection.getMyValue()];
    }

    /**
     * Returns the filename of the image representing the room based on its doors.
     *
     * @return the filename of the image representing the room
     */
    public String getRoomFileName() {
        String mazeFile = "maze_";
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

    /**
     * Retrieves the soft lock status of the room.
     *
     * @return the soft lock status of the room
     */
    public static boolean getSoftLock() {
        return mySoftLock;
    }

    /**
     * Sets the doors of the room.
     *
     * @param doors the array of doors to set
     * @throws IllegalArgumentException if the number of doors provided is not exactly four
     */
    public void setDoors(Door[] doors) {
        if (doors.length != 4) {
            throw new IllegalArgumentException("A room must have exactly 4 doors.");
        }
        System.arraycopy(doors, 0, myDoors, 0, doors.length);
    }

    /**
     * A serializer for converting Room objects into JSON format.
     */
    public static class RoomSerializer extends StdSerializer<Room> {

        /**
         * Constructs a RoomSerializer object.
         */
        public RoomSerializer() {
            this(null);
        }

        /**
         * Constructs a RoomSerializer object.
         *
         * @param the the type of Room
         */
        public RoomSerializer(Class<Room> the) {
            super(the);
        }

        /**
         * Serializes a Room object into JSON format.
         *
         * @param theRoom         the Room object to serialize
         * @param theGen          the JSON generator
         * @param theProvider     the serializer provider
         * @throws IOException if an I/O error occurs during serialization
         */
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

    /**
     * A deserializer for converting JSON data into Room objects.
     */
    public static class RoomDeserializer extends StdDeserializer<Room> {

        /**
         * Constructs a RoomDeserializer object.
         */
        public RoomDeserializer() {
            this(null);
        }

        /**
         * Constructs a RoomDeserializer object.
         *
         * @param vc the type to deserialize
         */
        public RoomDeserializer(Class<?> vc) {
            super(vc);
        }

        /**
         * Deserializes JSON data into a Room object.
         *
         * @param theJP      the JSON parser
         * @param theCtxt    the deserialization context
         * @return the deserialized Room object
         * @throws IOException if an I/O error occurs during deserialization
         */
        @Override
        public Room deserialize(JsonParser theJP, DeserializationContext theCtxt) throws IOException {
            JsonNode node = theJP.getCodec().readTree(theJP);
            JsonNode doorsNode = node.get("doors");
            if (doorsNode == null || doorsNode.size() != 4) {
                throw new IOException("Invalid 'doors' node in JSON data.");
            }

            Door[] doors = new Door[4];
            for (int i = 0; i < 4; i++) {
                JsonNode doorNode = doorsNode.get(i);
                doors[i] = theJP.getCodec().treeToValue(doorNode, Door.class);
            }

            Room room = new Room();
            room.setDoors(doors);
            return room;
        }
    }

    /**
     * To string of a room object
     *
     * @return returns room status of doors.
     */
    @Override
    public String toString() {
        StringBuilder roomString = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            roomString.append(i).append(" = ").append("Door ").append(Direction.getDirectionInt(i))
                    .append(" Attempt Status: ").append(getMyDoor(Direction.getDirectionInt(i))).append("\n");
        }
        return roomString.toString();
    }
}
