package View;

import Controller.MazeController;
import Model.Direction;
import javax.swing.*;
import java.awt.*;

public class MovementButtonPanel extends JPanel {
    private JButton myUpArrowButton;
    private JButton myDownArrowButton;
    private JButton myLeftArrowButton;
    private JButton myRightArrowButton;
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

        myTestButton.setBounds(120, 85, 40, 30); // Set the bounds of the button
        myUpArrowButton.setBounds(110, 25, 60, 60);
        myDownArrowButton.setBounds(110, 115, 60, 60);
        myLeftArrowButton.setBounds(50, 75, 70, 50);
        myRightArrowButton.setBounds(160, 76, 70, 50);

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
        myController.handlePlayerMovement(theDirection, myGamePanel);
//        if (!myGamePanel.isGameOver()) {
//            MazeController.promptAnswer((GameFrame) SwingUtilities.getWindowAncestor(this));
//        }
    }
    private ImageIcon resizeImage(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(getClass().getResource(path));
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImg);
    }

}