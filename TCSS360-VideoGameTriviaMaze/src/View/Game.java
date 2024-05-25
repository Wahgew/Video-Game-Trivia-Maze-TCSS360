package View;

import Model.Player;

import java.io.Serializable;

public class Game implements Serializable {
    private Player myPlayer;
    private PlayerManager myPlayerManager;
    private KeyboardsHandler myKeyHandler;
    private transient PlayerCollision myCollisionChecker;

    public Game(final GamePanel theGamePanel) {
        myKeyHandler = new KeyboardsHandler();
        myPlayer = Player.getInstance();
        myPlayerManager = new PlayerManager(myKeyHandler, theGamePanel, myPlayer);
        myCollisionChecker = new PlayerCollision(theGamePanel);
    }
    public void setMyCollisionChecker(GamePanel theGamePanel) {
        if (theGamePanel == null) {
            throw new IllegalArgumentException("GamePanel cannot be null");
        }
        myCollisionChecker = new PlayerCollision(theGamePanel);
    }
    public PlayerCollision getMyCollisionChecker() {
        return myCollisionChecker;
    }
    public KeyboardsHandler getKeyHandler() {
        return myKeyHandler;
    }
    public Player getMyPlayer() {
        return myPlayer;
    }
    public PlayerManager getMyPlayerManager() {
        return myPlayerManager;
    }

}
