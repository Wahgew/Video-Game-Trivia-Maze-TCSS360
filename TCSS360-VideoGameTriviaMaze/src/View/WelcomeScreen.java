package View;

import Model.Maze;
import Model.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class WelcomeScreen extends JPanel {
    private static final int Screen_Width = ScreenSetting.Screen_Width;
    private static final int Screen_Height = ScreenSetting.Screen_Height;
    private JButton myNewGameButton;
    private JButton myLoadGameButton;
    private JButton myAboutUsButton;
    private JButton myExitButton;
    private final Image myBackground;
    private final ImageIcon Speed_Icon = new ImageIcon(getClass().getResource("/Resource/SPEED_CRYING.gif"));
    private final ImageIcon backgroundIcon= new ImageIcon(getClass().getResource("/Resource/Background.jpg"));

    public WelcomeScreen() {
        setPreferredSize(new Dimension(Screen_Width, Screen_Height));

        myBackground = backgroundIcon.getImage();

        setLayout(null);
        setUpButtons();
    }
    private void setUpButtons() {
        myNewGameButton = new JButton();
        myLoadGameButton = new JButton();
        myAboutUsButton = new JButton();
        myExitButton = new JButton();

        ImageIcon newGameIcon = new ImageIcon(getClass().getResource("/Resource/NewGame.jpg"));
        ImageIcon loadGameIcon = new ImageIcon(getClass().getResource("/Resource/LoadGame.jpg"));
        ImageIcon aboutUsIcon = new ImageIcon(getClass().getResource("/Resource/AboutMe.jpg"));
        ImageIcon exitIcon = new ImageIcon(getClass().getResource("/Resource/Exit.jpg"));

        myNewGameButton.setIcon(newGameIcon);
        myLoadGameButton.setIcon(loadGameIcon);
        myAboutUsButton.setIcon(aboutUsIcon);
        myExitButton.setIcon(exitIcon);

        myNewGameButton.setHorizontalTextPosition(SwingConstants.CENTER);
        myLoadGameButton.setHorizontalTextPosition(SwingConstants.CENTER);
        myAboutUsButton.setHorizontalTextPosition(SwingConstants.CENTER);
        myExitButton.setHorizontalTextPosition(SwingConstants.CENTER);

        myNewGameButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        myLoadGameButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        myAboutUsButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        myExitButton.setVerticalTextPosition(SwingConstants.BOTTOM);

        myNewGameButton.setBorderPainted(false);
        myLoadGameButton.setBorderPainted(false);
        myAboutUsButton.setBorderPainted(false);
        myExitButton.setBorderPainted(false);

        myNewGameButton.setContentAreaFilled(false);
        myLoadGameButton.setContentAreaFilled(false);
        myAboutUsButton.setContentAreaFilled(false);
        myExitButton.setContentAreaFilled(false);

        myNewGameButton.setBounds(390,235, 170, 60); //150 50
        myLoadGameButton.setBounds(390,315, 170, 60);
        myAboutUsButton.setBounds(390,395, 170, 60);
        myExitButton.setBounds(390,475, 170, 60);

        addButtonListener();

        add(myNewGameButton);
        add(myLoadGameButton);
        add(myAboutUsButton);
        add(myExitButton);
    }
    public void addButtonListener() {
        myNewGameButton.addActionListener(e -> {
            GameFrame gameFrame = (GameFrame) SwingUtilities.getWindowAncestor(WelcomeScreen.this);
            gameFrame.switchToGamePanel(new GamePanel());
        });
        myLoadGameButton.addActionListener(e -> {
            GameFrame gameFrame = (GameFrame) SwingUtilities.getWindowAncestor(WelcomeScreen.this);
            if (gameFrame.getMyGamePanel().loadGame()) {
                gameFrame.switchToGamePanel(gameFrame.getMyGamePanel());
            }
        });
        myAboutUsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final int jOption = JOptionPane.showConfirmDialog(null, "Game: Trivia Labyrinth Maze.\n" +
                                "Author: Peter W Madin, Ken Egawa and Sopheanith Ny.\nVersion: 1.0.\nJDK: Java 21.", "About",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.DEFAULT_OPTION, Speed_Icon);
            }
        });
        myExitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final int jOption = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to Exit?", "Exit",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,Speed_Icon);
                if (jOption == JOptionPane.YES_NO_OPTION) {
                    System.exit(0);
                }
            }
        });
    }
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        theGraphics.drawImage(myBackground,0, 0, getWidth(), getHeight(), this);
    }

    public JButton getMyNewGameButton() {
        return myNewGameButton;
    }
}
