import Model.Direction;
import Model.Maze;
import Model.Player;
//import Model.QuestionAnswerDatabase;

import java.util.Scanner;

public class TriviaGame {
    public static void main(String[] args) {
        Scanner testInput = new Scanner(System.in);
//        int mazeRows;
//        int mazeCols;
//        System.out.println("Input # of rows in Maze: ");
//        mazeRows = testInput.nextInt();
//        System.out.println("Input # of columns in Maze: ");
//        mazeCols = testInput.nextInt();
//        Maze.getInstance(mazeRows, mazeCols);
//        TODO: EVERYTHING HERE IS JUST TEMPORARY FOR TESTING MAZE AND PLAYER.
        Maze.getInstance(4,4);
        System.out.println(Maze.getInstance());
        System.out.println("Player Location (▣): " + Player.getInstance().getMyLocationRow() + ", "
                + Player.getInstance().getMyLocationCol());
        System.out.println("Maze Entrance Location (E): " + Maze.getInstance().getMyEntranceRow() + ", "
                + Maze.getInstance().getMyEntranceColumn());
        System.out.println("Maze Exit Location (▨): " + Maze.getInstance().getMyExitRow() + ", "
                + Maze.getInstance().getMyExitColumn());
        System.out.println("\nCurrent Room:");
        System.out.println(Maze.getInstance().getMyRoom(Player.getInstance().getMyLocationRow(),
                Player.getInstance().getMyLocationCol()));
        String userIn = "";
        while (!Player.getInstance().getMyVictory()) {
            System.out.println("Input direction (0 = N, 1 = E, 2 = S, 3 = W) to move in: ");
            userIn = testInput.nextLine();
            Player.getInstance().movePlayer(Direction.getDirectionInt(Integer.parseInt(userIn)), testInput);
            System.out.println(Maze.getInstance());

            System.out.println("Player Location (▣): " + Player.getInstance().getMyLocationRow() + ", "
                    + Player.getInstance().getMyLocationCol());
            System.out.println("Player Victory: " + Player.getInstance().getMyVictory());

            System.out.println("\nCurrent Room:");
            System.out.println(Maze.getInstance().getMyRoom(Player.getInstance().getMyLocationRow(),
                    Player.getInstance().getMyLocationCol()));
            System.out.println("\n Player Score: " + Player.getInstance().getMyScore());
        }
        // TODO:
        //  Need some way to call question construction/query when moving through non-attempted door.
        //  FIX METHODS VISIBILITIES, CHANGED SOME TO PUBLIC FOR TESTING IN THIS.
    }
}