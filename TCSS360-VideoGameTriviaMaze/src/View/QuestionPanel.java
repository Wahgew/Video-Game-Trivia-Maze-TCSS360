package View;

import Model.*;

import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.geom.RoundRectangle2D;
import java.util.*;
import java.util.List;
import Resource.R;


public class QuestionPanel implements ActionListener {
    private final JDialog myDialog;
    private final JPanel myQuestionPanel;
    private final JPanel myAnswerOptionPanel;
    private final JTextPane myQuestionArea;
    private final JButton myAnswerButton1;
    private final JButton myAnswerButton2;
    private final JButton myAnswerButton3;
    private final JButton myAnswerButton4;
    private final JTextField myTextAnswer;
    private final GamePanel myGamePanel;
    private final Door myDoor;
    private String myCorrectAnswer; //
    private Clip[] myAudio;

    public QuestionPanel(final Door theDoor,final GamePanel theGamePanel) {
        if (theDoor == null || theGamePanel == null) {
            throw new IllegalArgumentException("The doors and game panel cannot be null");
        }
        this.myGamePanel = theGamePanel;
        this.myDoor = theDoor;
        this.myDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(theGamePanel), false);
        this.myQuestionPanel = new JPanel();
        this.myAnswerOptionPanel = new JPanel();
        this.myQuestionArea = new JTextPane();
        this.myAnswerButton1 = new JButton();
        this.myAnswerButton2 = new JButton();
        this.myAnswerButton3 = new JButton();
        this.myAnswerButton4 = new JButton();
        this.myTextAnswer = new JTextField();
        loadQuestionOption(theDoor.askQuestion()); //TODO: Load question should only happen when player interacts with a door.

        switch (myDoor.getQuestionObject().getType()) {
            case "Short" -> popUpForShort();
            case "Image" -> {
                popUpUI();
                myDialog.add(loadImage(), BorderLayout.NORTH);
            }
            case "Audio" -> {
                popUpUI();
                myDialog.add(loadAudio(), BorderLayout.NORTH);
            }
            default -> popUpUI();
        }
    }

    private JPanel loadImage() {
        assert myDoor.getQuestionObject() instanceof ImageQuestion;
        ImageQuestion imageQuestion = (ImageQuestion) myDoor.getQuestionObject();

        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setBackground(R.Colors.QUESTION_PANEL_BG);
        ImageIcon imageIcon = new ImageIcon("src/" + imageQuestion.getImagePath());
        Image originalImage = imageIcon.getImage();

        // Get the original dimensions of the image
        int originalWidth = originalImage.getWidth(null);
        int originalHeight = originalImage.getHeight(null);

        // Calculate the preferred size while maintaining aspect ratio
        double aspectRatio = (double) originalWidth / originalHeight;
        int maxWidth = 400; // Set a maximum width for the image
        int maxHeight = 400; // Set a maximum height for the image

        int preferredWidth = maxWidth;
        int preferredHeight = (int) (preferredWidth / aspectRatio);

        // If the preferred height exceeds the maximum height, calculate width based on maximum height
        if (preferredHeight > maxHeight) {
            preferredHeight = maxHeight;
            preferredWidth = (int) (preferredHeight * aspectRatio);
        }

        // Resize the original image to the preferred size
        Image resizedImage = originalImage.getScaledInstance(preferredWidth, preferredHeight, Image.SCALE_SMOOTH);
        ImageIcon resizedImageIcon = new ImageIcon(resizedImage);
        JLabel imageLabel = new JLabel(resizedImageIcon);

        imagePanel.add(imageLabel, BorderLayout.CENTER);
        imagePanel.setPreferredSize(new Dimension(preferredWidth, preferredHeight));
        imagePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding around the image
        return imagePanel;
    }

    private JPanel loadAudio() {
        assert myDoor.getQuestionObject() instanceof AuditoryQuestion;
        AuditoryQuestion audioQuestion = (AuditoryQuestion) myDoor.getQuestionObject();

        JPanel audioPanel = new JPanel(new BorderLayout());
        JButton playButton = new JButton("Play Audio");
        audioPanel.setBackground(R.Colors.QUESTION_PANEL_BG);
        audioPanel.setBackground(Color.RED);
        myAudio = new Clip[]{null};

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (myAudio[0] != null && myAudio[0].isRunning()) {
                    myAudio[0].stop();
                }
                myAudio[0] = audioQuestion.playMusic();
                myAudio[0].start();
            }
        });

        playButton.setPreferredSize(new Dimension(150, 150));

        // Make the button circular
        playButton.setBorder(BorderFactory.createEmptyBorder());
        playButton.setContentAreaFilled(false);
        playButton.setFocusPainted(false);
        playButton.setOpaque(true);
        playButton.setBackground(Color.WHITE);
        playButton.setForeground(Color.BLACK);

        // Add a border around the circular button
        playButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        audioPanel.add(playButton, BorderLayout.CENTER);
        audioPanel.setPreferredSize(new Dimension(700, 100));
        return audioPanel;
    }

    private void loadQuestionOption(final Question theQuestion) {
        if (theQuestion == null) {
            throw new IllegalArgumentException("Question cannot be null");
        }

        myCorrectAnswer = theQuestion.getCorrectAnswer();
        myQuestionArea.setText(theQuestion.getQuestion());
        TreeMap<String, Boolean> answerChoices = theQuestion.getAnswers().getAnswerChoices();

        String questionType = theQuestion.getType();
        switch (questionType) {
            case "Multi":
            case "T/F":
            case "Image":
            case "Audio":
                setupAnswerButtons(answerChoices);
                break;
            case "Short":
                setupShortAnswerField();
                break;
            default:
                throw new IllegalArgumentException("Invalid question type");
        }
    }

    private void setupAnswerButtons(TreeMap<String, Boolean> theAnswerChoices) {
        List<JButton> answerButtons = Arrays.asList(myAnswerButton1, myAnswerButton2, myAnswerButton3, myAnswerButton4);

        // Reset button states
        for (JButton button : answerButtons) {
            button.setEnabled(false);
            button.setVisible(false);
            button.removeActionListener(this);
        }

        if (theAnswerChoices.size() == 2) {
            // True/False question
            Iterator<Map.Entry<String, Boolean>> iterator = theAnswerChoices.entrySet().iterator();
            Map.Entry<String, Boolean> trueEntry = iterator.next();
            Map.Entry<String, Boolean> falseEntry = iterator.next();

            myAnswerButton1.setText(trueEntry.getKey());
            myAnswerButton1.setEnabled(true);
            myAnswerButton1.setVisible(true);
            myAnswerButton1.addActionListener(this);

            myAnswerButton2.setText(falseEntry.getKey());
            myAnswerButton2.setEnabled(true);
            myAnswerButton2.setVisible(true);
            myAnswerButton2.addActionListener(this);
        } else {
            // Other question types (Multi, Image, Audio)
            int index = 0;
            for (Map.Entry<String, Boolean> entry : theAnswerChoices.entrySet()) {
                if (index < answerButtons.size()) {
                    JButton button = answerButtons.get(index);
                    button.setText(entry.getKey());
                    button.setEnabled(true);
                    button.setVisible(true);
                    button.addActionListener(this);
                    index++;
                } else {
                    break;
                }
            }
        }
    }

    private void setupShortAnswerField() {
        // Disable and hide the answer buttons
        myAnswerButton1.setEnabled(false);
        myAnswerButton1.setVisible(false);
        myAnswerButton2.setEnabled(false);
        myAnswerButton2.setVisible(false);
        myAnswerButton3.setEnabled(false);
        myAnswerButton3.setVisible(false);
        myAnswerButton4.setEnabled(false);
        myAnswerButton4.setVisible(false);

        // Enable and show the text field
        myTextAnswer.setEnabled(true);
        myTextAnswer.setVisible(true);
        myTextAnswer.setText(""); // Clear the text field
        myTextAnswer.addActionListener(this);
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

        if (theEvent.getSource() == myTextAnswer) {
            // Handle user's input for short answer question
            String userAnswer = myTextAnswer.getText().toLowerCase(); // sanitize user input to lower
            checkAnswers(userAnswer);
        } else {
            if (myDoor.getQuestionObject().getType().equals("Audio")) {
                myAudio[0].stop();
            }
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
        //myAudio[0].stop();
    }

    public void checkAnswers(final String thePlayerAnswer) {
        if (thePlayerAnswer == null) {
            throw new IllegalArgumentException("The player's answer cannot be null");
        }

        if (thePlayerAnswer.equals(myCorrectAnswer)) {
            Player.getInstance().QuestionsAnswered(myDoor.getQuestionObject().getID(), true);
            myDoor.questionAttempted(true, Player.getInstance().getMyLocationRow(), Player.getInstance().getMyLocationCol(), Player.getInstance().getMyDirection());
            Player.getInstance().movePlayer(Player.getInstance().getMyDirection());
            dialogForResult("Correct");
        } else {
            Player.getInstance().QuestionsAnswered(myDoor.getQuestionObject().getID(), false);
            myDoor.questionAttempted(false, Player.getInstance().getMyLocationRow(), Player.getInstance().getMyLocationCol(), Player.getInstance().getMyDirection());
            Player.getInstance().decreaseHealth();
            if (Player.getInstance().getMyHealth() > 0) { //myGamePanel.getMyGame().getMyPlayer().getMyHealth()
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
     * Sets up GUI display for questions when the player interacts with a door.
     */
    private void popUpUI() {
        // JDialog for the pop up.
        myDialog.setTitle("Gamers Rise");

        if (myDoor.getQuestionObject() instanceof ImageQuestion) {
            myDialog.setSize(new Dimension(700, 600));
        } else if (myDoor.getQuestionObject() instanceof AuditoryQuestion) {
            myDialog.setSize(new Dimension(700, 400));
        } else {
            myDialog.setSize(new Dimension(700, 300));
        }

        myDialog.setLayout(new BorderLayout());
        myDialog.setUndecorated(true);
        myDialog.setLocationRelativeTo(myGamePanel);


        // Set black border around the dialog
        myDialog.getRootPane().setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));

        int cornerRadius = Math.min(myDialog.getWidth(), myDialog.getHeight()) / 5;
        myDialog.setShape(new RoundRectangle2D.Double(0, 0, myDialog.getWidth(), myDialog.getHeight(), cornerRadius, cornerRadius));

        // Panel for the question
        myQuestionPanel.setBackground(R.Colors.QUESTION_PANEL_BG);
        myQuestionPanel.setLayout(null);

        // Text Area to contain the question
        myQuestionArea.setFont(new Font("Lato", Font.BOLD, 20));
        myQuestionArea.setForeground(new Color(245, 71, 3));
        myQuestionArea.setBackground(R.Colors.QUESTION_PANEL_BG);
        int gapSides = 20;
        int gapTop = 25;
        myQuestionArea.setBounds(gapSides, gapTop,
                myDialog.getWidth() - 2 * gapSides, 100 - gapTop);
        myQuestionArea.setEditable(false);
        myQuestionArea.setFocusable(false);
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        myQuestionArea.setParagraphAttributes(center, false);

        myQuestionPanel.add(myQuestionArea);

        // Panel for the answer options
        myAnswerOptionPanel.setLayout(new GridLayout(2, 2));
        myAnswerOptionPanel.setPreferredSize(new Dimension(400, 120));
        myAnswerOptionPanel.setBackground(R.Colors.QUESTION_PANEL_BG);

        // Set up round buttons with black borders
        myAnswerButton1.setFont(new Font("Lato", Font.PLAIN, 16));
        myAnswerButton1.setForeground(R.Colors.TEXT_LABEL);
        myAnswerButton1.setBorder(BorderFactory.createLineBorder(R.Colors.OUTLINES, 3));
        myAnswerButton1.setOpaque(true);
        myAnswerButton1.setFocusPainted(false);
        myAnswerButton1.setContentAreaFilled(false);

        myAnswerButton2.setFont(new Font("Lato", Font.PLAIN, 16));
        myAnswerButton2.setForeground(R.Colors.TEXT_LABEL);
        myAnswerButton2.setBorder(BorderFactory.createLineBorder(R.Colors.OUTLINES, 3));
        myAnswerButton2.setOpaque(true);
        myAnswerButton2.setFocusPainted(false);
        myAnswerButton2.setContentAreaFilled(false);

        myAnswerButton3.setFont(new Font("Lato", Font.PLAIN, 16));
        myAnswerButton3.setForeground(R.Colors.TEXT_LABEL);
        myAnswerButton3.setBorder(BorderFactory.createLineBorder(R.Colors.OUTLINES, 3));
        myAnswerButton3.setOpaque(true);
        myAnswerButton3.setFocusPainted(false);
        myAnswerButton3.setContentAreaFilled(false);

        myAnswerButton4.setFont(new Font("Lato", Font.PLAIN, 16));
        myAnswerButton4.setForeground(R.Colors.TEXT_LABEL);
        myAnswerButton4.setBackground(Color.DARK_GRAY);
        myAnswerButton4.setBorder(BorderFactory.createLineBorder(R.Colors.OUTLINES, 3));
        myAnswerButton4.setOpaque(true);
        myAnswerButton4.setFocusPainted(false);
        myAnswerButton4.setContentAreaFilled(false);

        myAnswerOptionPanel.add(myAnswerButton1);
        myAnswerOptionPanel.add(myAnswerButton2);
        myAnswerOptionPanel.add(myAnswerButton3);
        myAnswerOptionPanel.add(myAnswerButton4);

        // Add the question panel and answer option panel to the dialog
        myDialog.add(myQuestionPanel, BorderLayout.CENTER);
        myDialog.add(myAnswerOptionPanel, BorderLayout.SOUTH);

        myDialog.setVisible(true);
    }

    private void popUpForShort() {
        // JDialog for the pop up.
        myDialog.setTitle("Gamers Rise");
        myDialog.setSize(700, 300);
        myDialog.setLayout(new BorderLayout());
        myDialog.setUndecorated(true);
        myDialog.setLocationRelativeTo(myGamePanel);

        // Set black border around the dialog
        myDialog.getRootPane().setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));

        int cornerRadius = Math.min(myDialog.getWidth(), myDialog.getHeight()) / 5;
        myDialog.setShape(new RoundRectangle2D.Double(0, 0, myDialog.getWidth(), myDialog.getHeight(), cornerRadius, cornerRadius));

        // Panel for the question
        myQuestionPanel.setBackground(R.Colors.QUESTION_PANEL_BG);
        myQuestionPanel.setLayout(null);

        // Text Area to contain the question
        myQuestionArea.setFont(new Font("Lato", Font.BOLD, 20));
        myQuestionArea.setForeground(new Color(245, 71, 3));
        myQuestionArea.setBackground(R.Colors.QUESTION_PANEL_BG);
        int gapSides = 20;
        int gapTop = 25;
        myQuestionArea.setBounds(gapSides, gapTop,
                myDialog.getWidth() - 2 * gapSides, 100 - gapTop);
        myQuestionArea.setEditable(false);
        myQuestionArea.setFocusable(false);
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        myQuestionArea.setParagraphAttributes(center, false);

        myQuestionPanel.add(myQuestionArea);

        // Panel for the answer options
        myAnswerOptionPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Use FlowLayout for JTextField
        myAnswerOptionPanel.setPreferredSize(new Dimension(400, 120));
        myAnswerOptionPanel.setBackground(R.Colors.QUESTION_PANEL_BG);

        // JTextField for short answer
        myTextAnswer.setPreferredSize(new Dimension(300, 30)); // Set preferred size for JTextField
        myTextAnswer.setFont(new Font("Lato", Font.PLAIN, 16));
        myTextAnswer.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
        myTextAnswer.setForeground(R.Colors.TEXT_LABEL);
        myTextAnswer.setBackground(R.Colors.QUESTION_PANEL_BG);
        addPlaceholder(myTextAnswer,"Type Here");

        myAnswerOptionPanel.add(myTextAnswer);

        // Add the question panel and answer option panel to the dialog
        myDialog.add(myQuestionPanel, BorderLayout.CENTER);
        myDialog.add(myAnswerOptionPanel, BorderLayout.SOUTH);

        myDialog.setVisible(true);
        myAnswerOptionPanel.requestFocus();
    }

    private void addPlaceholder(JTextField textField, String thePlaceholder) {
        textField.setForeground(R.Colors.TEXT_LABEL);
        textField.setText(thePlaceholder);
        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(thePlaceholder)) {
                    textField.setText("");
                    textField.setForeground(R.Colors.TEXT_LABEL);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setForeground(R.Colors.TEXT_LABEL);
                    textField.setText(thePlaceholder);
                }
            }
        });
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
                Player.getInstance().scoreUpdate(true);
                setBackground(LIGHT_GRAY);
                resultLabel1 = new JLabel("THE ANSWER IS CORRECT!");
                resultLabel1.setForeground(DARK_GREEN);
                resultLabel2 = new JLabel("The doors is unlocked! Please Enter.");
                resultLabel2.setForeground(GREEN);
                continueButton.setBackground(SUPER_LIGHT_GREEN);
                continueButton.setForeground(DARK_GREEN);
                continueButton.setBorder(BorderFactory.createLineBorder(GREEN,1));
            } else if (thePanel.equalsIgnoreCase("Incorrect")) {
                Player.getInstance().scoreUpdate(false);
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
