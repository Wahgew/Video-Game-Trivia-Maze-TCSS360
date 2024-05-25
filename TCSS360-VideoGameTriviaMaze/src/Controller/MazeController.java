package Controller;

import Model.*;
import View.GameFrame;
import View.GamePanel;
import View.MovementButtonPanel;
import View.QuestionPanel;

import javax.swing.*;

public class MazeController {
    public static void main(String[] args) {
        // Instantiating Singleton Instances for first time.
        //Maze.getInstance("MaxDoorsLayout.txt");
        //Maze.getInstance("ZigZagLayout.txt"); // testing layout generation from .txt
        //Maze.getInstance("MazeyMazeLayout.txt");
        //Maze.getInstance();
        //Player.getInstance();
        GameFrame mazeFrame = new GameFrame();

        //mazeFrame.getMyGamePanel().updateButtonStatus(); // need to run updateButtonStatus when player location changes.
//        try { // testing room variation grab and popup TODO: this is instantiating the maze, needs to wait for maze selection.
//            BufferedImage mapImage = ImageIO.read(new File(Maze.getInstance().getMyRoom(Player.getInstance().getMyLocationRow(), Player.getInstance().getMyLocationCol()).getRoomFileName()));
//            JOptionPane.showMessageDialog(null, new JLabel(new ImageIcon(mapImage)));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

//    /**
//     * Called when "New Game" button is pressed on Main Menu
//     * TEMPORARY IMPLEMENTATION, JUST TO CHECK FUNCTIONALITY OF MODEL.
//     * @param theMazeFrame the underlying GameFrame
//     */
//    public static void promptAnswer(GameFrame theMazeFrame ) {
//        while (theMazeFrame.getGamePanelFocus()) { // THIS WHILE LOOP IS ONLY FOR TESTING CURRENTLY TODO: REMOVE THIS WHEN NOT NEEDED
//            String movement = JOptionPane.showInputDialog(Maze.getInstance() + "\n"
//                    + Maze.getInstance().getMyRoom(Player.getInstance().getMyLocationRow(),
//                    Player.getInstance().getMyLocationCol()));
//            Player.getInstance().movePlayer(Integer.parseInt(movement));
//            theMazeFrame.getMyGamePanel().updateButtonStatus();
//        }
//    }

    public void handlePlayerMovement(Direction theDirection, GamePanel theGamePanel) {
        Player player = Player.getInstance();
        int playerRow = player.getMyLocationRow();
        int playerCol = player.getMyLocationCol();
        player.setMyDirection(theDirection);
        Door door = Maze.getInstance().getMyRoom(playerRow, playerCol).getMyDoor(theDirection);

        if (door != null && !door.getMyAttemptStatus()) {
            displayQuestionPanel(door, theGamePanel);
            theGamePanel.updateRoomImage();
            theGamePanel.getMyMovementButtonPanel().checkButtons();
        } else {
            player.movePlayer(theDirection);
            theGamePanel.getMyMovementButtonPanel().setButtonsState(true);
            theGamePanel.updateRoomImage();
            theGamePanel.getMyMovementButtonPanel().checkButtons();
        }

        // Door is unlocked or question not attempted, allow movement
        //player.movePlayer(direction); // check player move attempt move: first gets questions correctness then move.
        // TODO: ask the questions, then you call question attempted. based on the correctess -> allow them to move call player.move();

        // Check if the move was towards a door
//        if (!door.getMyAttemptStatus()) {
//            // Door hasn't been attempted, display the question panel
//            displayQuestionPanel(door, gamePanel);
//        }

//        try { // TODO: THIS IS FOR TESTING, remove when Room image is properly added.
//            BufferedImage mapImage = ImageIO.read(new File(Maze.getInstance().getMyRoom(Player.getInstance().getMyLocationRow(), Player.getInstance().getMyLocationCol()).getRoomFileName()));
//            JOptionPane.showMessageDialog(null, new JLabel(new ImageIcon(mapImage)));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

    private void displayQuestionPanel(Door door, GamePanel gamePanel) {
        SwingUtilities.invokeLater(() -> {
            // Create and display the question panel
            new QuestionPanel(door ,gamePanel); //TODO: if error just ignore the null
            //questionPanel.popUpUI();
        });
    }
}
