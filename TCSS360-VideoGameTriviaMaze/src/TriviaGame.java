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

        System.out.println();
        System.out.println("Player Row Location: " + Player.getInstance().getMyLocationRow());
        System.out.println("Player Col Location: " + Player.getInstance().getMyLocationCol());
    }
}