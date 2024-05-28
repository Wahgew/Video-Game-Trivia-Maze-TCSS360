package View;

import Controller.MazeController;
import Model.Direction;
import Model.Maze;
import Model.Player;

import javax.swing.*;
import java.awt.*;

public class MovementButtonPanel extends JPanel {
    private static JButton myUpArrowButton;
    private static JButton myDownArrowButton;
    private static JButton myLeftArrowButton;
    private static JButton myRightArrowButton;
    private JButton myTestButton;

    private final MazeController myController;
    private final GamePanel myGamePanel;

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

    private void addButtonListeners() {
        myUpArrowButton.addActionListener(e -> handleMovement(Direction.NORTH));
        myDownArrowButton.addActionListener(e -> handleMovement(Direction.SOUTH));
        myLeftArrowButton.addActionListener(e -> handleMovement(Direction.WEST));
        myRightArrowButton.addActionListener(e -> handleMovement(Direction.EAST));
    }

    public void handleMovement(Direction theDirection) {
        setButtonsState(false);
        myController.handlePlayerMovement(theDirection, myGamePanel);
        myGamePanel.requestFocus();
//        if (!myGamePanel.isGameOver()) {
//            MazeController.promptAnswer((GameFrame) SwingUtilities.getWindowAncestor(this));
//        }
    }
    public void setButtonsState(boolean theState) {
        myUpArrowButton.setEnabled(theState);
        myDownArrowButton.setEnabled(theState);
        myLeftArrowButton.setEnabled(theState);
        myRightArrowButton.setEnabled(theState);
    }
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
    public void checkButtons() {
        setButtonState(0, Player.getInstance().validPlayerMove(Direction.NORTH));
        setButtonState(1, Player.getInstance().validPlayerMove(Direction.EAST));
        setButtonState(2, Player.getInstance().validPlayerMove(Direction.SOUTH));
        setButtonState(3, Player.getInstance().validPlayerMove(Direction.WEST));
    }
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


    private static ImageIcon resizeImage(String path, int width, int height) {
        System.out.println(MovementButtonPanel.class.getResource(path));
        ImageIcon icon = new ImageIcon(MovementButtonPanel.class.getResource(path));
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImg);
    }

}