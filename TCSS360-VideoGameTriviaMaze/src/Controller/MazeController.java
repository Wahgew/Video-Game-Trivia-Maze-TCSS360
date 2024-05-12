package Controller;

import Model.*;
import View.GameFrame;
import View.GamePanel;
import View.QuestionPanel;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MazeController {
    public static void main(String[] args) {
        // Instantiating Singleton Instances for first time.
        Maze.getInstance("MaxDoorsLayout.txt");
        //Maze.getInstance("ZigZagLayout.txt"); // testing layout generation from .txt
        //Maze.getInstance();
        Player.getInstance();
        GameFrame mazeFrame = new GameFrame();
        mazeFrame.getMyGamePanel().updateButtonStatus(); // need to run updateButtonStatus when player location changes.
//        try { // testing image popup on image question
//            BufferedImage buffImage = ImageIO.read(new File("src/Resource/Images/Pokemon_Pikachu.png"));
//            BufferedImage buffImage = ImageIO.read(new File("src/Resource/Images/YoRHa_No.2_Type_B.png"));
//            JOptionPane.showMessageDialog(null, new JLabel(new ImageIcon(buffImage)));
//            JLabel picLabel = new JLabel(new ImageIcon(buffImage));
//            JOptionPane.showMessageDialog(null, picLabel);
//        } catch (IOException e){
//            throw new RuntimeException(e);
//        }
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
            theMazeFrame.getMyGamePanel().updateButtonStatus();
        }
    }

    public void handlePlayerMovement(Direction direction, GamePanel gamePanel) {
        Player player = Player.getInstance();
        int playerRow = player.getMyLocationRow();
        int playerCol = player.getMyLocationCol();
        Door door = Maze.getInstance().getMyRoom(playerRow, playerCol).getMyDoor(direction);
            // Door is unlocked or question not attempted, allow movement
            player.movePlayer(direction);

            // Check if the move was towards a door
            if (!door.getMyAttemptStatus()) {
                // Door hasn't been attempted, display the question panel
                displayQuestionPanel(door, gamePanel);
            }
    }

    private void displayQuestionPanel(Door door, GamePanel gamePanel) {
        SwingUtilities.invokeLater(() -> {
            // Create and display the question panel
            QuestionPanel questionPanel = new QuestionPanel(door, gamePanel);
            questionPanel.popUpUI();
        });
    }



}
