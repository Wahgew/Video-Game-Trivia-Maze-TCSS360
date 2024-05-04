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
    private String myDirection;

    private Rectangle mySolidAreas;
    private int mySolidAreasDefaultX;
    private int mySolidAreasDefaultY;

    private int mySpritesCounter;
    private int mySpritesNum;

    //int X = 100;
    //int Y = 100;
    int playerSpeed = 4;

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
        mySolidAreas = new Rectangle(6,14,44,44);
        mySolidAreasDefaultX = mySolidAreas.x;
        mySolidAreasDefaultY = mySolidAreas.y;
        myX = myPlayer.getMyLocationCol(); //
        myY = myPlayer.getMyLocationRow(); //
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
    public void draw(Graphics2D g) {

        //g.setColor(Color.black);
        //g.fillRect(X,Y,ScreenSetting.TILE_SIZE, ScreenSetting.TILE_SIZE);

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
        g.drawImage(image, myX, myY, ScreenSetting.TILE_SIZE, ScreenSetting.TILE_SIZE, null);
    }
    public void updateSpriteKeyPressed() {
//        if (myKeyboardsHandler.isMyDownKeyPressed() || myKeyboardsHandler.isMyLeftKeyPressed()
//                || !myKeyboardsHandler.isMyRightKeyPressed() || myKeyboardsHandler.isMyUpKeyPressed()) {
//            if (myKeyboardsHandler.isMyUpKeyPressed()) {
//                myDirection = "UP";
//            }
//            else if (myKeyboardsHandler.isMyDownKeyPressed()) {
//                myDirection = "DOWN";
//            }
//            else if (myKeyboardsHandler.isMyLeftKeyPressed()) {
//                myDirection = "LEFT";
//            }
//            else if (myKeyboardsHandler.isMyRightKeyPressed()) {
//                myDirection = "RIGHT";
//            }
//        }
        if (myKeyboardsHandler.isMyUpKeyPressed() == true) {
            myDirection = "UP";
            myY -= playerSpeed;
        }
        else if (myKeyboardsHandler.isMyDownKeyPressed() == true) {
            myDirection = "DOWN";
            myY += playerSpeed;
        }
        else if (myKeyboardsHandler.isMyLeftKeyPressed() == true) {
            myDirection = "LEFT";
            myX -= playerSpeed;
        }
        else if (myKeyboardsHandler.isMyRightKeyPressed() == true) {
            myDirection = "RIGHT";
            myX += playerSpeed;
        }
    }

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
        return mySolidAreasDefaultX;
    }
    public int getMySolidAreasDefualtY() {
        return mySolidAreasDefaultY;
    }
    public Player getPlayer() {
        return myPlayer;
    }



}
