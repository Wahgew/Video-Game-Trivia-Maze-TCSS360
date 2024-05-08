package View;

import Model.AnswerData;
import Model.Door;
import Model.Question;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;


public class QuestionPanel implements ActionListener {
    private final static Color White = new Color(255,255, 255);
    private final static Color Blue = new Color(210, 246, 250);
    private final static Color LightBlue = new Color(123, 195, 203);

    private final JDialog myDialog;
    private final JPanel myQuestionPanel;
    private final JPanel myAnswerOptionPanel;
    private final JTextArea myQuestionArea;
    private final JButton myAnswerButton1;
    private final JButton myAnswerButton2;
    private final JButton myAnswerButton3;
    private final JButton myAnswerButton4;

    private final GamePanel myGamePanel;
    private final Door myDoor;

    private AnswerData myCorrectAnswer; //


    public QuestionPanel(final Door theDoor, final GamePanel theGamePanel) {
        if (theDoor == null || theGamePanel == null) {
            throw new IllegalArgumentException("The doors and game panel cannot be null");
        }
        this.myGamePanel = theGamePanel;
        this.myDoor = theDoor;
        this.myDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(theGamePanel), false);
        this.myQuestionPanel = new JPanel();
        this.myAnswerOptionPanel = new JPanel();
        this.myQuestionArea = new JTextArea();
        this.myAnswerButton1 = new JButton();
        this.myAnswerButton2 = new JButton();
        this.myAnswerButton3 = new JButton();
        this.myAnswerButton4 = new JButton();
        loadQuestionOption(theDoor.getQuestionObject());

        popUpUI();
    }

    private void loadQuestionOption(final Question theQuestion) {
        if (theQuestion == null) {
            throw new IllegalArgumentException("Question cannot be null");
        }
        myQuestionArea.setText(theQuestion.getQuestion());
        myAnswerButton1.setText(theQuestion.getAnswerOption1());
        myAnswerButton2.setText(theQuestion.getAnswerOption2());
        myAnswerButton3.setText(theQuestion.getAnswerOption3());
        myAnswerButton4.setText(theQuestion.getAnswerOption4());
        myCorrectAnswer = theQuestion.getAnswers(); //
    }

    /**
     * the action perform method for all the answer option button.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String playerAnswers;
        if (e.getSource() == myAnswerButton1) {
            playerAnswers = myAnswerButton1.getText();
            checkAnswers(myCorrectAnswer, playerAnswers);
        } else if (e.getSource() == myAnswerButton2) {
            playerAnswers = myAnswerButton2.getText();
            checkAnswers(myCorrectAnswer, playerAnswers);
        } else if (e.getSource() == myAnswerButton3) {
            playerAnswers = myAnswerButton3.getText();
            checkAnswers(myCorrectAnswer, playerAnswers);
        } else if (e.getSource() == myAnswerButton4) {
            playerAnswers = myAnswerButton4.getText();
            checkAnswers(myCorrectAnswer, playerAnswers);
        }
    }

    public void checkAnswers(final AnswerData theCorrectAnswers, final String thePlayerAnswers) {
        if (theCorrectAnswers == null || thePlayerAnswers == null) {
            throw new IllegalArgumentException("The correct answer and the player answer cannot be null");
        }
        if (theCorrectAnswers.equals(thePlayerAnswers)) {
            myDoor.setMyLockStatus(true);
            dialogForResult("Correct");
        } else {
            myDoor.setMyAttemptStatus(true);
            myGamePanel.getMyGame().getMyPlayer().decreaseHealth();
            if (myGamePanel.getMyGame().getMyPlayer().getMyHealth() > 0) {
                dialogForResult("Incorrect Answer :(");
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
    public void popUpUI() {
        //Pop up dialog
        int radiusCorner = 40;
        int gapTop = 25;
        int gapSides = 45;
        myDialog.setTitle("Do you want to be our Maze expert? Type beat!");
        myDialog.setSize(400,300);
        myDialog.setLayout(new BorderLayout());
        myDialog.setUndecorated(true);
        myDialog.setShape(new RoundRectangle2D.Double(0,0,400,300,radiusCorner, radiusCorner));
        myQuestionPanel.setBackground(White);
        myQuestionPanel.setLayout(null);

        //Text area the question
        myQuestionPanel.setFont(new Font("Berlin Sans FB",Font.BOLD, 20));
        myQuestionPanel.setBackground(Color.BLACK);
        myQuestionArea.setBounds(gapSides, gapTop,
                myDialog.getWidth() - 2 * gapSides, 180 - gapTop);
        myQuestionArea.setLineWrap(true);
        myQuestionArea.setWrapStyleWord(true);
        myQuestionArea.setEditable(false);
        myQuestionArea.setFocusable(false);
        myQuestionPanel.add(myQuestionArea);

        //
        Font fontForEachButtons = new Font("Berlin Sans FB",Font.PLAIN, 16);
        myAnswerButton1.addActionListener(this);
        myAnswerButton1.setFont(fontForEachButtons);
        myAnswerButton1.setBackground(Blue);

        myAnswerButton2.addActionListener(this);
        myAnswerButton2.setFont(fontForEachButtons);
        myAnswerButton2.setBackground(LightBlue);

        myAnswerButton3.addActionListener(this);
        myAnswerButton3.setFont(fontForEachButtons);
        myAnswerButton3.setBackground(LightBlue);

        myAnswerButton4.addActionListener(this);
        myAnswerButton4.setFont(fontForEachButtons);
        myAnswerButton4.setBackground(Blue);

        myAnswerOptionPanel.add(myAnswerButton1);
        myAnswerOptionPanel.add(myAnswerButton2);
        myAnswerOptionPanel.add(myAnswerButton3);
        myAnswerOptionPanel.add(myAnswerButton4);
        myQuestionPanel.setLayout(new GridLayout(2,2));
        myQuestionPanel.setPreferredSize(new Dimension(400,120));

        myDialog.add(myQuestionPanel, BorderLayout.CENTER);
        myDialog.add(myAnswerOptionPanel, BorderLayout.SOUTH);

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
