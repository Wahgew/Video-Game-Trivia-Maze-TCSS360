package Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DoorTest {
    private Door door;

    @BeforeEach
    void setUp() {
        door = new Door(Direction.NORTH);
    }

    @Test
    void getMyLockStatus_DefaultValue_True() {
        assertTrue(door.getMyLockStatus());
    }

    @Test
    void getMyAttemptStatus_DefaultValue_False() {
        assertFalse(door.getMyAttemptStatus());
    }

    @Test
    void getMyLeadsOutOfBounds_DefaultValue_False() {
        assertFalse(door.getMyLeadsOutOfBounds());
    }

    @Test
    void getMyMovementIcon_DefaultDirection_ReturnsExpectedIcon() {
        assertEquals("/Resource/upQuestion.png", door.getMyMovementIcon());
    }

    @Test
    void setMyLockStatus_ChangedValue_ReturnsChangedValue() {
        door.setMyLockStatus(false);
        assertFalse(door.getMyLockStatus());
    }

    @Test
    void setMyAttemptStatus_ChangedValue_ReturnsChangedValue() {
        door.setMyAttemptStatus(true);
        assertTrue(door.getMyAttemptStatus());
    }

    @Test
    void askQuestion_DefaultDoor_ReturnsQuestion() {
        Door door = new Door(Direction.NORTH);
        Question question = door.askQuestion();
        assertNotNull(question);
    }

    @Test
    void locked_door_Icon() {
        door.setMyAttemptStatus(true);
        door.setMyLockStatus(true);
        assertEquals("/Resource/upLocked.png", door.getMyMovementIcon());
    }

    @Test
    void toString_DefaultDoor_ReturnsStringRepresentation() {
        String expectedString = "Door{myLockStatus=true, myAttemptStatus=false, myLeadsOutofBounds=false}";
        assertEquals(expectedString, door.toString());
    }
}
