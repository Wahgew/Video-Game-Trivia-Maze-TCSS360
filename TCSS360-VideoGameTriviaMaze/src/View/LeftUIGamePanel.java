package View;

import Model.GameDataManger;
import Model.Player;

import javax.swing.*;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.InputStream;

public class LeftUIGamePanel extends JPanel implements PropertyChangeListener {
    private PlayerHealth myPlayerHealth;
    private GameDataManger myGameData;
    private MovementButtonPanel myMovementButtonPanel;
    private MusicUI myMusicUI;
    private JButton mySaveGameButton;
    private JButton mySwitchToWelcomeScreenButton;
    private JButton myExitGameButton;
    private JButton myMusicButton;

    private JLabel myScore;
    private Font pixelMplus;
    private final GamePanel myGamePanel;
    private final PropertyChangeSupport myPCS = new PropertyChangeSupport(this);

    public LeftUIGamePanel(GamePanel theGamePanel) {
        myGameData = new GameDataManger();
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
    private ImageIcon resizeImage(String thePath, int theWidth, int theHeight) {
        ImageIcon icon = new ImageIcon(getClass().getResource(thePath));
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(theWidth, theHeight, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImg);
    }

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

    public void addPropertyChangeListener(PropertyChangeListener theListener) {
        myPCS.addPropertyChangeListener(theListener);
    }

    public void removePropertyChangeListener(PropertyChangeListener theistener) {
        myPCS.removePropertyChangeListener(theistener);
    }

    public JButton getMySaveGameButton() {
        return mySaveGameButton;
    }

    public JButton getMySwitchToWelcomeScreenButton() {
        return mySwitchToWelcomeScreenButton;
    }

    public MovementButtonPanel getMyMovementButtonPanel() {
        return myMovementButtonPanel;
    }

    private void updatePlayerScoreLabel() {
        myScore.setFont(pixelMplus);
        myScore.setText("Score " + Player.getInstance().getMyScore());
    }

    private void enableButtons() {
        mySaveGameButton.setEnabled(true);
        mySwitchToWelcomeScreenButton.setEnabled(true);
    }
}
