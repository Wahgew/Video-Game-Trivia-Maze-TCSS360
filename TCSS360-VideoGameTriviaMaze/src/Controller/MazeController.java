package Controller;

import Model.Maze;
import Model.Player;
import Model.QuestionAnswerDatabase;
import View.GameFrame;
import View.WelcomeScreen;
import View.GamePanel;

import javax.swing.*;

public class MazeController {
    static QuestionAnswerDatabase myQuestionAnswerDatabase;
    public static void main(String[] args) {
        // Instantiating Singleton Instances for first time.
        Maze.getInstance();
        Player.getInstance();
        myQuestionAnswerDatabase = new QuestionAnswerDatabase();
        GameFrame mazeFrame = new GameFrame();
        mazeFrame.getMyGamePanel().updateButtonStatus(); // need to run updateButtonStatus when player location changes.
    }
    public static QuestionAnswerDatabase getQuestionDatabase() {
        return myQuestionAnswerDatabase;
    }
}
