package View;

import Model.Maze;
import Model.Player;

import javax.swing.*;
import java.awt.*;

public class MazeLayoutPanel extends JPanel { // TODO: this should pop up on "New Game" Button, check WelcomeScreen?
    private GameFrame myGameFrame;
    private final JButton myLayoutButton1;
    private final JButton myLayoutButton2;
    private final JButton myLayoutButton3;

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
    private ImageIcon resizeImage(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(getClass().getResource(path));
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImg);
    }
}
