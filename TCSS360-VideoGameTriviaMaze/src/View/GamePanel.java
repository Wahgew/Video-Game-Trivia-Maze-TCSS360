package View;

import Model.Direction;
import Model.Player;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class GamePanel extends JPanel implements Runnable{
    private transient Thread myGameThread;
    private boolean myGameOver;

    private JButton myUpArrowButton;
    private JButton myDownArrowButton;
    private JButton myLeftArrowButton;
    private JButton myRightArrowButton;

    public Game myGame;
    private transient PlayerHealth myPlayerHealth;
    KeyboardsHandler keyboardsHandler = new KeyboardsHandler();


    public GamePanel() {
        setMyGame(new Game(this));
        myGameOver = false;

        this.setPreferredSize(new Dimension(ScreenSetting.Screen_Width, ScreenSetting.Screen_Height));
        this.setBackground(Color.white);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyboardsHandler);
        this.setFocusable(true);
        this.setLayout(new BorderLayout());


        //add(createLayeredPanel(), BorderLayout.WEST);
        createLayeredPanel();
        addButtonListener();
    }
    public void setMyGame(Game game) {
        //System.out.println("1"); // TODO: should this be printing 1 to console randomly?
        myGame = game;
        addKeyListener(myGame.getKeyHandler());
        myPlayerHealth = new PlayerHealth(myGame.getMyPlayer());
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
            System.out.println("Error occured while saving the game state: " + e.getMessage());
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
            System.out.println("Error occured while loading the game state: " + exception.getMessage());
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
    public void addButtonListener() {
        myUpArrowButton.addActionListener(e -> {
            Player.getInstance().movePlayer(Direction.NORTH);
        });
        myDownArrowButton.addActionListener(e -> {
            Player.getInstance().movePlayer(Direction.SOUTH);
        });
        myLeftArrowButton.addActionListener(e -> {
            Player.getInstance().movePlayer(Direction.WEST);
        });
        myRightArrowButton.addActionListener(e -> {
            Player.getInstance().movePlayer(Direction.EAST);
        });
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
    protected JPanel createLayeredPanel() {
        JPanel westPanel = new JPanel(new BorderLayout()) {
            @Override
            public Dimension getPreferredSize() {
                // Set the desired width and height for the westPanel
                return new Dimension(300, 300);
            }
        };
        JPanel topPanel = new JPanel();
        westPanel.add(topPanel,BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        //buttonPanel.setBackground(Color.BLACK);
        buttonPanel.setLayout(new GridLayout(3,3, 10, 10));

        //ImageIcon upArrowIcon = new ImageIcon(getClass().getResource("/Resource/upIcon.png"));
        //ImageIcon downArrowIcon = new ImageIcon(getClass().getResource("/Resource/downIcon.png"));
        //ImageIcon leftArrowIcon = new ImageIcon(getClass().getResource("/Resource/leftIcon.png"));
        //ImageIcon rightArrowIcon = new ImageIcon(getClass().getResource("/Resource/rightIcon.png"));
        ImageIcon upArrowIcon = resizeImage("/Resource/upIcon.png", 60, 60);
        ImageIcon downArrowIcon = resizeImage("/Resource/downIcon.png", 60, 60);
        ImageIcon rightArrowIcon = resizeImage("/Resource/rightIcon.png", 70, 50);
        ImageIcon leftArrowIcon = resizeImage("/Resource/leftIcon.png", 70, 50);


        myUpArrowButton = new JButton(upArrowIcon);
        myDownArrowButton = new JButton(downArrowIcon);
        myLeftArrowButton = new JButton(leftArrowIcon);
        myRightArrowButton = new JButton(rightArrowIcon);

        myUpArrowButton.setBorderPainted(false);
        myDownArrowButton.setBorderPainted(false);
        myLeftArrowButton.setBorderPainted(false);
        myRightArrowButton.setBorderPainted(false);

        myUpArrowButton.setContentAreaFilled(false);
        myDownArrowButton.setContentAreaFilled(false);
        myLeftArrowButton.setContentAreaFilled(false);
        myRightArrowButton.setContentAreaFilled(false);


        JButton invisButton1 = new JButton(); //invis buttons are to get desired spacing in the grid.
        invisButton1.setVisible(false);
        JButton invisButton2 = new JButton();
        invisButton2.setVisible(false);
        JButton invisButton3 = new JButton();
        invisButton3.setVisible(false);
        JButton invisButton4 = new JButton();
        invisButton4.setVisible(false);
        JButton invisButton5 = new JButton();
        invisButton5.setVisible(false);

        buttonPanel.add(invisButton1);
        buttonPanel.add(myUpArrowButton);
        buttonPanel.add(invisButton2);
        buttonPanel.add(myLeftArrowButton);
        buttonPanel.add(invisButton3);
        buttonPanel.add(myRightArrowButton);
        buttonPanel.add(invisButton4);
        buttonPanel.add(myDownArrowButton);
        buttonPanel.add(invisButton5);
        westPanel.add(buttonPanel, BorderLayout.SOUTH);


        //this.add(paintComponent(), BorderLayout.WEST);

        return westPanel;
    }
    private ImageIcon resizeImage(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(getClass().getResource(path));
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImg);
    }
    public void updateButtonStatus() {
        myUpArrowButton.setEnabled(Player.getInstance().validPlayerMove(Direction.NORTH));
        myDownArrowButton.setEnabled(Player.getInstance().validPlayerMove(Direction.SOUTH));
        myLeftArrowButton.setEnabled(Player.getInstance().validPlayerMove(Direction.WEST));
        myRightArrowButton.setEnabled(Player.getInstance().validPlayerMove(Direction.EAST));
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
