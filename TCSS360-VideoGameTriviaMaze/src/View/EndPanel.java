package View;

import Model.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EndPanel extends JPanel {
    private static final int ScreenWidth = ScreenSetting.Screen_Width;
    private static final int ScreenHeight = ScreenSetting.Screen_Height;
    private static final Color White = new Color(255, 255, 255);
    private JTextArea myText;
    private StringBuilder myStringBuilder;
    private int myIndex;
    private Timer myTime;
    private Player myPlayer;
    private GamePanel myGamePanel;

    public EndPanel(Player player, GamePanel gamePanel) {
        myPlayer = player;
        myGamePanel = gamePanel;
        setPreferredSize(new Dimension(ScreenWidth, ScreenHeight));
        setBackground(Color.BLACK);
        setLayout(null);

        myText = new JTextArea();
        myText.setBounds(200, 200, ScreenWidth, ScreenHeight);
        myText.setFont(new Font("Berlin Sans F8",Font.PLAIN,20));
        myText.setForeground(White);
        myText.setBackground(Color.BLACK);
        myText.setLineWrap(true);
        myText.setWrapStyleWord(true);
        myText.setEditable(false);
        add(myText);

        String text = "";
        if (myPlayer.getMyHealth() == 0) {
            text = "Game Over!" + "\nYou've lost all your health points...";
        }
        else if(myPlayer.getMyHealth() > 0 && myGamePanel.isGameOver()) {
            text = "Game Over" + "\nAll doors to the exit has been locked...";
        } else {
            text = "Let go... You Made it!\n\n" +
                    "Congratulation on the crazy journey through TRIVIA LABYRINTH MAZE.\n" +
                    "You have made it through the end! YEEEEEE!\n" +
                    "\n\n\n\n\n\n\n         Developed by: Peter W Madin, Ken Egawa and Sopheanith Ny (Below Average 2.0)";
        }
        myStringBuilder = new StringBuilder(text);
        myIndex = 0;
        startTimer();
    }
    public void startTimer() {
        JPanel thisPanel = this;
        myTime = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (myIndex < myStringBuilder.length()) {
                    myText.setText(myStringBuilder.substring(0, myIndex + 1));
                    myIndex++;
                } else {
                    int choice = JOptionPane.showConfirmDialog(null, "Congratulations! You have completed the game. Do you want to return to the main screen?", "Game Completed", JOptionPane.YES_NO_OPTION);
                    if (choice == JOptionPane.YES_OPTION) {
                        GameFrame frame = (GameFrame) SwingUtilities.getWindowAncestor(thisPanel);
                        frame.switchToWelcomeScreen();
                    }
                    myTime.stop();
                }
            }
        });
        myTime.start();
    }
    public void paintComponent(Graphics theGraphics) {
        super.paintComponent(theGraphics);
        theGraphics.setColor(Color.BLACK);
    }
}
