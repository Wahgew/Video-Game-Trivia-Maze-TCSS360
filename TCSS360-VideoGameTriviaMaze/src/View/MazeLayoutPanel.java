package View;

import Model.Maze;

import javax.swing.*;
import java.awt.*;

public class MazeLayoutPanel extends JPanel { // TODO: this should pop up on "New Game" Button, check WelcomeScreen?
    private final JButton myLayoutButton1;
    private final JButton myLayoutButton2;
    private final JButton myLayoutButton3;

    public MazeLayoutPanel() {
        myLayoutButton1 = new JButton("Max Doors Layout"); // TODO: add pictures of layouts?
        myLayoutButton2 = new JButton("Mazey Maze Layout");
        myLayoutButton3 = new JButton("Zig-Zag Layout");

        setLayout(new GridLayout(1, 3));
        add(myLayoutButton1);
        add(myLayoutButton2);
        add(myLayoutButton3);
        setupButtons();
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
