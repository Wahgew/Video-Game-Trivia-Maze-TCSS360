package View;

import javax.swing.*;

public class GameFrame extends JFrame {
    private static final String GameTitle = "Trivia Labyrinth Maze";
    private static final int Border = 15;
    private JMenuBar myMenuBar;
    private JMenuItem mySaveGame;
    private JMenuItem myResetGame;
    private JMenuItem myExitGame;
    private JMenuItem myHintGame;
    private JMenuItem myInstructionGame;
    private JButton myResumeGame;
    //private GamePanel GamePanel;
    private WelcomeScreen myWelcomeScreen;

    public GameFrame() {
        //setIcon();
        myWelcomeScreen = new WelcomeScreen();
        setContentPane(myWelcomeScreen);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle(GameTitle);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

    }

}
