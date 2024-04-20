package Model;

enum Direction {
    NORTH(0),
    EAST(1),
    SOUTH(2),
    WEST(3);
    private final int myValue;
    Direction(int theValue) {
        this.myValue = theValue;
    }
    int getMyValue() {
        return myValue;
    }
}
