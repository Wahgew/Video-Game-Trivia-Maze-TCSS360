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
        assertTrue(!door.getMyAttemptStatus());
    }

    @Test
    void getMyLeadsOutOfBounds_DefaultValue_False() {
        assertTrue(!door.getMyLeadsOutOfBounds());
    }

    @Test
    void getMyMovementIcon_DefaultDirection_ReturnsExpectedIcon() {
        assertEquals("/Resource/upLocked.png", door.getMyMovementIcon());
    }


    @Test
    void setMyLockStatus_ChangedValue_ReturnsChangedValue() {
        door.setMyLockStatus(false);
        assertTrue(!door.getMyLockStatus());
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
    void toString_DefaultDoor_ReturnsStringRepresentation() {
        String expectedString = "Door{myLockStatus=true, myAttemptStatus=false, myLeadsOutofBounds=false}";
        assertEquals(expectedString, door.toString());
    }
}
