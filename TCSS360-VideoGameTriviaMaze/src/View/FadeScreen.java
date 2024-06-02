package View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FadeScreen extends JPanel implements ActionListener {
    Timer myAlpha = new Timer(20, this);

    BufferedImage myBuffImage;
    float myAlphaValue = 1f;
    private GamePanel myGameP;

    public FadeScreen(GamePanel theGamePanel) {
        myGameP = theGamePanel;
        myGameP.setMyGameThread(null);
        loadImage();
        myAlpha.start();
    }

    public void loadImage() {
        myBuffImage = null;

        try {
            myBuffImage = ImageIO.read(new File("src/Resource/Images/Solid_black_bg.jpg"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void paint(Graphics theG) {
        super.paint(theG);
        Graphics2D g2d = (Graphics2D) theG;
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, myAlphaValue));

        g2d.drawImage(myBuffImage,0, 0, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        myAlphaValue -= 0.1f;

        if (myAlphaValue < 0) {
            myAlphaValue = 0;
            myAlpha.stop();
            myGameP.startGameThread();
        }

        repaint();
    }
}
