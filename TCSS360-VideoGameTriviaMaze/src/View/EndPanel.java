package View;

import Model.GameDataManger;
import Model.HighScore;
import Model.Player;
import Model.Room;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;

/**
 * EndPanel class represents the end screen of the game, displaying player's statistics,
 * high scores, and end messages based on game outcomes.
 *
 * @author Peter W Madin, Ken Egawa, Sopheanith Ny
 * @version 6/7/2024
 */
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
    private HighScore myHighScore;
    private SoundManager mySoundManager;
    private Font pixelMplus;

    /**
     * Constructs the EndPanel with the given player and game panel.
     * Initializes UI components, calculates player statistics,
     * and prepares end messages.
     *
     * @param thePlayer the player instance containing player data
     * @param theGamePanel the game panel instance
     */
    public EndPanel(Player thePlayer, GamePanel theGamePanel) {
        myPlayer = thePlayer;
        myGamePanel = theGamePanel;
        myHighScore = new HighScore();
        mySoundManager = SoundManager.getInstance();
        myGamePanel.setMyGameThread(null);

        GameFrame frame = (GameFrame) SwingUtilities.getWindowAncestor(myGamePanel);
        frame.getMySaveGame().setEnabled(false);
        frame.getMyCheats().setEnabled(false);
        loadCustomFont();
        setPreferredSize(new Dimension(ScreenWidth, ScreenHeight));
        setBackground(Color.BLACK);
        setLayout(null);

        myText = new JTextArea();
        myText.setBounds(200, 200, ScreenWidth, ScreenHeight);
        //myText.setFont(new Font("Berlin Sans F8",Font.PLAIN,20));
        myText.setFont(pixelMplus);
        myText.setForeground(White);
        myText.setBackground(Color.BLACK);
        myText.setLineWrap(true);
        myText.setWrapStyleWord(true);
        myText.setEditable(false);
        myText.setFocusable(false);
        add(myText);

        int score = myPlayer.getMyScore();
        int correctTotal = myPlayer.getMyCorrectTotal();
        int incorrectTotal = myPlayer.getMyIncorrectTotal();
        int totalQuestions = correctTotal + incorrectTotal;
        double correctPercentage = totalQuestions > 0 ? (correctTotal * 100.0 / totalQuestions) : 0.0;

        String systemUserName = HighScore.getSystemUserName();
        String highScoreMessage = String.format("\n\nHigh Score: %d by %s on %s\n",
                myHighScore.getScore(),
                myHighScore.getPlayerName(),
                myHighScore.getDate());

        if (!Player.getInstance().getMyCheat()) {
            myHighScore.saveHighScore(score, systemUserName);
        } else {
            highScoreMessage += "\n\nCurrent high score is not saved due to cheats being enabled.\n";
        }


        String statistics = String.format("\n\nPlayer Statistics:\n\nScore: %d\nCorrect Answers: %d\nIncorrect Answers: %d\nOverall Percentage: %.2f%%\n\n",
                score, correctTotal, incorrectTotal, correctPercentage);

        String endMessage = "";
        if (myPlayer.getMyHealth() == 0) {
            mySoundManager.stop();
            mySoundManager.playMusic(3,-20f);
            endMessage = "Game Over!" + "\nYou've lost all your health points...";
        }
        else if (myPlayer.getMyHealth() > 0 && Room.getSoftLock()) { // && !myPlayer.getMyVictory()
            mySoundManager.stop();
            mySoundManager.playMusic(3, -20f);
            endMessage = "Game Over" + "\nAll doors to the exit has been locked...";
        } else if (myPlayer.getMyVictory()) {
            GameDataManger.checkAndHandleVictory();
            mySoundManager.stop();
            mySoundManager.playMusic(4,-20f);
            endMessage = "Let's go... You Made it!\n\n" +
                    "Congratulations on the crazy journey through TRIVIA LABYRINTH MAZE.\n" +
                    "You have made it through the end! YEEEEEE!\n";
        }

        String credits = "\n\n\n\n\n\n\nDeveloped by: Peter W Madin, Ken Egawa and Sopheanith Ny (Below Average 2.0)";

        myStringBuilder = new StringBuilder(endMessage + highScoreMessage + statistics + credits);
        myIndex = 0;
        startTimer();
    }

    /**
     * Loads a custom font from resources to be used in the end panel.
     */
    private void loadCustomFont() {
        try {
            InputStream is = getClass().getResourceAsStream("/Resource/PixelMplus12-Regular.ttf");
            if (is != null) {
                pixelMplus = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(22f);
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                ge.registerFont(pixelMplus);
            } else {
                System.err.println("Font file not found");
            }
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }

    /**
     * Starts a timer to gradually display the end message text character by character.
     */
    public void startTimer() {
        JPanel thisPanel = this;
        myTime = new Timer(40, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (myIndex < myStringBuilder.length()) {
                    myText.setText(myStringBuilder.substring(0, myIndex + 1));
                    myIndex++;
                } else {
                    int choice = JOptionPane.showConfirmDialog(null, "Congratulations! You have completed the game. Do you want to return to the main screen?", "Game Completed", JOptionPane.YES_NO_OPTION);
                    if (choice == JOptionPane.YES_OPTION) {
                        mySoundManager.stop();
                        GameFrame frame = (GameFrame) SwingUtilities.getWindowAncestor(thisPanel);
                        frame.switchToWelcomeScreen();
                    }
                    myTime.stop();
                }
            }
        });
        myTime.start();
    }

    /**
     * Custom painting for the end panel, setting the background color to black.
     *
     * @param theGraphics the Graphics object to protect
     */
    public void paintComponent(Graphics theGraphics) {
        super.paintComponent(theGraphics);
        theGraphics.setColor(Color.BLACK);
    }
}
