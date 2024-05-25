package View;

import Model.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;

public class PlayerManager implements Serializable {
    private transient BufferedImage myUpIcon1, myUpIcon2, myDownIcon1, myDownIcon2,
            myRightIcon1, myRightIcon2, myLeftIcon1, myLeftIcon2;
    private KeyboardsHandler myKeyboardsHandler;
    private GamePanel myGamePanel;
    private Player myPlayer;
    private int myX;
    private int myY;
    private static final int WORLD_POSITION = (ScreenSetting.TILE_SIZE / 2);
    private String myDirection;
    private Rectangle mySolidAreas;
    private int mySolidAreasDefaultX;
    private int mySolidAreasDefaultY;

    private int mySpritesCounter;
    private int mySpritesNum;

    private int playerSpeed = 3;

    public PlayerManager(final KeyboardsHandler theKeyboardsHandler,
                         final GamePanel theGamePanel, final Player thePlayer) {
        if (theKeyboardsHandler == null || theGamePanel == null || thePlayer == null) {
            throw new IllegalArgumentException("Please enter non-null parameters");
        }
        myPlayer = thePlayer;
        myGamePanel = theGamePanel;
        myKeyboardsHandler = theKeyboardsHandler;
        mySpritesNum = 1;
        mySpritesCounter = 0;
        setDefaultValues();
        setPlayerImageIcon();

    }
    public void setDefaultValues() {
        // Set the initial position of the player to the center
        int panelWidth = myGamePanel.getWidth();
        int panelHeight = myGamePanel.getHeight();
        int centerX = panelWidth / 2;
        int centerY = panelHeight / 2;

        //TODO: Peter tapped out on making this player sprite center not using magic numbers
        // He may come back and fix it or leave it as is.
        setMyXDefault(ScreenSetting.SPRITE_CENTER_WIDTH / 2);
        setMyYDefault(ScreenSetting.SPRITE_CENTER_HEIGHT / 2);
        mySolidAreas = new Rectangle(6,14,44,44);
        mySolidAreasDefaultX = mySolidAreas.x;
        mySolidAreasDefaultY = mySolidAreas.y;
        myDirection = "DOWN";
    }
    public void setPlayerImageIcon() {
        //update the sprites movement image when we press the keys left right movement
        try {
            myUpIcon1 = ImageIO.read(getClass().getResourceAsStream("/Resource/Up1.png"));
            myUpIcon2 = ImageIO.read(getClass().getResourceAsStream("/Resource/Up2.png"));
            myDownIcon1 = ImageIO.read(getClass().getResourceAsStream("/Resource/Down1.png"));
            myDownIcon2 = ImageIO.read(getClass().getResourceAsStream("/Resource/Down2.png"));
            myLeftIcon1 = ImageIO.read(getClass().getResourceAsStream("/Resource/Left1.png"));
            myLeftIcon2 = ImageIO.read(getClass().getResourceAsStream("/Resource/Left2.png"));
            myRightIcon1 =  ImageIO.read(getClass().getResourceAsStream("/Resource/Right1.png"));
            myRightIcon2 = ImageIO.read(getClass().getResourceAsStream("/Resource/Right2.png"));

        } catch (IOException exception)  {
            exception.printStackTrace();
        }
    }
    public void draw(Graphics2D theG, int centerX, int centerY) {
        //draw the sprite 2D
        BufferedImage image = null;
        switch(myDirection) {
            case "UP":
                if (mySpritesNum == 1) {
                    image = myUpIcon1;
                }
                if (mySpritesNum == 2) {
                    image = myUpIcon2;
                }
                break;
            case "DOWN":
                if (mySpritesNum == 1) {
                    image = myDownIcon1;
                }
                if (mySpritesNum == 2) {
                    image = myDownIcon2;
                }
                break;
            case "LEFT":
                if (mySpritesNum == 1) {
                    image = myLeftIcon1;
                }
                if (mySpritesNum == 2) {
                    image = myLeftIcon2;
                }
                break;
            case "RIGHT":
                if (mySpritesNum == 1) {
                    image = myRightIcon1;
                }
                if (mySpritesNum == 2) {
                    image = myRightIcon2;
                }
                break;
        }
        theG.drawImage(image, myX, myY, ScreenSetting.TILE_SIZE, ScreenSetting.TILE_SIZE, null);
    }
    public void updateSpriteKeyPressed() {
        if (myKeyboardsHandler.isMyDownKeyPressed() || myKeyboardsHandler.isMyLeftKeyPressed()
                || myKeyboardsHandler.isMyRightKeyPressed() || myKeyboardsHandler.isMyUpKeyPressed()) {
            if (myKeyboardsHandler.isMyUpKeyPressed()) {
                myDirection = "UP";
                myY -= playerSpeed;
            } else if (myKeyboardsHandler.isMyDownKeyPressed()) {
                myDirection = "DOWN";
                myY += playerSpeed;
            } else if (myKeyboardsHandler.isMyLeftKeyPressed()) {
                myDirection = "LEFT";
                myX -= playerSpeed;
            } else if (myKeyboardsHandler.isMyRightKeyPressed()) {
                myDirection = "RIGHT";
                myX += playerSpeed;
            }
            mySpritesCounter++;
            if (mySpritesCounter > 12) {
                if (mySpritesNum == 1) {
                    mySpritesNum = 2;
                } else if (mySpritesNum == 2) {
                    mySpritesNum = 1;
                }
                mySpritesCounter = 0;
            }
        }
    }

    public int getX() {
        return myX;
    }
    public int getY() {
        return myY;
    }

    public void setMyXDefault(int theX) {
        this.myX = theX - WORLD_POSITION;
    }

    public void setMyYDefault(int theY) {
        this.myY = theY - WORLD_POSITION;
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
        return mySolidAreasDefaultX;
    }
    public int getMySolidAreasDefualtY() {
        return mySolidAreasDefaultY;
    }
    public Player getPlayer() {
        return myPlayer;
    }



}
