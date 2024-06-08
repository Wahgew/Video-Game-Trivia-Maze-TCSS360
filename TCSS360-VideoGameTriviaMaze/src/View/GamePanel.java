package View;

import Model.Maze;
import Model.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * The GamePanel class represents the panel where the game is displayed.
 *
 * @author Peter W Madin, Ken Egawa, Sopheanith Ny
 * @version 6/7/2024
 */
public class GamePanel extends JPanel implements Runnable {

    /**
     * The thread responsible for running the game loop.
     */
    private transient Thread myGameThread;

    /**
     * A boolean indicating whether the game is over.
     */
    private boolean myGameOver;

    /**
     * The instance of the Game class associated with this panel.
     */
    public Game myGame;

    /**
     * The health manager for the player.
     */
    private transient PlayerHealth myPlayerHealth;

    /**
     * The panel containing movement buttons.
     */
    private MovementButtonPanel myMovementButtonPanel;

    /**
     * The handler for keyboard input.
     */
    KeyboardsHandler keyboardsHandler = new KeyboardsHandler();

    /**
     * The label displaying the current room image.
     */
    private JLabel myRoomImage;

    /**
     * The manager for handling sound in the game.
     */
    private transient SoundManager mySoundManager;

    /**
     * A boolean indicating whether a fade effect is needed.
     */
    private boolean myNeedFade;

    /**
     * Constructs a GamePanel object.
     */
    public GamePanel() {
        myGameOver = false;
        myRoomImage = new JLabel();
        mySoundManager = SoundManager.getInstance();
        myNeedFade = false;

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

    /**
     * Sets the Game instance associated with this panel.
     *
     * @param game the Game instance to set
     */
    public void setMyGame(Game game) {
        myGame = game;
        addKeyListener(myGame.getKeyHandler());
        myPlayerHealth = new PlayerHealth(myGame.getMyPlayer());
        this.setFocusable(true);
    }

    /**
     * Updates the game state.
     */
    public void update(){
        //System.out.println("The game is running");
        if (Player.getInstance().getMyVictory()) {
            // Dispose all open JDialog instances
            for (Window window : Window.getWindows()) {
                if (window instanceof JDialog) {
                    window.dispose();
                }
            }

            // Check if the GamePanel has a Window ancestor
            Window windowAncestor = SwingUtilities.getWindowAncestor(this);
            if (windowAncestor instanceof GameFrame) {
                GameFrame frame = (GameFrame) windowAncestor;
                frame.switchToEndGamePanel();
                myGameThread = null;
            }
        }

        myGame.getMyPlayerManager().updateSpriteKeyPressed();
        myGameOver = Player.getInstance().getMyVictory();
        if (!myGameOver) {
            myGameOver = Maze.getInstance().getMyRoom(Player.getInstance().getMyLocationRow(),
                    Player.getInstance().getMyLocationCol()).softLockCheck();
        }
    }

    /**
     * Updates the room image displayed on the panel.
     */
    public void updateRoomImage() {
        //System.out.println("the room is being updated");
        BufferedImage mapImage = null;
        String roomFileName = Maze.getInstance().getMyRoom(Player.getInstance().getMyLocationRow(), Player.getInstance().getMyLocationCol()).getRoomFileName();
        try {
            InputStream inputStream = GamePanel.class.getResourceAsStream("/Resource/MazeRooms/" + roomFileName);
            if (inputStream != null) {
                mapImage = ImageIO.read(inputStream);
            } else {
                System.err.println("Resource file not found: " + roomFileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (mapImage != null) {
            ImageIcon mapImageIcon = new ImageIcon(mapImage);
            myRoomImage.setIcon(mapImageIcon);
            myRoomImage.setHorizontalAlignment(JLabel.CENTER);
            myRoomImage.setVerticalAlignment(JLabel.CENTER);
            add(myRoomImage, BorderLayout.CENTER);
            revalidate();
            repaint();
        }
    }

    /**
     * Paints the game components on the panel.
     *
     * @param theGraph the graphics context to paint on
     */
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

    /**
     * Runs the game loop.
     */
    @Override
    public void run() {
        //Setting up game loop with better FPS
        double drawInterval = (double) 1000000000 / ScreenSetting.FPS; // 0.0166 seconds
        double nextDrawTime = System.nanoTime() + drawInterval;
        long lastUpdateTime = System.currentTimeMillis();

        while (myGameThread != null) {
            // Update information player movement postions
            //update();
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

    /**
     * Starts the game thread.
     */
    public void startGameThread() {
        myGameThread = new Thread(this);
        myGameThread.start();
    }

    /**
     * Retrieves the player manager associated with the game.
     *
     * @return the PlayerManager instance used for managing the player
     */
    public PlayerManager getMyPlayerManager() {
        return myGame.getMyPlayerManager();
    }

    /**
     * Retrieves the game thread associated with this panel.
     *
     * @return the game thread
     */
    public Thread getMyGameThread() {
        return myGameThread;
    }

    /**
     * Sets the game thread associated with this panel.
     *
     * @param theThread the game thread to set
     */
    public void setMyGameThread(Thread theThread) {
        myGameThread = theThread;
    }

    /**
     * Sets the game over state.
     *
     * @param theGameOver the game over state to set
     */
    public void setGameOver(boolean theGameOver){
        myGameOver = theGameOver;
    }

    /**
     * Checks if the game is over.
     *
     * @return true if the game is over, otherwise false
     */
    public boolean isGameOver() {
        return myGameOver;
    }

    /**
     * Sets the movement button panel associated with this game panel.
     *
     * @param theMovementButtonPanel the movement button panel to set
     */
    public void setMyMovementButtonPanel(MovementButtonPanel theMovementButtonPanel) {
        myMovementButtonPanel = theMovementButtonPanel;
    }

    /**
     * Retrieves the movement button panel associated with this game panel.
     *
     * @return the movement button panel
     */
    public MovementButtonPanel getMyMovementButtonPanel() {
        return myMovementButtonPanel;
    }
}