package View;

import Model.GameDataManager;
import Model.HighScore;
import Model.Player;

import javax.swing.*;
import java.awt.*;
/**
 * The GameFrame class represents the main frame for the Trivia Labyrinth Maze game.
 * It handles the GUI components, game panel switching, and menu actions.
 *
 * @author Peter Madin
 * @author Ken Egawa
 * @author Sopheanith Ny
 * @version 0.0.7 June 1, 2024
 */
public class GameFrame extends JFrame {
    /**
     * The title of the game.
     */
    private static final String GameTitle = "TRIVIA LABYRINTH MAZE ";

    /**
     * The border size for the game frame.
     */
    private static final int Border = 15;

    /**
     * The menu bar of the game frame.
     */
    private JMenuBar myMenuBar;

    /**
     * The menu item for saving the game.
     */
    private JMenuItem mySaveGame;

    /**
     * The menu item for displaying the welcome screen.
     */
    private JMenuItem myWelcomeButton;

    /**
     * The menu item for enabling cheats.
     */
    private JMenuItem myCheats;

    /**
     * The menu item for displaying information about the game.
     */
    private JMenuItem myAboutUs;

    /**
     * The menu item for exiting the game.
     */
    private JMenuItem myExitGame;

//    /**
//     * The menu item for providing hints in the game.
//     */
//    private JMenuItem myHintGame;

    /**
     * The menu item for displaying game instructions.
     */
    private JMenuItem myInstructionGame;

    /**
     * The button for resuming the game.
     */
    private JButton myResumeGameButton;

    /**
     * The menu item for resetting high scores.
     */
    private JMenuItem myResetHighScores;

    /**
     * The icon for the game logo.
     */
    private final ImageIcon logoIcon = new ImageIcon(getClass().getResource("/Resource/Logo1.png"));

    /**
     * The icon for the speed image.
     */
    private final ImageIcon speedIcon = new ImageIcon(getClass().getResource("/Resource/SPEED_CRYING.gif"));

    /**
     * The icon for the author image.
     */
    private final ImageIcon authorIcon = new ImageIcon(getClass().getResource("/Resource/Author_image.png"));

    /**
     * The game panel associated with this frame.
     */
    private GamePanel myGamePanel;

    /**
     * The main panel that components will be attached to.
     */
    private JPanel myMainPanel;

    /**
     * The maze layout panel associated with this frame.
     */
    private MazeLayoutPanel myMazeLayoutPanel;

    /**
     * The game data manager.
     */
    private GameDataManager myGameData;

    /**
     * The left UI game panel.
     */
    private LeftUIGamePanel myLeftUIGamePanel;

    /**
     * The high-score manager.
     */
    private HighScore myHighScore;

    /**
     * The welcome screen panel.
     */
    private WelcomeScreen myWelcomeScreen;

    /**
     * The sound manager for handling game sounds.
     */
    private SoundManager mySoundManager;

    /**
     * The music UI for controlling music playback.
     */
    private MusicUI myMusicUI;

    /**
     * Constructs a new GameFrame instance, initializing the frame properties and game components.
     */
    public GameFrame() {
        setUndecorated(true);
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setIconImage(logoIcon.getImage());
        myWelcomeScreen = new WelcomeScreen();
        setContentPane(myWelcomeScreen);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle(GameTitle);
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
        setResizable(false);
        myGameData = new GameDataManager();
        myHighScore = new HighScore();
        mySoundManager = SoundManager.getInstance();
        mySoundManager.playMusic(0, -20.0f);
    }

    /**
     * Returns the game panel associated with this frame.
     *
     * @return the GamePanel instance
     */
    public GamePanel getMyGamePanel() {
        return myGamePanel;
    }

    /**
     * Returns the menu item for saving the game.
     *
     * @return the JMenuItem for saving the game
     */
    public JMenuItem getMySaveGame() {
        return mySaveGame;
    }

    /**
     * Returns the menu item for enabling cheats.
     *
     * @return the JMenuItem for enabling cheats
     */
    public JMenuItem getMyCheats() {
        return myCheats;
    }

    /**
     * Returns the left UI game panel.
     *
     * @return the LeftUIGamePanel instance
     */
    public LeftUIGamePanel getMyLeftUIGamePanel() {
        return myLeftUIGamePanel;
    }

    /**
     * Switches to the specified game panel, updating the UI components.
     *
     * @param theGamePanel the GamePanel to switch to
     */
    public void switchToGamePanel(final GamePanel theGamePanel) {
        myGamePanel = theGamePanel;
        myMainPanel = new JPanel(new BorderLayout());
        myMainPanel.setBackground(Color.BLACK);
        resumeButton();
        menuBar();
        myWelcomeButton.setEnabled(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Add left UI game panel to the left side of the main panel
        myLeftUIGamePanel = new LeftUIGamePanel(theGamePanel);
        myMainPanel.add(myLeftUIGamePanel, BorderLayout.WEST);
        myLeftUIGamePanel.addPropertyChangeListener(myWelcomeScreen);

        myMainPanel.revalidate();
        myMainPanel.repaint();

        // Add theGamePanel to the center of the main panel
        myMainPanel.add(theGamePanel, BorderLayout.CENTER);
        // Set the content pane of the frame to the main panel
        setContentPane(myMainPanel);
        //setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Revalidate the frame to apply changes
        revalidate();

        // Request focus for theGamePanel and start the game thread
        theGamePanel.requestFocusInWindow();
        theGamePanel.startGameThread();
        MovementButtonPanel.loadIcons();
        myMusicUI = new MusicUI(mySoundManager);
        myMusicUI.showUI();
        mySoundManager.stop();
        mySoundManager.playMusic(2,-40);
    }

    /**
     * Switches to fade screen when loading into new room.
     */
    public void fadeScreen() {
        myMainPanel.add(new FadeScreen(myGamePanel));
    }


    /**
     * Switches to the maze layout panel, updating the content pane.
     */
    public void switchToMazeLayout() {
        // Create a new panel to hold the MazeLayoutPanel
        JPanel mazeLayoutPanel = new JPanel(new BorderLayout());
        myMazeLayoutPanel = new MazeLayoutPanel(this);

        // Add the MazeLayoutPanel to the center of the panel
        mazeLayoutPanel.add(myMazeLayoutPanel, BorderLayout.CENTER);

        // Set the content pane to the mazeLayoutPanel
        setContentPane(mazeLayoutPanel);
        revalidate();
        repaint();
    }

    /**
     * Switches to the welcome screen, updating the content pane and stopping the game thread.
     */
    public void switchToWelcomeScreen() {
        myGamePanel.setMyGameThread(null);
        setExtendedState(JFrame.NORMAL);
        setContentPane(myWelcomeScreen);
        mySaveGame.setEnabled(false);
        myWelcomeButton.setEnabled(false);
        mySoundManager.stop();
        mySoundManager.playMusic(0,-20f);
        myMusicUI.hideUI();
        //pack(); // Reset to preferred size
        setLocationRelativeTo(null);
        revalidate();
        repaint();
    }

    /**
     * Switches to the end game panel, updating the content pane.
     */
    public void switchToEndGamePanel() {
        setContentPane(new EndPanel(Player.getInstance(), myGamePanel));
        //myMenuBar.removeAll();
        //mySoundManager.stop();
        //mySoundManager.playMusic(1);
        revalidate();
    }

    /**
     * Initializes and sets the resume button with its action listener.
     */
    private void resumeButton() {
        myResumeGameButton = new JButton("RESUME GAME");
        myResumeGameButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        myResumeGameButton.addActionListener(e -> {
            Component component = (Component) e.getSource();
            Window window = SwingUtilities.getWindowAncestor(component);
            window.dispose();
        });
    }

    /**
     * Initializes the menu bar with its menu items and listeners.
     */
    private void menuBar() {
        myMenuBar = new JMenuBar();
        JMenu myGameMenu = new JMenu("Game Setting");
        JMenu myHelpMenu = new JMenu("Help");
        mySaveGame = new JMenuItem("Save Game");
        myWelcomeButton = new JMenuItem("Welcome Screen");
        myCheats = new JMenuItem("Cheats");
        myAboutUs = new JMenuItem("About Us");
        //myHintGame = new JMenuItem("Hint");
        myExitGame = new JMenuItem("Exit");
        myInstructionGame = new JMenuItem("Instruction");
        myResetHighScores = new JMenuItem("Reset High Scores");

        myMenuBar.add(myGameMenu);
        myMenuBar.add(myHelpMenu);

        myGameMenu.add(mySaveGame);
        myGameMenu.add(myWelcomeButton);
        myGameMenu.add(myResetHighScores);
        myGameMenu.add(myCheats);
        myGameMenu.add(myExitGame);
        //myHelpMenu.add(myHintGame);
        myHelpMenu.add(myInstructionGame);
        myHelpMenu.add(myAboutUs);
        setJMenuBar(myMenuBar);
        myWelcomeButton.setEnabled(false);
        menuBarListener();

    }

    /**
     * Adds action listeners to the menu items.
     */
    private void menuBarListener() {
        myExitGame.addActionListener(e -> {
            final int jOption = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to Exit?", "Exit",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, speedIcon);
            if (jOption == JOptionPane.YES_NO_OPTION) {
                showDialog(new exitPanel());
                System.exit(0);
            }
        });
        myAboutUs.addActionListener(e -> {
            JOptionPane.showConfirmDialog(null, """
                            Game: Trivia Labyrinth Maze.
                            Author: Peter W Madin, Ken Egawa and Sopheanith Ny.
                            Version: 1.0.0
                            JDK: Java 20.""", "About",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, authorIcon);
        });
        //myHintGame.addActionListener(e -> showDialog(new hintPanel()));
        myInstructionGame.addActionListener(e -> { // TODO: REWRITE THIS WITH UPDATED INSTRUCTIONS
            JOptionPane.showConfirmDialog(null, "<html><p align='justify'>Objective:<br>"
                            + "Navigate through the maze, answer the trivia questions as prompted when you reach a door to open the pathway, and reach the exit!<br><br>"
                            + "Controls:<br>"
                            + "Press 'New Game' to begin the game.<br>"
                            + "Press 'Load Game' or go back to Welcome Screen to start a new game<br><br>"
                            + "Gameplay:<br>"
                            + "When the player encounters a door, a video game based trivia question will be prompted on the screen.<br>"
                            + "Trivia question types include short answer, multiple choice, true/false, audio and image.<br>"
                            + "Answer the question correctly, and the player is free to continue on the maze,<br>"
                            + "otherwise if the player answers incorrectly, the door is now locked.<br>"
                            + "The player must find another route to exit the maze.<br>"
                            + "Reach the exit to win the game!<br>"
                            + "Good luck and have fun!</p></html>", "Instruction",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, speedIcon);
        });
        mySaveGame.addActionListener(e -> myGameData.saveGameData());
        myCheats.addActionListener(e -> {
            int jOption = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to turn on cheats, high scores will be turned off!", "Dev Cheats",
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (jOption == JOptionPane.YES_OPTION) {
                QuestionPanel.cheatToggle(true);
                Player.getInstance().setMyCheat(true);

                JOptionPane.showMessageDialog(null, "Cheats Enable, High score is disabled", "Dev Cheats",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        myWelcomeButton.addActionListener(e -> {
            switchToWelcomeScreen();
            myWelcomeButton.setEnabled(false);
            myGamePanel.requestFocus();
                });

        myResetHighScores.addActionListener(e -> {
            int jOption = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to reset high scores?", "Reset High Scores",
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (jOption == JOptionPane.YES_OPTION) {
                myHighScore.resetHighScore();
                JOptionPane.showMessageDialog(null, "High scores have been reset.", "Reset High Scores",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

    }

    /**
     * Displays a dialog with the specified panel.
     *
     * @param thePanel the panel to display in the dialog
     */
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

    /**
     * The exitPanel class represents the panel displayed when confirming exit.
     */
    class instructionPanel extends JPanel {
        /**
         * Constructs an exitPanel with labels and a button for confirming exit.
         */
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
}
