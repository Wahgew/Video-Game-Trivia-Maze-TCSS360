package Controller;

import Model.Maze;
import Model.Player;
import Model.QuestionAnswerDatabase;
import View.GameFrame;
import View.WelcomeScreen;
import View.GamePanel;

import javax.swing.*;

public class MazeController {
    public static void main(String[] args) {
        // Instantiating Singleton Instances for first time.
        Maze.getInstance("ZigZagLayout.txt"); // testing layout generation from .txt
        //Maze.getInstance();
        Player.getInstance();
        GameFrame mazeFrame = new GameFrame();
        mazeFrame.getMyGamePanel().updateButtonStatus(); // need to run updateButtonStatus when player location changes.
    }

    /**
     * Called when "New Game" button is pressed on Main Menu
     * TEMPORARY IMPLEMENTATION, JUST TO CHECK FUNCTIONALITY OF MODEL.
     * @param theMazeFrame the underlying GameFrame
     */
    public static void promptAnswer(GameFrame theMazeFrame ) {
        while (theMazeFrame.getGamePanelFocus()) { // THIS WHILE LOOP IS ONLY FOR TESTING CURRENTLY TODO: REMOVE THIS WHEN NOT NEEDED
            String movement = JOptionPane.showInputDialog(Maze.getInstance() + "\n"
                    + Maze.getInstance().getMyRoom(Player.getInstance().getMyLocationRow(),
                    Player.getInstance().getMyLocationCol()));
            Player.getInstance().movePlayer(Integer.parseInt(movement));
        }
    }
}
