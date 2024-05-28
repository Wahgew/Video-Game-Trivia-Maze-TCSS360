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

    private boolean myLeadsOutOfBounds; // not actually useful? maybe remove this

    private final QuestionAnswerDatabase myQdb;
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

    public Direction getMyDirection() {
        return myDirection;
    }

    /**
     * Gets the attempt status of the door.
     *
     * @return true if an attempt has been made to interact with the door, false otherwise
     */
    public boolean getMyAttemptStatus() {
        return myAttemptStatus;
    }
    public boolean getMyLeadsOutOfBounds() {
        return myLeadsOutOfBounds;
    }
    public boolean getMyLockIconStatus() {
        return (myLockStatus && myAttemptStatus && !myLeadsOutOfBounds);
    }
    public String getMyMovementIcon() {
        String caseIcon = "/Resource/"; // default icon
        //String caseIcon = "/Resource/";
        if (myLockStatus && myAttemptStatus && !myLeadsOutOfBounds) {
            caseIcon += (myDirection.toString() + "Locked.png"); // locked icon
        } else if (myLockStatus & !myAttemptStatus && !myLeadsOutOfBounds) {
            caseIcon += (myDirection.toString() + "Question.png"); // question icon
        } else if (!myLockStatus & !myAttemptStatus && !myLeadsOutOfBounds) {
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

    void setMyLeadsOutOfBounds(boolean theOOBStatus) {
        myLeadsOutOfBounds = theOOBStatus;
        myLockStatus = theOOBStatus;
        myAttemptStatus = theOOBStatus;
    }

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

    //TODO: this method may need the row, col, and direction of player
    public Question askQuestion() {
        myQuestion = QuestionAnswerDatabase.getInstance().getRandomQuestion();
        return myQuestion;
    }

    public static class DoorSerializer extends StdSerializer<Door> {
        public DoorSerializer() {
            this(null);
        }

        public DoorSerializer(Class<Door> t) {
            super(t);
        }

        @Override
        public void serialize(Door door, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeBooleanField("myLockStatus", door.getMyLockStatus());
            jsonGenerator.writeBooleanField("myAttemptStatus", door.getMyAttemptStatus());
            jsonGenerator.writeBooleanField("myLeadsOutOfBounds", door.getMyLeadsOutOfBounds());
            jsonGenerator.writeStringField("myDirection", door.myDirection.toString());
            jsonGenerator.writeBooleanField("myLockStatus", door.getMyLockIconStatus());
            jsonGenerator.writeStringField("myMovementIcon", door.getMyMovementIcon());
            jsonGenerator.writeEndObject();
        }
    }

    public static class DoorDeserializer extends StdDeserializer<Door> {
        public DoorDeserializer() {
            this(null);
        }

        public DoorDeserializer(Class<?> vc) {
            super(vc);
        }

        @Override
        public Door deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            JsonNode node = jsonParser.getCodec().readTree(jsonParser);

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

    @Override
    public String toString() {
        return "Door{" +
                "myLockStatus=" + myLockStatus +
                ", myAttemptStatus=" + myAttemptStatus +
                ", myLeadsOutofBounds=" + myLeadsOutOfBounds +
                '}';
    }
}
