package View;

import Controller.MazeController;
import Model.Direction;
import Model.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.jar.JarEntry;

public class GamePanel extends JPanel implements Runnable {
    private transient Thread myGameThread;
    private boolean myGameOver;

    private JButton myUpArrowButton;
    private JButton myDownArrowButton;
    private JButton myLeftArrowButton;
    private JButton myRightArrowButton;

    private JButton mytestButton;

    private JButton mySwitchToWelcomeScreenButton;
    private JButton mySaveGameButton;
    private JButton myExitGameButton;

    public Game myGame;
    public GameFrame myGameFrame;
    private transient PlayerHealth myPlayerHealth;
    KeyboardsHandler keyboardsHandler = new KeyboardsHandler();
    public MovementButtonPanel myMovementButtonPanel;


    public GamePanel() {
        setMyGame(new Game(this));
        myGameOver = false;
        this.setPreferredSize(new Dimension(ScreenSetting.Screen_Width, ScreenSetting.Screen_Height));
        this.setBackground(Color.white);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyboardsHandler);
        this.setFocusable(true);
        this.setLayout(new BorderLayout());
    }
    public void setMyGame(Game game) {
        myGame = game;
        addKeyListener(myGame.getKeyHandler());
        myPlayerHealth = new PlayerHealth(myGame.getMyPlayer());
        createLayeredPanel();
        this.setFocusable(true);
    }

    public void saveGame() {
        try {
            FileOutputStream fileOut = new FileOutputStream("game_state.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(myGame);
            fileOut.close();
            System.out.println("Game have been saved successfully.");
            showDialog(new SaveLoadGamePanel("Saved"));
        } catch (Exception e) {
            System.out.println("Error occurred while saving the game state: " + e.getMessage());
        }
    }
    public boolean loadGame() {
        try {
            FileInputStream fileIn = new FileInputStream("game_state.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Game loadGame = (Game) in.readObject();
            in.close();
            fileIn.close();
            setMyGame(loadGame);
            myGame.getMyPlayerManager().setPlayerImageIcon();
            myGame.setMyCollisionChecker(this);
            showDialog(new SaveLoadGamePanel("Game loaded"));
            repaint();
            System.out.println(myGame.getMyPlayer().getMyHealth());
            return true;
        } catch (Exception exception) {
            showDialog(new SaveLoadGamePanel("No Saved File."));
            System.out.println("Error occurred while loading the game state: " + exception.getMessage());
            return false;
        }
    }
    public void deleteSavedGames(){
        try {
            File saveFile = new File("game_state.ser");
            if (saveFile.exists()) {
                if (saveFile.delete()) {
                    System.out.println("Game has been deleted successfully.");
                } else {
                    System.out.println("Error occurred while deleting the game state.");
                }
            } else {
                System.out.println("Error occurred while deleting the game state.");
            }
        } catch (Exception exception) {
            System.out.println("Error occurred while deleting the game state: " + exception.getMessage());
        }
    }

    public void update(){
        myGame.getMyPlayerManager().updateSpriteKeyPressed();
    }
    public void paintComponent(Graphics theGraph){
        super.paintComponent(theGraph);
        Graphics2D g2 = (Graphics2D) theGraph;
        myGame.getMyPlayerManager().draw(g2);
        //myPlayerHealth.draw(g2);
        g2.dispose();
    }
    private void showDialog(final JPanel thePanel) {
        GameFrame frame = (GameFrame) SwingUtilities.windowForComponent(thePanel);
        JDialog dialog = new JDialog(frame);
        dialog.getContentPane().add(thePanel);
        dialog.setUndecorated(true);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    @Override
    public void run() {
        //Setting up game loop with better FPS
        double drawInterval = (double) 1000000000 / ScreenSetting.FPS; // 0.0166 seconds
        double nextDrawTime = System.nanoTime() + drawInterval;
        long lastUpdateTime = System.currentTimeMillis();

        while (myGameThread != null) {
            // Update information player movement postions
            update();
            createLayeredPanel();
            //addButtonListener();
            //Draw the screen with updated information
            repaint();
            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;

                if (remainingTime < 0) {
                    remainingTime = 0;
                }
                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public JPanel createLayeredPanel() {
        JPanel westPanel = new JPanel(new BorderLayout()) {
            @Override
            public Dimension getPreferredSize() {
                // Set the desired width and height for the westPanel
                return new Dimension(300, 300);
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
        playerHealthPanel.setBackground(Color.white);
        playerHealthPanel.setPreferredSize(new Dimension(300, 135));


        ImageIcon upArrowIcon = resizeImage("/Resource/upIcon.png", 60, 60);
        ImageIcon downArrowIcon = resizeImage("/Resource/downIcon.png", 60, 60);
        ImageIcon rightArrowIcon = resizeImage("/Resource/rightIcon.png", 70, 50);
        ImageIcon leftArrowIcon = resizeImage("/Resource/leftIcon.png", 70, 50);
        ImageIcon saveGameIcon = resizeImage("/Resource/SaveGame.png", 170, 50);
        ImageIcon welcomeScreenIcon = resizeImage("/Resource/WelcomeScreen.png", 170, 50);
        ImageIcon exitIcon = resizeImage("/Resource/Exit.jpg", 170, 50);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.RED);
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

        //mySwitchToWelcomeScreenButton.setBounds(120,50,170,50);
        //mySaveGameButton.setBounds(170,50);
        //myExitGameButton.setBounds(170,50);

        buttonPanel.add(mySaveGameButton);
        buttonPanel.add(mySwitchToWelcomeScreenButton);
        buttonPanel.add(myExitGameButton);

        //Movement button and panel.
        JPanel testbuttonPanel = new JPanel(null); // Use null layout
        testbuttonPanel.setBackground(Color.LIGHT_GRAY);
        testbuttonPanel.setPreferredSize(new Dimension(300, 100));

        mytestButton = new JButton("");
        myUpArrowButton = new JButton(upArrowIcon);
        myDownArrowButton = new JButton(downArrowIcon);
        myLeftArrowButton = new JButton(leftArrowIcon);
        myRightArrowButton = new JButton(rightArrowIcon);

        mytestButton.setBounds(120, 85, 40, 30); // Set the bounds of the button
        myUpArrowButton.setBounds(110, 25, 60, 60);
        myDownArrowButton.setBounds(110, 115, 60, 60);
        myLeftArrowButton.setBounds(50, 75, 70, 50);
        myRightArrowButton.setBounds(160, 76, 70, 50);;

        myUpArrowButton.setBorderPainted(false);
        myDownArrowButton.setBorderPainted(false);
        myLeftArrowButton.setBorderPainted(false);
        myRightArrowButton.setBorderPainted(false);

        myUpArrowButton.setContentAreaFilled(false);
        myDownArrowButton.setContentAreaFilled(false);
        myLeftArrowButton.setContentAreaFilled(false);
        myRightArrowButton.setContentAreaFilled(false);

        mytestButton.setVisible(false);

        testbuttonPanel.add(mytestButton);
        testbuttonPanel.add(myUpArrowButton);
        testbuttonPanel.add(myDownArrowButton);
        testbuttonPanel.add(myLeftArrowButton);
        testbuttonPanel.add(myRightArrowButton);

        westPanel.add(playerHealthPanel,BorderLayout.NORTH);
//        westPanel.add(testbuttonPanel);
//        westPanel.add(buttonPanel,BorderLayout.SOUTH);

        return westPanel;
    }

    public void updateButtonStatus() {
        myUpArrowButton.setEnabled(Player.getInstance().validPlayerMove(Direction.NORTH));
        myDownArrowButton.setEnabled(Player.getInstance().validPlayerMove(Direction.SOUTH));
        myLeftArrowButton.setEnabled(Player.getInstance().validPlayerMove(Direction.WEST));
        myRightArrowButton.setEnabled(Player.getInstance().validPlayerMove(Direction.EAST));
    }
    private ImageIcon resizeImage(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(getClass().getResource(path));
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImg);
    }

    public void startGameThread() {
        myGameThread = new Thread(this);
        myGameThread.start();
    }
    public PlayerCollision getCollision() {
        return myGame.getMyCollisionChecker();
    }
    public PlayerManager getMyPlayerManager() {
        return myGame.getMyPlayerManager();
    }
    public void setGameOver(boolean theGameOver){
        myGameOver = theGameOver;
    }
    public boolean isGameOver() {
        return myGameOver;
    }
    public Game getMyGame() {
        return myGame;
    }

    class SaveLoadGamePanel extends JPanel {
        private static final int Border = 15;
        private static final Color GRAY = new Color(188, 188, 188);
        private static final Color BLACK = new Color(9, 9, 9);
        private static final Color LIGHT_BLUE = new Color(173, 216, 230);

        public SaveLoadGamePanel(final String thePanel) {
            JButton continueButton = new JButton("Continue");
            JLabel resultLabel1 = new JLabel();
            setBackground(GRAY);

            continueButton.setForeground(BLACK);
            continueButton.setBackground(LIGHT_BLUE);
            continueButton.setBorder(BorderFactory.createLineBorder(BLACK, 1));

            if (thePanel.equalsIgnoreCase("Saved")) {
                resultLabel1 = new JLabel("Progress saved");
                resultLabel1.setForeground(BLACK);
            } else if (thePanel.equalsIgnoreCase("Loaded")) {
                resultLabel1 = new JLabel("Progress loaded");
                resultLabel1.setForeground(BLACK);
            } else if (thePanel.equalsIgnoreCase("No Saved File")) {
                resultLabel1 = new JLabel("No Saved File Found");
                resultLabel1.setForeground(BLACK);
            }

            JPanel resultPanel1 = new JPanel();
            resultPanel1.setOpaque(false);
            resultPanel1.add(resultLabel1);

            setBorder(BorderFactory.createEmptyBorder(Border, Border, Border, Border));
            setLayout(new GridLayout(2,1,10,10));
            add(resultPanel1);
            add(continueButton);

            continueButton.addActionListener(e -> {
               Component component = (Component) e.getSource();
               Window window = SwingUtilities.getWindowAncestor(component);
               window.dispose();
            });
        }
    }

}
