package Controller;

import Model.*;
import View.*;

import javax.swing.*;

public class MazeController {
    static GameFrame myGame;
    public static void main(String[] args) {
        myGame = new GameFrame();
    }

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
        }
    }

    private void displayQuestionPanel(Door door, GamePanel gamePanel) {
        myGame.getMyLeftUIGamePanel().getMySaveGameButton().setEnabled(false);
        myGame.getMyLeftUIGamePanel().getMySwitchToWelcomeScreenButton().setEnabled(false);
        SwingUtilities.invokeLater(() -> new QuestionPanel(door ,gamePanel));
    }
}
