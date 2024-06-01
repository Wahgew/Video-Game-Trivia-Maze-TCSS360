package View;

import Controller.MazeController;
import Model.Direction;
import Model.Maze;
import Model.Player;

import javax.swing.*;
import java.awt.*;
/**
 * Panel containing buttons for player movement.
 *
 * @author Peter W Madin, Ken Egawa, Sopheanith Ny
 * @version 6/7/2024
 */
public class MovementButtonPanel extends JPanel {
    /**
     * Button for moving up
     */
    private static JButton myUpArrowButton;
    /**
     * Button for moving down
     */
    private static JButton myDownArrowButton;
    /**
     * Button for moving left
     */
    private static JButton myLeftArrowButton;
    /**
     * Button for moving right
     */
    private static JButton myRightArrowButton;
    /**
     * Test button for internal use
     */
    private JButton myTestButton;
    /**
     * Controller for handling player movement
     */
    private final MazeController myController;
    /**
     * Panel containing the game view
     */
    private final GamePanel myGamePanel;

    /**
     * Constructs a MovementButtonPanel.
     *
     * @param theGamePanel The panel containing the game view.
     */
    public MovementButtonPanel(GamePanel theGamePanel) {
        myGamePanel = theGamePanel;
        myController = new MazeController();
        //setLayout(new GridLayout(3, 3, 10, 10));
        setLayout(null);
        setPreferredSize(new Dimension(300, 200));

        createButtons();
        addButtonListeners();
        checkButtons();
        checkIcons();
    }

    /**
     * Creates movement buttons and sets up their appearance.
     */
    private void createButtons() {
        ImageIcon upArrowIcon = resizeImage("/Resource/upIcon.png", 60, 60);
        ImageIcon downArrowIcon = resizeImage("/Resource/downIcon.png", 60, 60);
        ImageIcon rightArrowIcon = resizeImage("/Resource/rightIcon.png", 70, 50);
        ImageIcon leftArrowIcon = resizeImage("/Resource/leftIcon.png", 70, 50);

        myTestButton = new JButton("");
        myUpArrowButton = new JButton(upArrowIcon);
        myDownArrowButton = new JButton(downArrowIcon);
        myLeftArrowButton = new JButton(leftArrowIcon);
        myRightArrowButton = new JButton(rightArrowIcon);

        myTestButton.setBounds(120, 85, 40, 30); // Set the bounds of the button
        myUpArrowButton.setBounds(110, 25, 60, 60);
        myDownArrowButton.setBounds(110, 115, 60, 60);
        myLeftArrowButton.setBounds(50, 75, 70, 50);
        myRightArrowButton.setBounds(160, 76, 70, 50);

        // Configure button appearance
        configureButtons();

        myTestButton.setVisible(false);

        add(myTestButton);
        add(myUpArrowButton);
        add(myDownArrowButton);
        add(myLeftArrowButton);
        add(myRightArrowButton);

    }

    /**
     * Configures the appearance of movement buttons.
     */
    private void configureButtons() {
        myUpArrowButton.setBorderPainted(false);
        myDownArrowButton.setBorderPainted(false);
        myLeftArrowButton.setBorderPainted(false);
        myRightArrowButton.setBorderPainted(false);

        myUpArrowButton.setContentAreaFilled(false);
        myDownArrowButton.setContentAreaFilled(false);
        myLeftArrowButton.setContentAreaFilled(false);
        myRightArrowButton.setContentAreaFilled(false);
    }

    /**
     * Adds action listeners to movement buttons.
     */
    private void addButtonListeners() {
        myUpArrowButton.addActionListener(e -> handleMovement(Direction.NORTH));
        myDownArrowButton.addActionListener(e -> handleMovement(Direction.SOUTH));
        myLeftArrowButton.addActionListener(e -> handleMovement(Direction.WEST));
        myRightArrowButton.addActionListener(e -> handleMovement(Direction.EAST));
    }

    /**
     * Handles player movement in response to button clicks.
     *
     * @param theDirection The direction of movement.
     */
    public void handleMovement(Direction theDirection) {
        setButtonsState(false);
        myController.handlePlayerMovement(theDirection, myGamePanel);
        myGamePanel.requestFocus();
    }

    /**
     * Sets the state of all movement buttons.
     *
     * @param theState The state to set.
     */
    public void setButtonsState(boolean theState) {
        myUpArrowButton.setEnabled(theState);
        myDownArrowButton.setEnabled(theState);
        myLeftArrowButton.setEnabled(theState);
        myRightArrowButton.setEnabled(theState);
    }

    /**
     * Sets the state of a specific movement button.
     *
     * @param theDoor  The index of the button (0: up, 1: right, 2: down, 3: left).
     * @param theState The state to set.
     */
    public void setButtonState(int theDoor, boolean theState) {
        switch (theDoor) {
            case 0:
                myUpArrowButton.setEnabled(theState);
                break;
            case 1:
                myRightArrowButton.setEnabled(theState);
                break;
            case 2:
                myDownArrowButton.setEnabled(theState);
                break;
            case 3:
                myLeftArrowButton.setEnabled(theState);
                break;
            default:
                throw new IllegalArgumentException("Invalid door index/case: " + theDoor);
        }
    }

    /**
     * Sets the icon of a specific movement button.
     *
     * @param theDoor The index of the button (0: up, 1: right, 2: down, 3: left).
     * @param theIcon The path to the icon image.
     */
    public static void setButtonIcon(int theDoor, String theIcon) {
        switch (theDoor) {
            case 0:
                myUpArrowButton.setIcon(resizeImage(theIcon, 60, 85));
                break;
            case 1:
                myRightArrowButton.setIcon(resizeImage(theIcon, 85, 60));
                break;
            case 2:
                myDownArrowButton.setIcon(resizeImage(theIcon, 60, 85));
                break;
            case 3:
                myLeftArrowButton.setIcon(resizeImage(theIcon, 85, 60));
                break;
            default:
                throw new IllegalArgumentException("Invalid door index/case: " + theDoor);
        }
    }

    /**
     * Checks and updates the state of all movement buttons based on player's valid moves.
     */
    public void checkButtons() {
        setButtonState(0, Player.getInstance().validPlayerMove(Direction.NORTH));
        setButtonState(1, Player.getInstance().validPlayerMove(Direction.EAST));
        setButtonState(2, Player.getInstance().validPlayerMove(Direction.SOUTH));
        setButtonState(3, Player.getInstance().validPlayerMove(Direction.WEST));
    }

    /**
     * Checks and updates the icons of all movement buttons based on player's current position.
     */
    public void checkIcons() {
        int playerRow = Player.getInstance().getMyLocationRow();
        int playerCol = Player.getInstance().getMyLocationCol();

        setButtonIcon(0, Maze.getInstance().getMyRoom(playerRow, playerCol).
                getMyDoor(Direction.NORTH).getMyMovementIcon());
        setButtonIcon(1, Maze.getInstance().getMyRoom(playerRow, playerCol).
                getMyDoor(Direction.EAST).getMyMovementIcon());
        setButtonIcon(2, Maze.getInstance().getMyRoom(playerRow, playerCol).
                getMyDoor(Direction.SOUTH).getMyMovementIcon());
        setButtonIcon(3, Maze.getInstance().getMyRoom(playerRow, playerCol).
                getMyDoor(Direction.WEST).getMyMovementIcon());

    }

    /**
     * Loads and sets the icons of all movement buttons.
     */
    public static void loadIcons() {
        int playerRow = Player.getInstance().getMyLocationRow();
        int playerCol = Player.getInstance().getMyLocationCol();

        setButtonIcon(0, Maze.getInstance().getMyRoom(playerRow, playerCol).
                getMyDoor(Direction.NORTH).getMyMovementIcon());
        setButtonIcon(1, Maze.getInstance().getMyRoom(playerRow, playerCol).
                getMyDoor(Direction.EAST).getMyMovementIcon());
        setButtonIcon(2, Maze.getInstance().getMyRoom(playerRow, playerCol).
                getMyDoor(Direction.SOUTH).getMyMovementIcon());
        setButtonIcon(3, Maze.getInstance().getMyRoom(playerRow, playerCol).
                getMyDoor(Direction.WEST).getMyMovementIcon());
    }

    /**
     * Resizes an image to the specified dimensions.
     *
     * @param path   The path to the image.
     * @param width  The width of the resized image.
     * @param height The height of the resized image.
     * @return The resized image icon.
     */
    private static ImageIcon resizeImage(String path, int width, int height) {
        //System.out.println(MovementButtonPanel.class.getResource(path));
        ImageIcon icon = new ImageIcon(MovementButtonPanel.class.getResource(path));
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImg);
    }

}