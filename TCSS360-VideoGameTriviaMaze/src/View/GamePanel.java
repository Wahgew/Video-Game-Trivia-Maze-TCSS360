package View;

import Controller.MazeController;
import Model.Direction;
import Model.Maze;
import Model.Player;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class GamePanel extends JPanel implements Runnable{
    private JLayeredPane myLayeredPane;
    private transient Thread myGameThread;
    private boolean myGameOver;

    private JButton myUpArrowButton;
    private JButton myDownArrowButton;
    private JButton myLeftArrowButton;
    private JButton myRightArrowButton;

    public GamePanel() {
        myGameOver = false;

        this.setPreferredSize(new Dimension(ScreenSetting.Screen_Width, ScreenSetting.Screen_Height));
        //this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.setLayout(new BorderLayout());

        add(createLayeredPanel(), BorderLayout.WEST);
        addButtonListener();
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


//    public void saveGame() {
//        try {
//            FileOutputStream fileOut = new FileOutputStream("game_state.ser");
//            ObjectOutputStream out = new ObjectOutputStream(fileOut);
//            out.writeObject(myGame);
//            fileOut.close();
//            System.out.println("Game have been saved successfully.");
//            showDialog(new SaveLoadPanel("Saved"));
//        } catch (FileNotFoundException e) {
//            System.out.println("Error occured while saving the game state: " + e.getMessage());
//        }
//    }

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
    @Override
    public void run() {
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
    public void updateButtonStatus() {
        myUpArrowButton.setEnabled(Player.getInstance().validPlayerMove(Direction.NORTH));
        myDownArrowButton.setEnabled(Player.getInstance().validPlayerMove(Direction.SOUTH));
        myLeftArrowButton.setEnabled(Player.getInstance().validPlayerMove(Direction.WEST));
        myRightArrowButton.setEnabled(Player.getInstance().validPlayerMove(Direction.EAST));
    }
}
