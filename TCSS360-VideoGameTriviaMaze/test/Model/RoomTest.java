package Model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RoomTest {

    @Test
    void softLockCheck() {
        Room room = new Room();
        assertFalse(room.softLockCheck()); // Ensure that the room is not soft locked initially
    }

    @Test
    void getMyDoor() {
        Room room = new Room();

        // Test getting a door in a specific direction
        assertNotNull(room.getMyDoor(Direction.NORTH));
    }

    @Test
    void getRoomFileName() {
        Room room = new Room();

        // Test getting the room's file name
        assertNotNull(room.getRoomFileName());
    }

    @Test
    void getSoftLock() {
        // Ensure that the soft lock status is initially false
        assertFalse(Room.getSoftLock());
    }

    @Test
    void setDoors() {
        Room room = new Room();

        Door[] doors = new Door[4];
        for (int i = 0; i < 4; i++) {
            doors[i] = new Door(Direction.getDirectionInt(i));
        }

        room.setDoors(doors);

        // Ensure that the doors are set correctly
        assertEquals(doors[0], room.getMyDoor(Direction.NORTH));
        assertEquals(doors[1], room.getMyDoor(Direction.EAST));
        assertEquals(doors[2], room.getMyDoor(Direction.SOUTH));
        assertEquals(doors[3], room.getMyDoor(Direction.WEST));
    }

    @Test
    void testToString() {
        Room room = new Room();

        // Ensure that toString() method does not throw an exception
        assertDoesNotThrow(() -> {
            room.toString();
        });
        assertEquals("0 = Door up Attempt Status: Door{myLockStatus=true, myAttemptStatus=false, myLeadsOutofBounds=false}\n" +
                "1 = Door right Attempt Status: Door{myLockStatus=true, myAttemptStatus=false, myLeadsOutofBounds=false}\n" +
                "2 = Door down Attempt Status: Door{myLockStatus=true, myAttemptStatus=false, myLeadsOutofBounds=false}\n" +
                "3 = Door left Attempt Status: Door{myLockStatus=true, myAttemptStatus=false, myLeadsOutofBounds=false}\n", room.toString());
    }
}
