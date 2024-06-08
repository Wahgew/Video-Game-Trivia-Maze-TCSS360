package View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * FadeScreen is a JPanel that handles a fade-in effect by gradually increasing
 * the alpha value of a black background image.
 * It implements ActionListener to handle the fade effect timer.
 *
 *
 * @author Peter Madin
 * @author Ken Egawa
 * @author Sopheanith Ny
 * @version 0.0.4 May 30, 2024
 */
public class FadeScreen extends JPanel implements ActionListener {

    /**
     * Timer to control the fade-in effect.
     */
    Timer myAlpha = new Timer(20, this);


    /**
     * BufferedImage to hold the background image.
     */
    BufferedImage myBuffImage;

    /**
     * Alpha value for the fade-in effect.
     */
    float myAlphaValue = 0f;

    /**
     * Reference to the GamePanel to control the game state.
     */
    private GamePanel myGameP;

    /**
     * Constructor for FadeScreen.
     *
     * @param theGamePanel the GamePanel instance to control the game state
     */
    public FadeScreen(GamePanel theGamePanel) {
        myGameP = theGamePanel;
        myGameP.setMyGameThread(null);
        loadImage();
        myAlpha.start();
    }

    /**
     * Loads the background image for the fade-in effect.
     */
    public void loadImage() {
        myBuffImage = null;

        try {
            InputStream inputStream = FadeScreen.class.getResourceAsStream("/Resource/Images/Solid_black_bg.jpg");
            if (inputStream != null) {
                myBuffImage = ImageIO.read(inputStream);
            } else {
                System.err.println("Resource file not found: /Resource/Images/Solid_black_bg.jpg");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Paints the panel with the background image and applies the alpha composite
     * for the fade-in effect.
     *
     * @param theG the Graphics object to protect
     */
    public void paint(Graphics theG) {
        super.paint(theG);
        Graphics2D g2d = (Graphics2D) theG;
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, myAlphaValue));

        g2d.drawImage(myBuffImage,0, 0, null);
    }

    /**
     * Handles the timer action events to update the alpha value and repaint the panel.
     *
     * @param e the ActionEvent object
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        myAlphaValue += 0.1f;

        if (myAlphaValue > 1) {
            myAlphaValue = 0;
            myAlpha.stop();
            myGameP.startGameThread();
        }

        repaint();
    }
}
