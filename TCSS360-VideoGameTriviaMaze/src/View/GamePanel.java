package View;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

    public GamePanel(){
        this.setPreferredSize(new Dimension(ScreenSetting.Screen_Width, ScreenSetting.Screen_Height));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
    }
    @Override
    public void run() {

    }
}
