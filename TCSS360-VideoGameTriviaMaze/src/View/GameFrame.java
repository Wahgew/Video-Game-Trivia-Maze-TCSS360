package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
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

    private final ImageIcon logoIcon = new ImageIcon(getClass().getResource("/Resource/Logo1.png"));
    private final ImageIcon Speed_Icon = new ImageIcon(getClass().getResource("/Resource/SPEED_CRYING.gif"));

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
        setContentPane(theGamePanel.getMyLayeredPane());

        revalidate();
        theGamePanel.requestFocusInWindow();
        theGamePanel.startGameThread();
        //showDialog(new instructionPanel());
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
        JMenu myGameMenu = new JMenu("Game Setting");
        JMenu myHelpMenu = new JMenu("Help");
        mySaveGame = new JMenuItem("Save Game");
        myLoadGame = new JMenuItem("Load Game");
        myAboutUs = new JMenuItem("About Us");
        myHintGame = new JMenuItem("Hint");
        myExitGame = new JMenuItem("Exit");
        myInstructionGame = new JMenuItem("Instruction");

        myMenuBar.add(myGameMenu);
        myMenuBar.add(myHelpMenu);

        myGameMenu.add(mySaveGame);
        myGameMenu.add(myLoadGame);
        myGameMenu.add(myExitGame);
        myHelpMenu.add(myHintGame);
        myHelpMenu.add(myInstructionGame);
        myHelpMenu.add(myAboutUs);
        setJMenuBar(myMenuBar);
        menuBarListener();

    }
    private void menuBarListener() {
        myExitGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final int jOption = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to Exit?", "Exit",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,Speed_Icon);
                if (jOption == JOptionPane.YES_NO_OPTION) {
                    showDialog(new exitPanel());
                    System.exit(0);
                }
            }
        });
        myAboutUs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final int jOption = JOptionPane.showConfirmDialog(null, "Game: Trivia Labyrinth Maze.\n" +
                        "Author: Peter, Ken Sopheanith Ny.\nVersion: 1.0.\nJDK: Java 20.", "About",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.DEFAULT_OPTION, Speed_Icon);
            }
        });
        myHintGame.addActionListener(e -> showDialog(new hintPanel()));
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
    class exitPanel extends JPanel {
        public exitPanel() {
        setBackground(Color.WHITE);
            //Set up the label for the exit panel
            JLabel confirmExitLabel = new JLabel("CONFIRM EXIT?");
            confirmExitLabel.setForeground(Color.BLACK);

            JLabel confirmUnSavedExitLabel = new JLabel("UNSAVED PROGRESS WILL BE LOST!!");
            confirmUnSavedExitLabel.setForeground(Color.BLACK);

            //Set up the label for the exit panel
            JPanel confirmExitPanel = new JPanel();
            confirmExitPanel.setOpaque(false);
            confirmExitPanel.add(confirmExitLabel);

            JPanel confirmUnSavedExitPanel = new JPanel();
            confirmUnSavedExitPanel.setOpaque(false);
            confirmUnSavedExitPanel.add(confirmUnSavedExitLabel);

            //Setting up the border
            setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
            setLayout(new GridLayout(4,1,10,10));
            add(confirmExitPanel);
            add(confirmUnSavedExitPanel);

            //Setting up the Exit Button
            JButton exitButton = new JButton("EXIT");
            exitButton.setBackground(Color.DARK_GRAY);
            exitButton.setForeground(Color.BLACK);
            exitButton.addActionListener(e -> dispose());
            add(myResumeGameButton);
            add(exitButton);
        }
    }
    class hintPanel extends JPanel {}
    class instructionPanel extends JPanel {}
    class aboutUsPanel extends JPanel {
        public aboutUsPanel() {
            setBackground(Color.WHITE);
            JLabel aboutUsLabel = new JLabel("About US");

        }
    }

}
