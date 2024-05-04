package View;

import Model.Player;

import java.io.Serializable;

public class Game implements Serializable {
    private Player myPlayer;
    private PlayerManager myPlayerManager;
    private KeyboardsHandler myKeyHandler;

    public Game(final GamePanel theGamePanel) {
        myKeyHandler = new KeyboardsHandler();
        myPlayer = new Player();
        myPlayerManager = new PlayerManager(myKeyHandler, theGamePanel, myPlayer);
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
//    public int getTime() {
//        return myPlayer.getTimeLimit;
//    }
}
