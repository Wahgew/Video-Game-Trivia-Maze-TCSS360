package View;

import Model.GameDataManger;
import Model.Player;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class LeftUIGamePanel extends JPanel implements PropertyChangeListener {
    private PlayerHealth myPlayerHealth;
    private GameDataManger myGameData;
    private JButton mySaveGameButton;
    private JButton mySwitchToWelcomeScreenButton;
    private JButton myExitGameButton;
    private JLabel myScore;

    private final GamePanel myGamePanel;
    private Game myGame;
    public LeftUIGamePanel(GamePanel theGamePanel) {
        myGameData = new GameDataManger();
        myGamePanel = theGamePanel;
        myScore = new JLabel("Score: "+ Player.getInstance().getMyScore());
        myPlayerHealth = new PlayerHealth(Player.getInstance());

        //setLayout(new BorderLayout());
        //JPanel leftPanel = new JPanel();

        JPanel leftPanel = new JPanel(new BorderLayout()) ;
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(Color.BLACK);

        JPanel topLeftPanel = new JPanel();
        topLeftPanel.add(HeartPanel(), BorderLayout.CENTER);
        //topLeftPanel.setBackground(Color.BLACK);
        topLeftPanel.setPreferredSize(new Dimension(300, 135));

        JPanel middleLeftPanel1 = new JPanel();
        middleLeftPanel1.setPreferredSize(new Dimension(300, 70));
        middleLeftPanel1.add(myScore, BorderLayout.CENTER);
        middleLeftPanel1.setBackground(Color.gray);

        JPanel middleLeftPanel = new JPanel();
        middleLeftPanel.add(new MovementButtonPanel(theGamePanel), BorderLayout.CENTER);
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

        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(300, 500));

        mySwitchToWelcomeScreenButton = new JButton(welcomeScreenIcon);
        mySaveGameButton = new JButton(saveGameIcon);
        myExitGameButton = new JButton(exitIcon);

        myExitGameButton.setBorderPainted(false);
        mySaveGameButton.setBorderPainted(false);
        mySwitchToWelcomeScreenButton.setBorderPainted(false);

        myExitGameButton.setContentAreaFilled(false);
        mySaveGameButton.setContentAreaFilled(false);
        mySwitchToWelcomeScreenButton.setContentAreaFilled(false);

        buttonPanel.add(mySaveGameButton);
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
        });
        myExitGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final int jOption = JOptionPane.showConfirmDialog(myGamePanel,
                        "Are you sure you want to Exit?", "Exit",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (jOption == JOptionPane.YES_NO_OPTION) {
                    //showDialog(new GameFrame.exitPanel());
                    System.exit(0);
                }
            }
        });
        //mySaveGameButton.addActionListener(e ->myGamePanel.saveGame());
        mySaveGameButton.addActionListener(e -> myGameData.saveGameData());
    }
    private ImageIcon resizeImage(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(getClass().getResource(path));
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImg);
    }

    @Override
    public void propertyChange(PropertyChangeEvent theEvt) {
        if (theEvt.getPropertyName().equals("score")) {
            updatePlayerScoreLabel();
        }
    }

    private void updatePlayerScoreLabel() {
        myScore.setText("Score " + Player.getInstance().getMyScore());
    }
}
