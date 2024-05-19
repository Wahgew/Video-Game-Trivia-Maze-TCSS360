package View;

import Model.Maze;

import javax.swing.*;
import java.awt.*;

public class MazeLayoutPanel { // TODO: this should pop up on "New Game" Button, check WelcomeScreen?
    private final JPanel myLayoutPanel;
    private final JButton myLayoutButton1;
    private final JButton myLayoutButton2;
    private final JButton myLayoutButton3;

    public MazeLayoutPanel(final GamePanel theGamePanel) {
        myLayoutPanel = new JPanel();
        myLayoutButton1 = new JButton("Max Doors Layout"); // TODO: add pictures of layouts?
        myLayoutButton2 = new JButton("Mazey Maze Layout");
        myLayoutButton3 = new JButton("Zig-Zag Layout");

        myLayoutPanel.setLayout(new GridLayout(1, 3));
        myLayoutPanel.add(myLayoutButton1);
        myLayoutPanel.add(myLayoutButton2);
        myLayoutPanel.add(myLayoutButton3);
        setupButtons();

        theGamePanel.add(myLayoutPanel); // TODO: ??? I don't know how to do GUI
        myLayoutPanel.setVisible(false); // TODO: ??? I am unaware of how to make this popup
    }
    private void setupButtons() {
        myLayoutButton1.addActionListener(e -> {
            Maze.getInstance("MaxDoorsLayout.txt");
        });
        myLayoutButton2.addActionListener(e -> {
            Maze.getInstance("MazeyMazeLayout.txt");
        });
        myLayoutButton3.addActionListener(e -> {
            Maze.getInstance("ZigZagLayout.txt");
        });
    }
}
