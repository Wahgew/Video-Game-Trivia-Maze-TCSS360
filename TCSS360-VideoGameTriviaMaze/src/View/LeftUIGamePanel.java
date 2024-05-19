package View;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class LeftUIGamePanel extends JPanel {

    public LeftUIGamePanel(GamePanel theGamePanel) {
        setLayout(new BorderLayout());

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(4, 1)); //Create the left panel with 3 sub-panel

        JPanel topLeftPanel = new JPanel();
        topLeftPanel.setBackground(Color.black);

        JPanel middleLeftPanel = new JPanel();
        middleLeftPanel.setBackground(Color.GREEN);

        JPanel bottomLeftPanel = new JPanel();
        bottomLeftPanel.setBackground(Color.BLUE);

        leftPanel.add(topLeftPanel);
        leftPanel.add(new MovementButtonPanel(theGamePanel), BorderLayout.CENTER);;
        leftPanel.add(bottomLeftPanel);

        // Add left and center panels to the main panel
        add(leftPanel, BorderLayout.WEST);
        //add(centerPanel, BorderLayout.CENTER);
    }
}
