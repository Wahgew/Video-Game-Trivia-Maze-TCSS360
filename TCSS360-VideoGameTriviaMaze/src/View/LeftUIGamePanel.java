package View;

import Model.GameDataManager;
import Model.Player;

import javax.swing.*;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.InputStream;
/**
 * The LeftUIGamePanel class represents the left UI panel in the game interface.
 * It contains player health, score, movement buttons, and various control buttons.
 *
 * @author Peter W Madin, Ken Egawa, Sopheanith Ny
 * @version 6/7/2024
 */
public class LeftUIGamePanel extends JPanel implements PropertyChangeListener {
    /**
     * The player's health panel.
     */
    private PlayerHealth myPlayerHealth;

    /**
     * The manager for game data.
     */
    private GameDataManager myGameData;

    /**
     * The panel containing movement buttons.
     */
    private MovementButtonPanel myMovementButtonPanel;

    /**
     * The UI component for music control.
     */
    private MusicUI myMusicUI;

    /**
     * The button to save the game.
     */
    private JButton mySaveGameButton;

    /**
     * The button to switch to the welcome screen.
     */
    private JButton mySwitchToWelcomeScreenButton;

    /**
     * The button to exit the game.
     */
    private JButton myExitGameButton;

    /**
     * The button to control music.
     */
    private JButton myMusicButton;

    /**
     * The label displaying the player's score.
     */
    private JLabel myScore;

    /**
     * The custom font used for displaying text.
     */
    private Font pixelMplus;

    /**
     * The associated game panel.
     */
    private final GamePanel myGamePanel;

    /**
     * The PropertyChangeSupport object for handling property change events.
     */
    private final PropertyChangeSupport myPCS = new PropertyChangeSupport(this);

    /**
     * Constructs a new LeftUIGamePanel.
     * @param theGamePanel The associated game panel.
     */
    public LeftUIGamePanel(GamePanel theGamePanel) {
        myGameData = new GameDataManager();
        myGamePanel = theGamePanel;
        myPlayerHealth = new PlayerHealth(Player.getInstance());
        //mySoundManager = new SoundManager();
        loadCustomFont();
        myScore = new JLabel("Score: "+ Player.getInstance().getMyScore());
        myScore.setFont(pixelMplus);
        myScore.setForeground(Color.BLACK);

        JPanel leftPanel = new JPanel(new BorderLayout()) ;
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(Color.BLACK);
        leftPanel.setPreferredSize(new Dimension(300, 1200));

        JPanel topLeftPanel = new JPanel();
        topLeftPanel.add(HeartPanel(), BorderLayout.CENTER);
        //topLeftPanel.setBackground(Color.BLACK);
        topLeftPanel.setPreferredSize(new Dimension(300, 150));

        JPanel middleLeftPanel1 = new JPanel();
        middleLeftPanel1.setPreferredSize(new Dimension(300, 70));
        middleLeftPanel1.add(myScore, BorderLayout.CENTER);
        //middleLeftPanel1.setBackground(Color.gray);

        JPanel middleLeftPanel = new JPanel();
        myMovementButtonPanel = new MovementButtonPanel(myGamePanel);
        theGamePanel.setMyMovementButtonPanel(myMovementButtonPanel);
        middleLeftPanel.add(myMovementButtonPanel, BorderLayout.CENTER);
        //middleLeftPanel.setBackground(Color.gray);

        JPanel bottomLeftPanel = new JPanel();
        bottomLeftPanel.add(BottomButton());

        leftPanel.add(topLeftPanel,BorderLayout.NORTH);
        leftPanel.add(middleLeftPanel1);
        leftPanel.add(middleLeftPanel);
        leftPanel.add(bottomLeftPanel,BorderLayout.SOUTH);

        // Add left and center panels to the main panel
        add(leftPanel, BorderLayout.WEST);
        setBackground(Color.BLACK);
        Player.getInstance().getMyPCS().addPropertyChangeListener(this);
    }

    /**
     * Loads the custom font used in the UI.
     */
    private void loadCustomFont() {

        try {
            InputStream is = getClass().getResourceAsStream("/Resource/PixelMplus12-Bold.ttf");
            //InputStream is = getClass().getResourceAsStream("/Resource/PixelMplus12-Regular.ttf");
            if (is != null) {
                pixelMplus = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(40f);
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                ge.registerFont(pixelMplus);
            } else {
                System.out.println("Font file not found");
            }
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates the panel displaying the player's health.
     * @return The panel displaying the player's health.
     */
    private JPanel HeartPanel() {
        JPanel westPanel = new JPanel(new BorderLayout()) {
            @Override
            public Dimension getPreferredSize() {
                // Set the desired width and height for the westPanel
                return new Dimension(300, 135);
            }
        };
        JPanel playerHealthPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D)g;
                myPlayerHealth.playerHealthImage();
                myPlayerHealth.draw(g2);
                g2.dispose();
                repaint();
            }
        };
        //playerHealthPanel.setBackground(Color.white);
        playerHealthPanel.setPreferredSize(new Dimension(300, 135));

        westPanel.add(playerHealthPanel, BorderLayout.NORTH);
        return westPanel;
    }

    /**
     * Creates the panel containing control buttons at the bottom.
     * @return The panel containing control buttons.
     */
    private JPanel BottomButton(){
        ImageIcon saveGameIcon = resizeImage("/Resource/SaveGame.png", 170, 50);
        ImageIcon welcomeScreenIcon = resizeImage("/Resource/WelcomeScreen.png", 170, 50);
        ImageIcon exitIcon = resizeImage("/Resource/Exit.jpg", 170, 50);
        ImageIcon musicIcon = resizeImage("/Resource/Volume.png", 170, 50);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(300, 800));

        myMusicButton = new JButton(musicIcon);
        mySwitchToWelcomeScreenButton = new JButton(welcomeScreenIcon);
        mySaveGameButton = new JButton(saveGameIcon);
        myExitGameButton = new JButton(exitIcon);

        myMusicButton.setBorderPainted(false);
        myExitGameButton.setBorderPainted(false);
        mySaveGameButton.setBorderPainted(false);
        mySwitchToWelcomeScreenButton.setBorderPainted(false);

        myMusicButton.setContentAreaFilled(false);
        myExitGameButton.setContentAreaFilled(false);
        mySaveGameButton.setContentAreaFilled(false);
        mySwitchToWelcomeScreenButton.setContentAreaFilled(false);

        buttonPanel.add(mySaveGameButton);
        buttonPanel.add(myMusicButton);
        buttonPanel.add(mySwitchToWelcomeScreenButton);
        buttonPanel.add(myExitGameButton);


        addButtonListener();

        return buttonPanel;
    }

    /**
     * Adds action listeners to the control buttons.
     */
    private void addButtonListener() {
        mySwitchToWelcomeScreenButton.addActionListener(e -> {
            JPanel thisP = this;
            GameFrame frame = (GameFrame) SwingUtilities.getWindowAncestor(thisP);
            frame.switchToWelcomeScreen();
            myGamePanel.requestFocus();
        });
        myExitGameButton.addActionListener(e -> {
            final int jOption = JOptionPane.showConfirmDialog(myGamePanel,
                    "Are you sure you want to Exit?", "Exit",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (jOption == JOptionPane.YES_NO_OPTION) {
                System.exit(0);
            }
            myGamePanel.requestFocus();
        });
        mySaveGameButton.addActionListener(e -> {
            myGameData.saveGameData();
            myPCS.firePropertyChange("saveFileCreated", null, null);
            myGamePanel.requestFocus();
        });
        myMusicButton.addActionListener(e -> {
            //mySoundManager = new SoundManager();
            //myMusicUI = new MusicUI();
            //myMusicUI = new MusicUI(mySoundManager, true);
            myMusicUI = new MusicUI(SoundManager.getInstance());
            //MusicUI.showMusicUI(SoundManager.getInstance());
        });
    }

    /**
     * Resizes the given image icon to the specified dimensions.
     * @param thePath The path to the image file.
     * @param theWidth The width to resize the image to.
     * @param theHeight The height to resize the image to.
     * @return The resized image icon.
     */
    private ImageIcon resizeImage(String thePath, int theWidth, int theHeight) {
        ImageIcon icon = new ImageIcon(getClass().getResource(thePath));
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(theWidth, theHeight, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImg);
    }

    /**
     * Handles property change events.
     * @param theEvt The property change event.
     */
    @Override
    public void propertyChange(PropertyChangeEvent theEvt) {
        if (theEvt.getPropertyName().equals("score")) {
            updatePlayerScoreLabel();
        }

        if (theEvt.getPropertyName().equals("buttonEnable")) {
            boolean enableButtons = (boolean) theEvt.getNewValue();
            if (enableButtons) {
                enableButtons();
            }
        }
    }

    /**
     * Adds a property change listener to this panel.
     * @param theListener The property change listener to add.
     */
    public void addPropertyChangeListener(PropertyChangeListener theListener) {
        myPCS.addPropertyChangeListener(theListener);
    }

    /**
     * Removes a property change listener from this panel.
     * @param theistener The property change listener to remove.
     */
    public void removePropertyChangeListener(PropertyChangeListener theistener) {
        myPCS.removePropertyChangeListener(theistener);
    }

    /**
     * Retrieves the save game button.
     * @return The save game button.
     */
    public JButton getMySaveGameButton() {
        return mySaveGameButton;
    }
    /**
     * Retrieves the switch to welcome screen button.
     * @return The switch to welcome screen button.
     */
    public JButton getMySwitchToWelcomeScreenButton() {
        return mySwitchToWelcomeScreenButton;
    }
    /**
     * Retrieves the movement button panel.
     * @return The movement button panel.
     */
    public MovementButtonPanel getMyMovementButtonPanel() {
        return myMovementButtonPanel;
    }
    /**
     * Updates the player's score label.
     */
    private void updatePlayerScoreLabel() {
        myScore.setFont(pixelMplus);
        myScore.setText("Score " + Player.getInstance().getMyScore());
    }
    /**
     * Enables the control buttons.
     */
    private void enableButtons() {
        mySaveGameButton.setEnabled(true);
        mySwitchToWelcomeScreenButton.setEnabled(true);
    }
}
