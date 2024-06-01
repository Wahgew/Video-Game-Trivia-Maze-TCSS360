package View;

import Model.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;

/**
 * Manages the player's movement and appearance.
 *
 * @author Peter W Madin, Ken Egawa, Sopheanith Ny
 * @version 6/7/2024
 */
public class PlayerManager implements Serializable {

    /**
     * The image representing the player facing upwards, facing upwards (alternate)
     * facing downwards, facing downwards (alternate), facing rightwards, facing rightwards
     * (alternate), facing leftwards, facing leftwards (alternate).
     */
    private transient BufferedImage myUpIcon1, myUpIcon2, myDownIcon1, myDownIcon2,
            myRightIcon1, myRightIcon2, myLeftIcon1, myLeftIcon2;

    /**
     * The keyboard handler for player input.
     */
    private KeyboardsHandler myKeyboardsHandler;

    /**
     * The game panel containing the player.
     */
    private GamePanel myGamePanel;

    /**
     * The player object associated with this manager.
     */
    private Player myPlayer;

    /**
     * The x-coordinate of the player.
     */
    private int myX;

    /**
     * The y-coordinate of the player.
     */
    private int myY;

    /**
     * The world position, which is half the size of a tile.
     */
    private static final int WORLD_POSITION = (ScreenSetting.TILE_SIZE / 2);

    /**
     * The direction the player is facing.
     */
    private String myDirection;

    /**
     * The solid areas around the player.
     */
    private Rectangle mySolidAreas;

    /**
     * The default x-coordinate for solid areas.
     */
    private int mySolidAreasDefaultX;

    /**
     * The default y-coordinate for solid areas.
     */
    private int mySolidAreasDefaultY;

    /**
     * The counter for sprite animation.
     */
    private int mySpritesCounter;

    /**
     * The number of sprites for animation.
     */
    private int mySpritesNum;

    /**
     * The speed of the player.
     */
    private int playerSpeed = 3;

    /**
     * Constructs a PlayerManager with the specified parameters.
     *
     * @param theKeyboardsHandler the keyboard handler for player input
     * @param theGamePanel the game panel containing the player
     * @param thePlayer the player object associated with this manager
     * @throws IllegalArgumentException if any parameter is null
     */
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

    /**
     * Sets the default values for the player.
     */
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

    /**
     * Loads the player image icons.
     */
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

    /**
     * Draws the player sprite on the graphics context.
     *
     * @param theG the graphics context
     * @param centerX the center x-coordinate
     * @param centerY the center y-coordinate
     */
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

    /**
     * Updates the sprite when a key is pressed.
     */
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

    /**
     * Gets the current x-coordinate of the player.
     *
     * @return The current x-coordinate of the player.
     */
    public int getX() {
        return myX;
    }


    /**
     * Gets the current y-coordinate of the player.
     *
     * @return The current y-coordinate of the player.
     */
    public int getY() {
        return myY;
    }

    /**
     * Sets the default x-coordinate of the player based on the given value.
     *
     * @param theX The default x-coordinate value.
     */
    public void setMyXDefault(int theX) {
        this.myX = theX - WORLD_POSITION;
    }

    /**
     * Sets the default y-coordinate of the player based on the given value.
     *
     * @param theY The default y-coordinate value.
     */
    public void setMyYDefault(int theY) {
        this.myY = theY - WORLD_POSITION;
    }

    /**
     * Gets the current direction in which the player is facing.
     *
     * @return The direction the player is facing.
     */
    public String getDirection() {
        return myDirection;
    }

    /**
     * Gets the solid areas occupied by the player as a rectangle.
     *
     * @return The rectangle representing the solid areas occupied by the player.
     */
    public Rectangle getMySolidAreas() {
        return mySolidAreas;
    }

    /**
     * Sets the default x-coordinate of the solid areas based on the given value.
     *
     * @param theAreaX The default x-coordinate value of the solid areas.
     */
    public void setMySolidAreasDefaultX(final int theAreaX) {
        mySolidAreas.x = theAreaX;
    }

    /**
     * Sets the default y-coordinate of the solid areas based on the given value.
     *
     * @param theAreaY The default y-coordinate value of the solid areas.
     */
    public void setMySolidAreasDefaultY(final int theAreaY) {
        mySolidAreas.y = theAreaY;
    }

    /**
     * Gets the default x-coordinate of the solid areas.
     *
     * @return The default x-coordinate of the solid areas.
     */
    public int getMySolidAreasDefaultX() {
        return mySolidAreasDefaultX;
    }

    /**
     * Gets the default y-coordinate of the solid areas.
     *
     * @return The default y-coordinate of the solid areas.
     */
    public int getMySolidAreasDefaultY() {
        return mySolidAreasDefaultY;
    }

    /**
     * Gets the player object.
     *
     * @return The player object.
     */
    public Player getPlayer() {
        return myPlayer;
    }
}
