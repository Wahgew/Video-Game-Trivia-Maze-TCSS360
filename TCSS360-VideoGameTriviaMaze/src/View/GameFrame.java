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

    private JButton myUpArrowButton;
    private JButton myDownArrowButton;
    private JButton myLeftArrowButton;
    private JButton myRightArrowButton;

    private final ImageIcon logoIcon = new ImageIcon(getClass().getResource("/Resource/Logo1.png"));
    private final ImageIcon Speed_Icon = new ImageIcon(getClass().getResource("/Resource/SPEED_CRYING.gif"));

    private GamePanel myGamePanel;
    private WelcomeScreen myWelcomeScreen;

    private boolean myGamePanelFocus;

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
        myGamePanelFocus = false; // TEMPORARY WORKAROUND FOR MazeController TODO: REPLACE THIS LATER
    }

    public GamePanel getMyGamePanel() {
        return myGamePanel;
    }
    public void switchToGamePanel(final GamePanel theGamePanel) {
        myGamePanel = theGamePanel;
        resumeButton();
        menuBar();
        //setContentPane(theGamePanel.getMyLayeredPane());
        setContentPane(theGamePanel);
        //setExtendedState(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        //setUndecorated(true);
        revalidate();
        theGamePanel.requestFocusInWindow();
        theGamePanel.startGameThread();
        //showDialog(new instructionPanel());
        myGamePanelFocus = true; // TEMPORARY WORKAROUND FOR MazeController TODO: REPLACE THIS LATER
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
        myResumeGameButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
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
                        "Author: Peter W Madin, Ken Egawa and Sopheanith Ny.\nVersion: 1.0.\nJDK: Java 21.", "About",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.DEFAULT_OPTION, Speed_Icon);
            }
        });
        myHintGame.addActionListener(e -> showDialog(new hintPanel()));
        myInstructionGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final int jOption = JOptionPane.showConfirmDialog(null, "<html><p align='justify'>Objective:<br>"
                                + "Navigate through the maze, answer the trivia questions as prompted when you reach a door to open the pathway, and reach the exit!<br><br>"
                                + "Controls:<br>"
                                + "Use arrow key buttons or keyboard arrows to navigate through the maze.<br>"
                                + "Load the game at the 'File' tab to enable the start button to begin game play.<br>"
                                + "Press 'Start' to begin the game.<br>"
                                + "Press 'Play Again' or 'Reset' to restart the game.<br><br>"
                                + "Gameplay:<br>"
                                + "Move the player using arrow key buttons or keyboard arrows.<br>"
                                + "When the player encounters a door, a Marvel trivia question will be prompted on the screen.<br>"
                                + "Trivia questions will be short answer, multiple choice, and true/false.<br>"
                                + "Answer the question correctly, and the door color will change to white meaning the player is free to continue on the maze,<br>"
                                + "or else the door will turn grey if the player answers incorrectly and the door is now locked.<br>"
                                + "The player must find another route to exit the maze.<br>"
                                + "Reach the exit to win the game!<br>"
                                + "Otherwise, if there are no other possible pathways out of the maze, the player loses!<br>"
                                + "Good luck and have fun!</p></html>", "Instruction",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.DEFAULT_OPTION, Speed_Icon);
            }
        });
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
            exitButton.setBackground(Color.GRAY);
            exitButton.setForeground(Color.WHITE);
            exitButton.addActionListener(e -> dispose());
            add(myResumeGameButton);
            add(exitButton);
        }
    }
    class hintPanel extends JPanel {}
    class instructionPanel extends JPanel {
        public instructionPanel() {
            setBackground(Color.GRAY);
            JLabel instructionLabel1 = new JLabel("Instruction");
            instructionLabel1.setForeground(Color.BLACK);
            JLabel instructionLabel2 = new JLabel
                    ("<html><p align='justify'>Objective:<br>"
                            + "Navigate through the maze, answer the trivia questions as prompted when you reach a door to open the pathway, and reach the exit!<br><br>"
                            + "Controls:<br>"
                            + "Use arrow key buttons or keyboard arrows to navigate through the maze.<br>"
                            + "Load the game at the 'File' tab to enable the start button to begin game play.<br>"
                            + "Press 'Start' to begin the game.<br>"
                            + "Press 'Play Again' or 'Reset' to restart the game.<br><br>"
                            + "Gameplay:<br>"
                            + "Move the player using arrow key buttons or keyboard arrows.<br>"
                            + "When the player encounters a door, a Marvel trivia question will be prompted on the screen.<br>"
                            + "Trivia questions will be short answer, multiple choice, and true/false.<br>"
                            + "Answer the question correctly, and the door color will change to white meaning the player is free to continue on the maze,<br>"
                            + "or else the door will turn grey if the player answers incorrectly and the door is now locked.<br>"
                            + "The player must find another route to exit the maze.<br>"
                            + "Reach the exit to win the game!<br>"
                            + "Otherwise, if there are no other possible pathways out of the maze, the player loses!<br>"
                            + "Good luck and have fun!</p></html>");
            instructionLabel2.setForeground(Color.BLACK);

            JPanel instructionPanel1 = new JPanel();
            instructionPanel1.setOpaque(false);
            instructionPanel1.add(instructionLabel1);

            JPanel instructionPanel2 = new JPanel();
            instructionPanel2.setOpaque(false);
            instructionPanel2.add(instructionLabel2);

            setLayout(new BorderLayout());
            setBorder(BorderFactory.createEmptyBorder(Border,Border,Border,Border));
            add(instructionPanel1);
            add(instructionPanel2);
            add(myResumeGameButton, BorderLayout.SOUTH);


        }
    }
    public boolean getGamePanelFocus() {
        return myGamePanelFocus;
    }
}
