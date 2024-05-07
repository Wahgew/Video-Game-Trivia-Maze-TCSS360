package View;

import Model.Door;
import Model.Question;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


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

    private String myCorrectAnswer;


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
        //loadQuestion(theDoor.getQuestionObject());
    }

    private void loadQuestion(final Question theQuestion) {
        if (theQuestion == null) {
            throw new IllegalArgumentException("Question cannot be null");
        }
        myQuestionArea.setText(theQuestion.getQuestion());
//        myAnswerButton1.setText(theQuestion.);
//        myAnswerButton2.setText(theQuestion.);
//        myAnswerButton3.setText(theQuestion.);
//        myAnswerButton4.setText(theQuestion.);
        //myCorrectAnswer = theQuestion.getAnswers();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
