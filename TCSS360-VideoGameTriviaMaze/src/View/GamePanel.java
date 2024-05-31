package View;

import Model.Maze;
import Model.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GamePanel extends JPanel implements Runnable {
    private transient Thread myGameThread;
    private boolean myGameOver;
    public Game myGame;
    private transient PlayerHealth myPlayerHealth;
    private MovementButtonPanel myMovementButtonPanel;
    KeyboardsHandler keyboardsHandler = new KeyboardsHandler();
    private JLabel myRoomImage;
    private transient SoundManager mySoundManager;
    public GamePanel() {
        myGameOver = false;
        myRoomImage = new JLabel();
        mySoundManager = SoundManager.getInstance();
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(ScreenSetting.Screen_Width, ScreenSetting.Screen_Height));
        setSize(new Dimension(ScreenSetting.Screen_Width, ScreenSetting.Screen_Height));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        addKeyListener(keyboardsHandler);
        setFocusable(true);
        setMyGame(new Game(this));
        updateRoomImage();
        revalidate();
        repaint();

    }
    public void setMyGame(Game game) {
        myGame = game;
        addKeyListener(myGame.getKeyHandler());
        myPlayerHealth = new PlayerHealth(myGame.getMyPlayer());
        this.setFocusable(true);
    }

    public void update(){
        myGame.getMyPlayerManager().updateSpriteKeyPressed();
        myGameOver = Player.getInstance().getMyVictory();
        if (!myGameOver) {
            myGameOver = Maze.getInstance().getMyRoom(Player.getInstance().getMyLocationRow(),
                    Player.getInstance().getMyLocationCol()).softLockCheck();
        }
    }

    public void updateRoomImage() {
        BufferedImage mapImage = null;
        try {
            mapImage = ImageIO.read(new File(Maze.getInstance().getMyRoom(Player.getInstance().getMyLocationRow(), Player.getInstance().getMyLocationCol()).getRoomFileName()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (mapImage != null) {
            ImageIcon mapImageIcon = new ImageIcon(mapImage);
            myRoomImage.setIcon(mapImageIcon);
            myRoomImage.setHorizontalAlignment(JLabel.CENTER);
            myRoomImage.setVerticalAlignment(JLabel.CENTER);
            add(myRoomImage, BorderLayout.CENTER);

//            JPanel t = new JPanel();
//            t.setBackground(Color.YELLOW); //TODO: Call transition fade here.
//            add(t);

            revalidate();
            repaint();
        }
    }

    @Override
    public void paintComponent(Graphics theGraph) {
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        int centerX = panelWidth / 2;
        int centerY = panelHeight / 2;

        super.paintComponent(theGraph);
        Graphics2D g2 = (Graphics2D) theGraph;

        // Paint the rooms
        myRoomImage.setHorizontalAlignment(SwingConstants.CENTER);
        myRoomImage.setVerticalAlignment(SwingConstants.CENTER);
        myRoomImage.setBounds(0, 0, getWidth(), getHeight());
        myRoomImage.paint(g2);
        myGame.getMyPlayerManager().draw(g2, centerX, centerY);

        g2.dispose();
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
    public void setMyMovementButtonPanel(MovementButtonPanel theMovementButtonPanel) {
        myMovementButtonPanel = theMovementButtonPanel;
    }
    public MovementButtonPanel getMyMovementButtonPanel() {
        return myMovementButtonPanel;
    }
}