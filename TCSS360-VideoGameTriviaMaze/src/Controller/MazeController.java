package Controller;

import Model.Maze;
import Model.Player;
import View.GameFrame;
import View.WelcomeScreen;
import View.GamePanel;

import javax.swing.*;

public class MazeController {
    public static void main(String[] args) {
        // Instantiating Singleton Instances for first time.
        Maze.getInstance();
        Player.getInstance();
        GameFrame mazeFrame = new GameFrame();
        mazeFrame.getMyGamePanel().updateButtonStatus();
    }
}
