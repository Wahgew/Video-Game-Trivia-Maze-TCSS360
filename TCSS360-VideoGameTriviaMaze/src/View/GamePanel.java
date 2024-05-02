package View;

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

        myLayeredPane = new JLayeredPane();
        myLayeredPane.setLayout(new BorderLayout());
        myLayeredPane.add(this);
        myLayeredPane.add(createLayeredPanel());

    }

    private JPanel createLayeredPanel() {
        JPanel layeredPanel = new JPanel();
        layeredPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 1));
        //layeredPanel.setLayout(new GridLayout(3,1,10,10));
        //layeredPanel.setLayout(new BoxLayout(layeredPanel, BoxLayout.LINE_AXIS));


        myUpArrowButton = new JButton("^");
        myDownArrowButton = new JButton("v");
        myLeftArrowButton = new JButton("<");
        myRightArrowButton = new JButton(">");

        layeredPanel.add(myLeftArrowButton);
        layeredPanel.add(myUpArrowButton);
        layeredPanel.add(myDownArrowButton);
        layeredPanel.add(myRightArrowButton);

        return layeredPanel;
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
}
