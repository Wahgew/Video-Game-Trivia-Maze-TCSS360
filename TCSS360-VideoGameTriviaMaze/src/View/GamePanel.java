package View;

//import Controller.MazeController;
import Model.Direction;
import Model.Maze;
import Model.Player;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.*;

public class GamePanel extends JPanel implements Runnable{
    private JLayeredPane myLayeredPane;
    private transient Thread myGameThread;
    private boolean myGameOver;


    private JButton myUpArrowButton;
    private JButton myDownArrowButton;
    private JButton myLeftArrowButton;
    private JButton myRightArrowButton;

    private Game myGame;
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

        add(createLayeredPanel(), BorderLayout.WEST);
        addButtonListener();
    }
    public void setMyGame(Game game) {
        System.out.println("1");
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
            //showDialog(new SaveLoadPanel("Saved"));
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
            //myGame.setMyCollisionChecker(this);
            //showDialog
            repaint();
            System.out.println(myGame.getMyPlayer().getMyHealth());
            return true;
        } catch (Exception exception) {
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
                    System.out.println("Error occured while deleting the game state.");
                }
            } else {
                System.out.println("Error occured while deleting the game state.");
            }
        } catch (Exception exception) {
            System.out.println("Error occured while deleting the game state: " + exception.getMessage());
        }
    }


    public void update(){
        myGame.getMyPlayerManager().updateSpriteKeyPressed();
    }
    public void paintComponent(Graphics theGraph){
        super.paintComponent(theGraph);

        Graphics2D g2 = (Graphics2D) theGraph;
        myGame.getMyPlayerManager().draw(g2);
        myPlayerHealth.draw(g2);

        g2.dispose();
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
    private JPanel createLayeredPanel() {
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

        myUpArrowButton = new JButton("^");
        myDownArrowButton = new JButton("v");
        myLeftArrowButton = new JButton("<");
        myRightArrowButton = new JButton(">");

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

        return westPanel;
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
    public void setGameOver(boolean theGameOver){
        myGameOver = theGameOver;
    }
    public boolean isGameOver() {
        return myGameOver;
    }
    public JLayeredPane getMyLayeredPane(){
        return myLayeredPane;
    }
}
