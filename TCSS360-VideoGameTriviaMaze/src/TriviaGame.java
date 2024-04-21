import Model.Maze;
import Model.Player;

import java.util.Scanner;

public class TriviaGame {
    public static void main(String[] args) {
//        Scanner testInput = new Scanner(System.in);
//        int mazeRows;
//        int mazeCols;
//        System.out.println("Input # of rows in Maze: ");
//        mazeRows = testInput.nextInt();
//        System.out.println("Input # of columns in Maze: ");
//        mazeCols = testInput.nextInt();
//        Maze.getInstance(mazeRows, mazeCols);

//        EVERYTHING HERE IS JUST TEMPORARY FOR TESTING MAZE AND PLAYER.
        Maze.getInstance(4,4);
        System.out.println(Maze.getInstance());

        System.out.println("Player Location (▣): " + Player.getInstance().getMyLocationRow() + ", "
                + Player.getInstance().getMyLocationCol());
        System.out.println("Maze Entrance Location (E): " + Maze.getInstance().getMyEntranceRow() + ", "
                + Maze.getInstance().getMyEntranceColumn());
        System.out.println("Maze Exit Location (▨): " + Maze.getInstance().getMyExitRow() + ", "
                + Maze.getInstance().getMyExitColumn());

        //TODO:
        //      Look at the Player class, need to change validPlayerMove or how Room/Door class works.
        //  current implementation of Room/Door has every room with four doors, meaning each "doorway"
        //  has two doors, one from the room the player starts movement in and one in the room
        //  the player is moving to. Might need to sync the "set" of doors.
        //      Need some way to call question construction/query when moving through non-attempted door.
    }
}