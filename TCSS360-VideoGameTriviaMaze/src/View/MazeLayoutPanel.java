package View;

import Model.Maze;
import Model.Player;

import javax.swing.*;
import java.awt.*;
/**
 * Panel for selecting maze layouts, typically displayed when starting a new game.
 * Allows users to choose from different maze layouts.
 *
 * @author Peter W Madin, Ken Egawa, Sopheanith Ny
 * @version 6/7/2024
 */
public class MazeLayoutPanel extends JPanel { // TODO: this should pop up on "New Game" Button, check WelcomeScreen?

    /**
     * Reference to the main game frame
     */
    private GameFrame myGameFrame;

    /**
     * Button for selecting layout 1
     */
    private final JButton myLayoutButton1;

    /**
     * Button for selecting layout 2
     */
    private final JButton myLayoutButton2;

    /**
     * Button for selecting layout 3
     */
    private final JButton myLayoutButton3;

    /**
     * Constructs a MazeLayoutPanel with the specified GameFrame.
     *
     * @param theFrame The GameFrame to associate with this panel.
     */
    public MazeLayoutPanel(GameFrame theFrame) {
        ImageIcon MaxDoors = resizeImage("/Resource/MaxDoors.jpg", 400, 600);
        ImageIcon ZigZag = resizeImage("/Resource/ZigZag.jpg", 400, 600);
        ImageIcon MazeyMaze = resizeImage("/Resource/MazeyMaze.jpg", 400, 600);

        myLayoutButton1 = new JButton(MaxDoors); // TODO: add pictures of layouts?
        myLayoutButton2 = new JButton(MazeyMaze);
        myLayoutButton3 = new JButton(ZigZag);

        setLayout(new GridLayout(1, 3));
        add(myLayoutButton1);
        add(myLayoutButton3);
        add(myLayoutButton2);

        myGameFrame = theFrame;
        setupButtons();
    }

    /**
     * Sets up action listeners for the layout selection buttons.
     */
    private void setupButtons() {
        //GamePanel gamePanel = new GamePanel();
        myLayoutButton1.addActionListener(e -> {
            Maze.resetMaze("MaxDoorsLayout.txt");
            //Maze.resetMaze("Maze2x2Test.txt");
            Player.resetPlayer();
            Player.getInstance().setMyMazeLayout("MaxDoorsLayout.txt");
            //Player.getInstance().setMyMazeLayout("Maze2x2Test.txt");
            myGameFrame.switchToGamePanel(new GamePanel());
        });
        myLayoutButton2.addActionListener(e -> {
            Maze.resetMaze("MazeyMazeLayout.txt");
            Player.resetPlayer();
            Player.getInstance().setMyMazeLayout("MazeyMazeLayout.txt");
            myGameFrame.switchToGamePanel(new GamePanel());
        });
        myLayoutButton3.addActionListener(e -> {
            //Maze.getInstance("ZigZagLayout.txt");
            Maze.resetMaze("ZigZagLayout.txt");
            Player.resetPlayer();
            Player.getInstance().setMyMazeLayout("ZigZagLayout.txt");
            myGameFrame.switchToGamePanel(new GamePanel());
        });
    }

    /**
     * Resizes an image to the specified width and height.
     *
     * @param path   The path to the image resource.
     * @param width  The desired width of the image.
     * @param height The desired height of the image.
     * @return An ImageIcon object representing the resized image.
     */
    private ImageIcon resizeImage(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(getClass().getResource(path));
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImg);
    }
}
