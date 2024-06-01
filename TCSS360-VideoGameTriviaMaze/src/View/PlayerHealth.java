package View;

import Model.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
/**
 * Represents the player's health in the game.
 *
 * @author Peter W Madin, Ken Egawa, Sopheanith Ny
 * @version 6/7/2024
 */
public class PlayerHealth {

    /**
     * The image representing the player's health.
     */

    private transient BufferedImage myImage;

    /**
     * The player associated with this health object.
     */
    private Player myPlayer;

    /**
     * The custom font for rendering text.
     */
    private Font pixelMplus;

    /**
     * Constructs a PlayerHealth object with the specified player.
     *
     * @param thePlayer the player object to associate with this health
     * @throws IllegalArgumentException if thePlayer is null
     */
    public PlayerHealth(final Player thePlayer) {
        if (thePlayer == null) {
            throw new IllegalArgumentException("Please enter non-null player object");
        }
        myPlayer = thePlayer;
        loadCustomFont();
    }

    /**
     * Loads the player health image.
     */
    public void playerHealthImage() {
        try {
            myImage = (ImageIO.read(getClass().getResourceAsStream("/Resource/Heart.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the custom font.
     */
    private void loadCustomFont() {

        try {
            InputStream is = getClass().getResourceAsStream("/Resource/PixelMplus12-Bold.ttf");
            //InputStream is = getClass().getResourceAsStream("/Resource/PixelMplus12-Regular.ttf");
            if (is != null) {
                pixelMplus = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(45f);
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                ge.registerFont(pixelMplus);
            } else {
                System.out.println("Font file not found");
            }
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }

    /**
     * Draws the player's health on the graphics context.
     *
     * @param g the graphics context on which to draw the health
     */
    public void draw(final Graphics g) {
        if (myPlayer.getMyHealth() > 0) {
            //g.setFont(ARIAL_30);
            g.setFont(pixelMplus);
            g.setColor(Color.BLACK);
            g.drawImage(myImage, ScreenSetting.TILE_SIZE, ScreenSetting.TILE_SIZE/2 ,
                    ScreenSetting.TILE_SIZE, ScreenSetting.TILE_SIZE, null);
            g.drawString("\nX" + myPlayer.getMyHealth(), 135, 72);

        }
    }
    /**
     This method customizes how objects are saved to a file.
     It writes the current state of the object to the specified output stream.
     If there's any problem with writing the object, it throws an IOException.
     @param out The output stream to write the object to.
     @throws IOException If an I/O error occurs while writing the object.
     */
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        if (myImage != null) {
            ImageIO.write(myImage, "png", out);
        }
    }
    /**
     This method customizes how objects are loaded from a file.
     It reads the saved state of the object from the given input stream.
     If there's any issue reading the object due to input/output problems, an IOException is thrown.
     If the class of the object being loaded cannot be found, a ClassNotFoundException is thrown.
     @param in The input stream to read the object from.
     @throws IOException If an I/O error occurs while reading the object.
     @throws ClassNotFoundException If the class of the object being deserialized cannot be found.
     */
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        try { // Deserialize the image data
            myImage = ImageIO.read(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
