package Model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.io.Serializable;

/**
 * The Door class represents a door object with a lock and attempt status.
 *
 * This classes provides methods to access and modify the lock status and attempt status
 * the Lock status indicates wherever the door is locked or unlocked, while the attempt status\
 * signals if an attempt has been made to interact with the door.
 *
 * @author Ken Egawa
 * @author Peter Madin
 * @author Sopheanith Ny
 * @version 0.0.1 April 20, 2024
 */
public class Door implements Serializable {

    /**
     * Door lock status.
     */
    private boolean myLockStatus;

    /**
     * Question objects
     */
    private Question myQuestion;

    /**
     * Attempt status of the Door
     * Player can only attempt to pass through Door if attempt status is false.
     */
    private boolean myAttemptStatus;

    /**
     * Out-of-bounds positions.
     */
    private boolean myLeadsOutOfBounds; // not actually useful? maybe remove this

    /**
     * Question and answer database.
     */
    private final QuestionAnswerDatabase myQdb;

    /**
     * The Direction the door is facing.
     */
    private final Direction myDirection;
    /**
     * Constructs a new Door object with default locks
     * status as true (locked) and attempt status as false.
     */
    Door(final Direction theDirection) {
        myLockStatus = true;
        myAttemptStatus = false;
        myLeadsOutOfBounds = false;
        myQuestion = null;
        myDirection = theDirection;
        myQdb = QuestionAnswerDatabase.getInstance();
    }

    /**
     * Constructs a new Door object with the specified parameters.
     */
    Door(final Direction theDirection, boolean theLockStatus, boolean theAttemptStatus, boolean theLeadsOutOfBounds) {
        myLockStatus = theLockStatus;
        myAttemptStatus = theAttemptStatus;
        myLeadsOutOfBounds = theLeadsOutOfBounds;
        myQuestion = null;
        myDirection = theDirection;
        myQdb = QuestionAnswerDatabase.getInstance();
    }

    /**
     * Gets the lock status of the door.
     *
     * @return true if the door is locked, false if it is unlocked
     */
    public boolean getMyLockStatus() {
        return myLockStatus;
    }

    /**
     * Gets the attempt status of the door.
     *
     * @return true if an attempt has been made to interact with the door, false otherwise
     */
    public boolean getMyAttemptStatus() {
        return myAttemptStatus;
    }

    /**
     * Gets the out-of-bounds position.
     *
     * @return true if out of bounds, false otherwise.
     */
    public boolean getMyLeadsOutOfBounds() {
        return myLeadsOutOfBounds;
    }

    /**
     * Gets the movement button icons.
     *
     * @return the icons depending on the door status.
     */

    //This is the original.

//    public String getMyMovementIcon() {
//        String caseIcon = "/Resource/"; // default icon
//        //String caseIcon = "/Resource/";
//        if (myLockStatus && myAttemptStatus && !myLeadsOutOfBounds) {
//            caseIcon += (myDirection.toString() + "Locked.png"); // locked icon
//        } else if (myLockStatus & !myAttemptStatus && !myLeadsOutOfBounds) {
//            caseIcon += (myDirection.toString() + "Question.png"); // question icon
//        } else if (!myLockStatus & !myAttemptStatus && !myLeadsOutOfBounds) {
//            caseIcon += (myDirection.toString() + "Question.png");
//        } else {
//            caseIcon += (myDirection.toString() + "Icon.png");
//        }
//        return caseIcon;
//    }

    //This is correct logic and i did change the 135.
    public String getMyMovementIcon() {
        String caseIcon = "/Resource/"; // default icon
        if (myLockStatus && !myAttemptStatus && !myLeadsOutOfBounds) {
            caseIcon += (myDirection.toString() + "Locked.png"); // locked icon
        } else if (myLockStatus && myAttemptStatus && !myLeadsOutOfBounds) {
            caseIcon += (myDirection.toString() + "Question.png"); // question icon
        } else if (!myLockStatus && !myAttemptStatus && !myLeadsOutOfBounds) {
            caseIcon += (myDirection.toString() + "Question.png");
        } else {
            caseIcon += (myDirection.toString() + "Icon.png");
        }
        return caseIcon;
    }

    /**
     * Sets the lock status of the door.
     *
     * @param theLockStatus true to lock the door, false to unlock it
     */
    public void setMyLockStatus(boolean theLockStatus) {
        myLockStatus = theLockStatus;
    }

    /**
     * Sets the attempt status of the door.
     * Player can only attempt to pass through Door if attempt status is false.
     * @param theAttemptStatus if the Door has been attempted.
     */
    public void setMyAttemptStatus(boolean theAttemptStatus) {
        myAttemptStatus = theAttemptStatus;
    }

    /**
     * Getter method for question objects that attached the doors.
     * @return the Question objects attached.
     */
    public Question getQuestionObject() {
        return myQuestion;
    }

    /**
     * Sets the status of the door.
     *
     * @param thePassibility the passibility of the door
     */
    public void setNonPassable(boolean thePassibility) {
        myLockStatus = thePassibility;
        myAttemptStatus = thePassibility;
        myLeadsOutOfBounds = thePassibility;
    }

    /**
     * Should be called when a question has been attempted, "syncs" the state of
     * the corresponding door and adjacent room's door.
     * @param theSuccess if the question was answered correctly.
     * @param theRow the row of the room the player is attempting the question from.
     * @param theCol the column of the room the player is attempting the question from.
     * @param theDirection the Direction the player is attempting to move.
     */
    public void questionAttempted(boolean theSuccess, int theRow, int theCol, Direction theDirection) {
        if (theSuccess) {
            Maze.getInstance().getMyRoom(theRow, theCol).getMyDoor(theDirection).setMyAttemptStatus(true);
            Maze.getInstance().getMyRoom(theRow, theCol).getMyDoor(theDirection).setMyLockStatus(false);

            Maze.getInstance().getMyAdjacentRoom(theDirection, theRow, theCol)
                    .getMyDoor(Direction.getPlayerDirection(theDirection)).setMyAttemptStatus(true);
            Maze.getInstance().getMyAdjacentRoom(theDirection, theRow, theCol)
                    .getMyDoor(Direction.getPlayerDirection(theDirection)).setMyLockStatus(false);
        } else {
            Maze.getInstance().getMyRoom(theRow, theCol).getMyDoor(theDirection).setMyAttemptStatus(true);
            Maze.getInstance().getMyRoom(theRow, theCol).getMyDoor(theDirection).setMyLockStatus(true);

            Maze.getInstance().getMyAdjacentRoom(theDirection, theRow, theCol)
                    .getMyDoor(Direction.getPlayerDirection(theDirection)).setMyAttemptStatus(true);
            Maze.getInstance().getMyAdjacentRoom(theDirection, theRow, theCol)
                    .getMyDoor(Direction.getPlayerDirection(theDirection)).setMyLockStatus(true);
        }
    }

    /**
     * Asks a random question from the QuestionAnswerDatabase.
     *
     * @return the question asked
     */
    public Question askQuestion() {
        myQuestion = QuestionAnswerDatabase.getInstance().getRandomQuestion();
        return myQuestion;
    }

    /**
     * Serializer for the Door class.
     */
    public static class DoorSerializer extends StdSerializer<Door> {

        /**
         * Default constructor.
         */
        public DoorSerializer() {
            this(null);
        }

        /**
         * Constructor with a specific class.
         *
         * @param t the class of the Door
         */
        public DoorSerializer(Class<Door> t) {
            super(t);
        }

        /**
         * Serializes a Door object into JSON format.
         *
         * @param theDoor the Door object to be serialized
         * @param theJsonGenerator the JsonGenerator used to generate JSON content
         * @param theSP the provider for serializing objects
         * @throws IOException if an I/O error occurs during serialization
         */
        @Override
        public void serialize(Door theDoor, JsonGenerator theJsonGenerator, SerializerProvider theSP) throws IOException {
            theJsonGenerator.writeStartObject();
            theJsonGenerator.writeBooleanField("myLockStatus", theDoor.getMyLockStatus());
            theJsonGenerator.writeBooleanField("myAttemptStatus", theDoor.getMyAttemptStatus());
            theJsonGenerator.writeBooleanField("myLeadsOutOfBounds", theDoor.getMyLeadsOutOfBounds());
            theJsonGenerator.writeStringField("myDirection", theDoor.myDirection.toString());
            theJsonGenerator.writeStringField("myMovementIcon", theDoor.getMyMovementIcon());
            theJsonGenerator.writeEndObject();
        }
    }

    /**
     * Deserializer for the Door class.
     */
    public static class DoorDeserializer extends StdDeserializer<Door> {

        /**
         * Default constructor.
         */
        public DoorDeserializer() {
            this(null);
        }

        /**
         * Constructor with a specific class.
         *
         * @param vc the class of the Door
         */
        public DoorDeserializer(Class<?> vc) {
            super(vc);
        }

        /**
         * Deserializes a Door object from JSON data.
         *
         * @param theJsonParser the JsonParser used to parse the JSON content
         * @param theDC the context for the deserialization process
         * @return a Door object deserialized from the JSON data
         * @throws IOException if an I/O error occurs during parsing
         */
        @Override
        public Door deserialize(JsonParser theJsonParser, DeserializationContext theDC) throws IOException {
            JsonNode node = theJsonParser.getCodec().readTree(theJsonParser);

            boolean lockStatus = node.get("myLockStatus").asBoolean();
            boolean attemptStatus = node.get("myAttemptStatus").asBoolean();
            boolean leadsOutOfBounds = node.get("myLeadsOutOfBounds").asBoolean();
            String directionString = node.get("myDirection").asText();
            Direction direction = switch (directionString) {
                case "up" -> Direction.NORTH;
                case "down" -> Direction.SOUTH;
                case "left" -> Direction.WEST;
                case "right" -> Direction.EAST;
                default -> null;
            };

            return new Door(direction, lockStatus, attemptStatus, leadsOutOfBounds);
        }
    }

    /**
     * Returns a string representation of the Door object.
     *
     * @return a string representation of the Door
     */
    @Override
    public String toString() {
        return "Door{" +
                "myLockStatus=" + myLockStatus +
                ", myAttemptStatus=" + myAttemptStatus +
                ", myLeadsOutofBounds=" + myLeadsOutOfBounds +
                '}';
    }
}
