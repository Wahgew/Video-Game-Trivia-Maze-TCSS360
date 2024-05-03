package View;

import Model.Player;

import java.awt.*;
import java.io.Serializable;

public class PlayerManager implements Serializable {

    private KeyboardsHandler myKeyboardsHandler;
    private GamePanel myGamePanel;
    private Player myPlayer;
    private int myX;
    private int myY;
    private String myDirection;

    private Rectangle mySolidAreas;
    private int mySolidAreasDefualtX;
    private int mySolidAreasDefualtY;

    public PlayerManager(final KeyboardsHandler theKeyboardsHandler,
                         final GamePanel theGamePanel, final Player thePlayer) {
        if (theKeyboardsHandler == null || theGamePanel == null || thePlayer == null) {
            throw new IllegalArgumentException("Please enter non-null parameters");
        }
        myPlayer = thePlayer;
        myGamePanel = theGamePanel;
        myKeyboardsHandler = theKeyboardsHandler;


    }
    public void setPlayerImageIcon() {
        //update the sprites movement image when we press the keys left right movement
    }
    public void draw(final Graphics g) {
        //draw the sprite 2D
    }
    public void updateSpriteKeyPressed() {}

    public int getX() {
        return myX;
    }
    public int getY() {
        return myY;
    }
    public String getDirection() {
        return myDirection;
    }
    public Rectangle getMySolidAreas() {
        return mySolidAreas;
    }
    public void setMySolidAreasDefualtX(final int theAreaX) {
        mySolidAreas.x = theAreaX;
    }
    public void setMySolidAreasDefualtY(final int theAreaY) {
        mySolidAreas.y = theAreaY;
    }
    public int getMySolidAreasDefualtX() {
        return mySolidAreasDefualtX;
    }
    public int getMySolidAreasDefualtY() {
        return mySolidAreasDefualtY;
    }
    public Player getPlayer() {
        return myPlayer;
    }



}
