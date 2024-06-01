package View;

import Model.Player;

import java.io.Serializable;

/**
 * The Game class is responsible for managing the main components of the game,
 * including the player, player manager, keyboard handler, and collision checker.
 *
 * @author Peter W Madin, Ken Egawa, Sopheanith Ny
 * @version 6/7/2024
 */
public class Game implements Serializable {

    /**
     * The player instance representing the player in the game.
     */
    private Player myPlayer;

    /**
     * The player manager responsible for managing the player.
     */
    private PlayerManager myPlayerManager;

    /**
     * The keyboard handler for handling keyboard inputs.
     */
    private KeyboardsHandler myKeyHandler;


    /**
     * Constructs a new Game instance, initializing the keyboard handler,
     * player, player manager, and collision checker.
     *
     * @param theGamePanel the GamePanel associated with this game
     */
    public Game(final GamePanel theGamePanel) {
        myKeyHandler = new KeyboardsHandler();
        myPlayer = Player.getInstance();
        myPlayerManager = new PlayerManager(myKeyHandler, theGamePanel, myPlayer);
    }

    /**
     * Sets the collision checker for the game using the provided game panel.
     *
     * @param theGamePanel the GamePanel to be used for collision checking
     * @throws IllegalArgumentException if the provided GamePanel is null
     */
    public void setMyCollisionChecker(GamePanel theGamePanel) {
        if (theGamePanel == null) {
            throw new IllegalArgumentException("GamePanel cannot be null");
        }
    }


    /**
     * Returns the keyboard handler for this game.
     *
     * @return the KeyboardsHandler instance used for handling keyboard inputs
     */
    public KeyboardsHandler getKeyHandler() {
        return myKeyHandler;
    }

    /**
     * Returns the player instance for this game.
     *
     * @return the Player instance representing the player
     */
    public Player getMyPlayer() {
        return myPlayer;
    }

    /**
     * Returns the player manager for this game.
     *
     * @return the PlayerManager instance used for managing the player
     */
    public PlayerManager getMyPlayerManager() {
        return myPlayerManager;
    }

}
