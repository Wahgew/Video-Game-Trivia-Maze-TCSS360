package Controller;

import Model.*;
import View.*;

import javax.swing.*;

/**
 * The MazeController class handles the interaction between the player and the game.
 * It manages the player's movements within the maze and handles the display of
 * questions when the player encounters doors.
 *
 * @author Peter W Madin, Ken Egawa, Sopheanith Ny
 * @version 6/7/2024
 */
public class MazeController {
    /**
     * The main game frame that serves as the primary window of the game.
     */
    static GameFrame myGame;

    /**
     * The main method initializes the game by creating an instance of GameFrame.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        myGame = new GameFrame();
    }

    /**
     * Handles the player's movement in the specified direction. If the player encounters
     * a door, it displays a question panel. If there is no door or the door has already
     * been attempted, the player moves to the new location.
     *
     * @param theDirection The direction in which the player wants to move.
     * @param theGamePanel The game panel that displays the current state of the game.
     */
    public void handlePlayerMovement(Direction theDirection, GamePanel theGamePanel) {
        Player player = Player.getInstance();
        int playerRow = player.getMyLocationRow();
        int playerCol = player.getMyLocationCol();
        player.setMyDirection(theDirection);
        Door door = Maze.getInstance().getMyRoom(playerRow, playerCol).getMyDoor(theDirection);

         if (door != null && !door.getMyAttemptStatus()) {
            displayQuestionPanel(door, theGamePanel);
            theGamePanel.updateRoomImage();
        } else {
            player.movePlayer(theDirection);
            theGamePanel.updateRoomImage();
            theGamePanel.getMyMovementButtonPanel().checkButtons();
            theGamePanel.getMyMovementButtonPanel().checkIcons();
            myGame.fadeScreen();
        }
    }

    /**
     * Displays a question panel when the player encounters a door. It also disables
     * certain buttons in the UI while the question panel is active.
     *
     * @param door The door that the player has encountered.
     * @param gamePanel The game panel that displays the current state of the game.
     */
    private void displayQuestionPanel(Door door, GamePanel gamePanel) {
        myGame.getMyLeftUIGamePanel().getMySaveGameButton().setEnabled(false);
        myGame.getMyLeftUIGamePanel().getMySwitchToWelcomeScreenButton().setEnabled(false);
        SwingUtilities.invokeLater(() -> new QuestionPanel(door ,gamePanel));
    }
}
