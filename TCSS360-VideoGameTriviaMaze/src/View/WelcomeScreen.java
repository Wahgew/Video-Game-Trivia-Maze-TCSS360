package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class WelcomeScreen extends JPanel {
    private static final int Screen_Width = ScreenSetting.Screen_Width;
    private static final int Screen_Height = ScreenSetting.Screen_Height;
    private static final Font buttonFont = new Font("Banhschrift", Font.BOLD, 16);
    private JButton myNewGameButton;
    private JButton myLoadGameButton;
    private JButton myAboutUsButton;
    private JButton myExitButton;
    private final Image myBackground;
    private final Image myIconLogo;

    public WelcomeScreen() {
        setPreferredSize(new Dimension(Screen_Width, Screen_Height));

//        ImageIcon backgroundIcon = new ImageIcon
//                (("C:\\Users\\nysop\\Desktop\\Trivia\\src\\Resource\\Background.jpg")); //Window
//        ImageIcon backgroundIcon = new ImageIcon
//                (("/Users/nithh/Desktop/Trivia/src/Resource/Background.jpg")); //macOS
        ImageIcon backgroundIcon= new ImageIcon(getClass().getResource("/Resource/Background.jpg"));
        myBackground = backgroundIcon.getImage();

        ImageIcon logoIcon = new ImageIcon
                (("C:\\Users\nysop\\Desktop\\Trivia\\src\\Resource\\Logo.jpg")); //Window
        myIconLogo = logoIcon.getImage();

        setLayout(null);
        setUpButtons();
    }
    private void setUpButtons() {
        myNewGameButton = new JButton();
        myLoadGameButton = new JButton();
        myAboutUsButton = new JButton();
        myExitButton = new JButton();

        //ImageIcon newGameIcon = new ImageIcon("/Users/nithh/Desktop/Trivia/src/Resource/NewGame.jpg"); //MacOS
        ImageIcon newGameIcon = new ImageIcon(getClass().getResource("/Resource/NewGame.jpg"));
        //ImageIcon loadGameIcon = new ImageIcon("/Users/nithh/Desktop/Trivia/src/Resource/LoadGame.jpg"); //MacOS
        ImageIcon loadGameIcon = new ImageIcon(getClass().getResource("/Resource/LoadGame.jpg"));
        //ImageIcon aboutUsIcon = new ImageIcon("/Users/nithh/Desktop/Trivia/src/Resource/AboutMe.jpg"); //MacOS
        ImageIcon aboutUsIcon = new ImageIcon(getClass().getResource("/Resource/AboutMe.jpg"));
        //ImageIcon exitIcon = new ImageIcon("/Users/nithh/Desktop/Trivia/src/Resource/Exit.jpg"); //MacOS
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

//        myNewGameButton.setFont(buttonFont);
//        myLoadGameButton.setFont(buttonFont);
//        myAboutUsButton.setFont(buttonFont);
//        myExitButton.setFont(buttonFont);
//
//        myNewGameButton.setBackground(Color.WHITE);
//        myLoadGameButton.setBackground(Color.WHITE);
//        myAboutUsButton.setBackground(Color.WHITE);
//        myExitButton.setBackground(Color.WHITE);
//
//        myNewGameButton.setForeground(Color.BLACK);
//        myLoadGameButton.setForeground(Color.BLACK);
//        myAboutUsButton.setForeground(Color.BLACK);
//        myExitButton.setForeground(Color.BLACK);
//
//        myNewGameButton.setBorder(BorderFactory.createLineBorder(Color.GRAY, 4));
//        myLoadGameButton.setBorder(BorderFactory.createLineBorder(Color.GRAY, 4));
//        myAboutUsButton.setBorder(BorderFactory.createLineBorder(Color.GRAY, 4));
//        myExitButton.setBorder(BorderFactory.createLineBorder(Color.GRAY, 4));

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
            //gameFrame.switchToGamePanel(new GamePanel());
        });
        myLoadGameButton.addActionListener(e -> {
            GameFrame loadGame = (GameFrame) SwingUtilities.getWindowAncestor(WelcomeScreen.this);
            //loadGame.switchToGamePanel(new getMyGamePanel());
        });
        myAboutUsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Game: Game of Carps.\n" +
                        "Author: Sopheanith Ny.\nVersion: 1.0.\nJDK: Java 19.", "About",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
        myExitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final int jOption = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to Exit?", "Exit",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (jOption == JOptionPane.YES_NO_OPTION) {
                    System.exit(0);
                }
            }
        });

    }
    public void paintComponent(final Graphics graphics) {
        super.paintComponent(graphics);
        graphics.drawImage(myBackground,  0, 0, getWidth(), getHeight(), this);
        //graphics.setColor(Color.BLACK);
        int logoX = (getWidth() - myIconLogo.getWidth(this)) / 2; // Center the logo horizontally
        int logoY = 100; // Adjust the vertical position of the logo
        graphics.drawImage(myIconLogo, logoX,logoY, this);

    }

}
