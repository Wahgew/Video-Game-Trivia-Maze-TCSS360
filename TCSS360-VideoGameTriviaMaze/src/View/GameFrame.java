package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GameFrame extends JFrame {
    private static final String GameTitle = "TRIVIA LABYRINTH MAZE ";
    private static final int Border = 15;
    private JMenuBar myMenuBar;
    private JMenuItem mySaveGame;
    private JMenuItem myLoadGame;
    private JMenuItem myAboutUs;
    private JMenuItem myExitGame;
    private JMenuItem myHintGame;
    private JMenuItem myInstructionGame;

    private JButton myResumeGameButton;

    ImageIcon logoIcon = new ImageIcon(getClass().getResource("/Resource/Logo1.png"));

    private GamePanel myGamePanel;
    private WelcomeScreen myWelcomeScreen;

    public GameFrame() {
        setIconImage(logoIcon.getImage());
        myWelcomeScreen = new WelcomeScreen();
        setContentPane(myWelcomeScreen);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle(GameTitle);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        myGamePanel = new GamePanel();

    }
    public GamePanel getMyGamePanel() {
        return myGamePanel;
    }
    public void switchToGamePanel(final GamePanel theGamePanel) {
        myGamePanel = theGamePanel;
        resumeButton();
        menuBar();
        setContentPane(theGamePanel);
        //setContentPane(theGamePanel.getMyLayeredPane());
        revalidate();
        theGamePanel.requestFocusInWindow();
        //theGamePanel.startGameThreat();
        showDialog(new instructionPanel());
    }
    public void switchToWelcomeScreen() {
        setContentPane(myWelcomeScreen);
        revalidate();
    }
    public void switchToEndGamePanel() {
        myMenuBar.removeAll();
        revalidate();
    }
    private void resumeButton() {
        myResumeGameButton = new JButton("RESUME GAME");
        //myResumeGameButton.setBackground(BEIGE);
        myResumeGameButton.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
        myResumeGameButton.addActionListener(e -> {
            Component component = (Component) e.getSource();
            Window window = SwingUtilities.getWindowAncestor(component);
            window.dispose();
        });
    }

    private void menuBar() {
        myMenuBar = new JMenuBar();
        JMenu myGameMenu = new JMenu("Game");
        JMenu myHelpMenu = new JMenu("Help");
        mySaveGame = new JMenuItem("Save Game");
        myLoadGame = new JMenuItem("Load Game");
        myAboutUs = new JMenuItem("About Us");
        myExitGame = new JMenuItem("Exit");
        myHintGame = new JMenuItem("Hint");
        myInstructionGame = new JMenuItem("Instruction");

        myMenuBar.add(myGameMenu);
        myMenuBar.add(myHelpMenu);

        myMenuBar.add(mySaveGame);
        myMenuBar.add(myLoadGame);
        myMenuBar.add(myAboutUs);
        myMenuBar.add(myExitGame);
        myMenuBar.add(myHintGame);
        myMenuBar.add(myInstructionGame);
        setJMenuBar(myMenuBar);
        menuBarListener();

    }
    private void menuBarListener() {
        myExitGame.addActionListener(e -> showDialog(new exitPanel()));
        myHintGame.addActionListener(e -> showDialog(new hintPanel()));
        myAboutUs.addActionListener(e -> showDialog(new aboutUsPanel()));
        myInstructionGame.addActionListener(e -> showDialog(new instructionPanel()));
        //mySaveGame.addActionListener(e -> myGamePanel.saveGame());
        //myLoadGame.addActionListener(e -> myGamePanel.loadGame());

    }
    public void showDialog(final JPanel thePanel) {
        JDialog dialog = new JDialog(this, "Dialog", true);
        dialog.getContentPane().add(thePanel);
        dialog.setUndecorated(true);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
    class exitPanel extends JPanel {}
    class hintPanel extends JPanel {}
    class instructionPanel extends JPanel {}
    class aboutUsPanel extends JPanel {}

}
