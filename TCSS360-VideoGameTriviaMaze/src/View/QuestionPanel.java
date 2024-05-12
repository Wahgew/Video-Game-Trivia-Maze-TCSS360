package View;

import Model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;


public class QuestionPanel implements ActionListener {
    private final static Color White = new Color(255,255, 255);
    private final static Color Blue = new Color(210, 246, 250);
    private final static Color LightBlue = new Color(123, 195, 203);

    private final JDialog myDialog;
    private final JPanel myQuestionPanel;
    private final JPanel myAnswerOptionPanel;
    private final JLabel myQuestionArea;
    private final JButton myAnswerButton1;
    private final JButton myAnswerButton2;
    private final JButton myAnswerButton3;
    private final JButton myAnswerButton4;

    private final GamePanel myGamePanel;
    private final Door myDoor;

    private String myCorrectAnswer; //


    public QuestionPanel(final Door theDoor, final GamePanel theGamePanel) {
        if (theDoor == null || theGamePanel == null) {
            throw new IllegalArgumentException("The doors and game panel cannot be null");
        }
        this.myGamePanel = theGamePanel;
        this.myDoor = theDoor;
        this.myDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(theGamePanel), false);
        this.myQuestionPanel = new JPanel();
        this.myAnswerOptionPanel = new JPanel();
        this.myQuestionArea = new JLabel();
        this.myAnswerButton1 = new JButton();
        this.myAnswerButton2 = new JButton();
        this.myAnswerButton3 = new JButton();
        this.myAnswerButton4 = new JButton();
        loadQuestionOption(theDoor.askQuestion()); //TODO: Load question should only happen when player interacts with a door.

        popUpUI();
    }

    public String getPlayerAnswer() {
        return myCorrectAnswer;
    }

    private void loadQuestionOption(final Question theQuestion) {
        if (theQuestion == null) {
            throw new IllegalArgumentException("Question cannot be null");
        }

        myQuestionArea.setText(theQuestion.getQuestion());

        TreeMap<String, Boolean> answerChoices = theQuestion.getAnswers().getAnswerChoices();

        int optionCount = 1;
        for (Map.Entry<String, Boolean> entry : answerChoices.entrySet()) {
            switch (optionCount) {
                case 1:
                    myAnswerButton1.setText(entry.getKey());
                    break;
                case 2:
                    myAnswerButton2.setText(entry.getKey());
                    break;
                case 3:
                    myAnswerButton3.setText(entry.getKey());
                    break;
                case 4:
                    myAnswerButton4.setText(entry.getKey());
                default:
            }

            optionCount++;
            if (optionCount > 4) {
                break;
            }
        }

        myCorrectAnswer = theQuestion.getCorrectAnswer();
    }

    /**
     * The action performed method for all the answer option button.
     *
     * @param theEvent the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent theEvent) {
        String playerAnswer = null;
        JButton[] answerButtons = {myAnswerButton1, myAnswerButton2, myAnswerButton3, myAnswerButton4};

        for (JButton button : answerButtons) {
            if (theEvent.getSource() == button) {
                playerAnswer = button.getText();
                break;
            }
        }

        if (playerAnswer != null) {
            checkAnswers(playerAnswer);
        }
    }

    public void checkAnswers(final String thePlayerAnswer) {
        if (thePlayerAnswer == null) {
            throw new IllegalArgumentException("The player's answer cannot be null");
        }

        if (thePlayerAnswer.equals(myCorrectAnswer)) {
            Player.getInstance().getQuestionsAnswered().put(myDoor.getQuestionObject().getID(), true);
            myDoor.setMyLockStatus(true);
            dialogForResult("Correct");
        } else {
            Player.getInstance().getQuestionsAnswered().put(myDoor.getQuestionObject().getID(), false);
            myDoor.setMyAttemptStatus(true);
            myGamePanel.getMyGame().getMyPlayer().decreaseHealth();
            if (myGamePanel.getMyGame().getMyPlayer().getMyHealth() > 0) {
                dialogForResult("Incorrect");
            } else {
                myDialog.dispose();
                GameFrame frame = (GameFrame) SwingUtilities.getWindowAncestor(myGamePanel);
                frame.switchToEndGamePanel();
                myGamePanel.deleteSavedGames();
            }
        }
    }

    public void dialogForResult(final String theCorrectAnswer) {
        resultPanel rePanel = new resultPanel(theCorrectAnswer);
        myDialog.dispose();
        JDialog dialog = new JDialog(myDialog, "Dialog", true);
        dialog.getContentPane().add(rePanel);
        dialog.setUndecorated(true);
        dialog.pack();
        dialog.setLocationRelativeTo(myGamePanel);
        dialog.setVisible(true);
    }
    /**
     * Set up the GUI pop up when the player stand infront of the doors
     */
//    public void popUpUI() {
//        //Pop up dialog
//        int radiusCorner = 40;
//        int gapTop = 25;
//        int gapSides = 45;
//        myDialog.setTitle("Do you want to be our Maze expert? Type beat!");
//        myDialog.setSize(400,300);
//        myDialog.setLayout(new BorderLayout());
//        myDialog.setUndecorated(true);
//        myDialog.setShape(new RoundRectangle2D.Double(0,0,400,300,radiusCorner, radiusCorner));
//        myQuestionPanel.setBackground(White);
//        myQuestionPanel.setLayout(null);
//
//        myDialog.setLocationRelativeTo(myGamePanel);
//
//        //Text area the question
//        myQuestionPanel.setFont(new Font("Berlin Sans FB",Font.BOLD, 20));
//        myQuestionPanel.setBackground(Color.BLACK);
//        myQuestionArea.setBounds(gapSides, gapTop,
//                myDialog.getWidth() - 2 * gapSides, 180 - gapTop);
//        myQuestionArea.setLineWrap(true);
//        myQuestionArea.setWrapStyleWord(true);
//        myQuestionArea.setEditable(false);
//        myQuestionArea.setFocusable(false);
//        myQuestionPanel.add(myQuestionArea);
//
//        //
//        Font fontForEachButtons = new Font("Berlin Sans FB",Font.PLAIN, 16);
//        myAnswerButton1.addActionListener(this);
//        myAnswerButton1.setFont(fontForEachButtons);
//        myAnswerButton1.setBackground(Blue);
//
//        myAnswerButton2.addActionListener(this);
//        myAnswerButton2.setFont(fontForEachButtons);
//        myAnswerButton2.setBackground(LightBlue);
//
//        myAnswerButton3.addActionListener(this);
//        myAnswerButton3.setFont(fontForEachButtons);
//        myAnswerButton3.setBackground(LightBlue);
//
//        myAnswerButton4.addActionListener(this);
//        myAnswerButton4.setFont(fontForEachButtons);
//        myAnswerButton4.setBackground(Blue);
//
//        myAnswerOptionPanel.add(myAnswerButton1);
//        myAnswerOptionPanel.add(myAnswerButton2);
//        myAnswerOptionPanel.add(myAnswerButton3);
//        myAnswerOptionPanel.add(myAnswerButton4);
//        myQuestionPanel.setLayout(new GridLayout(2,2));
//        myQuestionPanel.setPreferredSize(new Dimension(100,120));
//
//        myDialog.add(myQuestionPanel, BorderLayout.CENTER);
//        myDialog.add(myAnswerOptionPanel, BorderLayout.SOUTH);
//
//        myDialog.setVisible(true);
//        myDialog.pack();
//    }
    public void popUpUI() {
        // Pop-up dialog
        int radiusCorner = 40;
        int gapTop = 25;
        int gapSides = 45;
        myDialog.setTitle("Do you want to be our Maze expert? Type beat!");
        myDialog.setPreferredSize(new Dimension(700, 200)); // Increased width for better button layout
        myDialog.setLayout(new BorderLayout());
        myDialog.setUndecorated(true);
        myDialog.setShape(new RoundRectangle2D.Double(0, 0, 700, 200, radiusCorner, radiusCorner));
        myQuestionPanel.setBackground(new Color(51, 51, 51)); // Dark gray background
        myQuestionPanel.setLayout(new BorderLayout());
        myAnswerOptionPanel.setPreferredSize(new Dimension(400, 120));

        // Pack the dialog to the preferred size and center it relative to the game panel
        myDialog.pack();
        myDialog.setLocationRelativeTo(myGamePanel);

        // Text area for the question
        myQuestionArea.setFont(new Font("Arial", Font.PLAIN, 16));
        myQuestionArea.setForeground(Color.WHITE); // White text color
        myQuestionArea.setBackground(new Color(51, 51, 51)); // Dark gray background
        myQuestionArea.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the question text
        //myQuestionArea.setLineWrap(true);
        //myQuestionArea.setWrapStyleWord(true);
        //myQuestionArea.setEditable(false);
        myQuestionArea.setFocusable(false);
        //myQuestionArea.setBounds(gapSides, gapTop, myDialog.getWidth() - 2 * gapSides, 120); // Adjusted height for better layout
        //myQuestionArea.setBounds(gapSides, gapTop,
                //myDialog.getWidth() - 2 * gapSides, 180 - gapTop);
        myQuestionPanel.add(myQuestionArea, BorderLayout.NORTH);
        //myQuestionPanel.add(myQuestionArea);
        //myQuestionPanel.add(myQuestionArea, BorderLayout.CENTER); // Add to the center of the panel

        // Set up the answer option panel
//        myAnswerOptionPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Use FlowLayout with horizontal and vertical gaps
//        myAnswerOptionPanel.setBackground(new Color(51, 51, 51)); // Dark gray background

        myAnswerOptionPanel.setLayout(new GridLayout(2, 2)); // 2x2 grid layout with gaps
        myAnswerOptionPanel.setBackground(new Color(51, 51, 51)); // Dark gray background

        // Add the answer buttons to the answer option panel
        myAnswerButton1.addActionListener(this);
        myAnswerButton1.setBackground(new Color(102, 102, 102)); // Grayish button color
        myAnswerButton1.setForeground(Color.WHITE); // White text color
        myAnswerButton1.setFont(new Font("Arial", Font.PLAIN, 20)); // Adjust the font size as needed
        myAnswerButton1.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true)); // Rounded button border

        myAnswerButton2.addActionListener(this);
        myAnswerButton2.setBackground(new Color(102, 102, 102));
        myAnswerButton2.setForeground(Color.WHITE);
        myAnswerButton2.setFont(new Font("Arial", Font.PLAIN, 20));
        myAnswerButton2.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));

        myAnswerButton3.addActionListener(this);
        myAnswerButton3.setBackground(new Color(102, 102, 102));
        myAnswerButton3.setForeground(Color.WHITE);
        myAnswerButton3.setFont(new Font("Arial", Font.PLAIN, 20));
        myAnswerButton3.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));

        myAnswerButton4.addActionListener(this);
        myAnswerButton4.setBackground(new Color(102, 102, 102));
        myAnswerButton4.setForeground(Color.WHITE);
        myAnswerButton4.setFont(new Font("Arial", Font.PLAIN, 20));
        myAnswerButton4.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));

        myAnswerOptionPanel.add(myAnswerButton1);
        myAnswerOptionPanel.add(myAnswerButton2);
        myAnswerOptionPanel.add(myAnswerButton3);
        myAnswerOptionPanel.add(myAnswerButton4);

        // Add the question panel and answer option panel to the dialog
        myDialog.add(myQuestionPanel, BorderLayout.NORTH);
        myDialog.add(myAnswerOptionPanel, BorderLayout.CENTER);

        myDialog.setVisible(true);
    }

    static class resultPanel extends JPanel {
        private static final int border = 15;
        private static final Color DARK_GRAY = new Color(127, 127, 127);
        private static final Color LIGHT_GRAY = new Color(209, 209, 209);
        private static final Color RED = new Color(139, 0, 0);
        private static final Color PINK = new Color(245,218,223);
        private static final Color SUPER_LIGHT_GREEN = new Color(230,255,239);
        private static final Color GREEN = new Color(95, 133, 117);
        private static final Color DARK_GREEN = new Color(53, 94, 59);
        private static final Color LIGHT_GREEN = new Color(193, 225, 193);

        public resultPanel(final String thePanel) {
            JButton continueButton = new JButton("Continue");
            JLabel resultLabel1;
            JLabel resultLabel2;

            if (thePanel.equalsIgnoreCase("Correct")) {
                setBackground(LIGHT_GRAY);
                resultLabel1 = new JLabel("THE ANSWER IS CORRECT!");
                resultLabel1.setForeground(DARK_GREEN);
                resultLabel2 = new JLabel("The doors is unlocked! Please Enter.");
                resultLabel2.setForeground(GREEN);
                continueButton.setBackground(SUPER_LIGHT_GREEN);
                continueButton.setForeground(DARK_GREEN);
                continueButton.setBorder(BorderFactory.createLineBorder(GREEN,1));
            } else if (thePanel.equalsIgnoreCase("Incorrect")) {
                setBackground(DARK_GRAY);
                resultLabel1 = new JLabel("THE ANSWER IS INCORRECT!");
                resultLabel1.setForeground(RED);
                resultLabel2 = new JLabel("The doors is locked!");
                resultLabel2.setForeground(DARK_GRAY);
                continueButton.setBackground(SUPER_LIGHT_GREEN);
                continueButton.setForeground(RED);
                continueButton.setBorder(BorderFactory.createLineBorder(LIGHT_GRAY,1));
            } else {
                throw new IllegalArgumentException("Invalid input");
            }

            JPanel resultPanel1 = new JPanel();
            resultPanel1.setOpaque(false);
            resultPanel1.add(resultLabel1);

            JPanel resultPanel2 = new JPanel();
            resultPanel2.setOpaque(false);
            resultPanel2.add(resultLabel2);

            setBorder(BorderFactory.createEmptyBorder(border,border,border,border));
            setLayout(new GridLayout(3,1,10,10));
            add(resultPanel1);
            add(resultPanel2);
            add(continueButton);

            continueButton.addActionListener(e -> {
               Component component = (Component) e.getSource();
               Window window = SwingUtilities.getWindowAncestor(component);
               window.dispose();
            });

        }
    }


}
