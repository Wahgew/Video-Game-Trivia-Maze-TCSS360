package View;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
    private JLayeredPane myLayeredPane;
    private transient Thread myGameThread;
    private boolean myGameOver;

    public GamePanel(){
        myGameOver = false;

        this.setPreferredSize(new Dimension(ScreenSetting.Screen_Width, ScreenSetting.Screen_Height));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setFocusable(true);

        myLayeredPane = new JLayeredPane();
        myLayeredPane.setLayout(new BorderLayout());
        myLayeredPane.add(this);
    }
    public void startGameThread() {
        myGameThread = new Thread(this);
        myGameThread.start();
    }
    public void setGameOver(boolean theGameOver){
        myGameOver = theGameOver;
    }
    public boolean isGameOver() {
        return myGameOver;
    }
    public JLayeredPane getMyLayeredPane(){
        return myLayeredPane;
    }
    @Override
    public void run() {

    }
}
