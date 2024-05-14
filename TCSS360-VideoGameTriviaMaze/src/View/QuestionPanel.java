package View;

import Model.*;

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
    private final static Color White = new Color(255,255, 255);
    private final static Color Blue = new Color(210, 246, 250);
    private final static Color LightBlue = new Color(123, 195, 203);

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


    public QuestionPanel(final Door theDoor, final GamePanel theGamePanel) {
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

        if (myDoor.getQuestionObject().getType().equals("Short")) {
            popUpForShort();
        } else if (myDoor.getQuestionObject().getType().equals("Image")) {
            popUpUI();
            myDialog.add(loadImage(), BorderLayout.NORTH);
        } else {
            popUpUI();
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


//    private void loadQuestionOption(final Question theQuestion) {
//        if (theQuestion == null) {
//            throw new IllegalArgumentException("Question cannot be null");
//        }
//
//        myQuestionArea.setText(theQuestion.getQuestion());
//
//        TreeMap<String, Boolean> answerChoices = theQuestion.getAnswers().getAnswerChoices();
//
//        String questionType = theQuestion.getType();
//        switch(questionType) {
//            case "Multi":
//                setButton(true);
//                int optionCount = 1;
//                for (Map.Entry<String, Boolean> entry : answerChoices.entrySet()) {
//                    switch (optionCount) {
//                        case 1:
//                            myAnswerButton1.setText(entry.getKey());
//                            break;
//                        case 2:
//                            myAnswerButton2.setText(entry.getKey());
//                            break;
//                        case 3:
//                            myAnswerButton3.setText(entry.getKey());
//                            break;
//                        case 4:
//                            myAnswerButton4.setText(entry.getKey());
//                        default:
//                    }
//                    optionCount++;
//                    if (optionCount > 4) {
//                        break;
//                    }
//                }
//            case "T/F":
//                myAnswerButton3.setEnabled(false);
//                myAnswerButton4.setEnabled(false);
//                myAnswerButton3.setVisible(false);
//                myAnswerButton4.setVisible(false);
//            case "Short":
//                setButton(false);
//            case "Audio":
//                setButton(true);
//                AuditoryQuestion audioQuestion = (AuditoryQuestion) theQuestion;
//                Clip audio = audioQuestion.playMusic();
//                audio.start();
//                JOptionPane.showMessageDialog(null, audioQuestion.getQuestion() + "\nPress OK to stop playing."); // temporary testing
//                audio.stop();
//
//                int optionCount3 = 1;
//                for (Map.Entry<String, Boolean> entry : answerChoices.entrySet()) {
//                    switch (optionCount3) {
//                        case 1:
//                            myAnswerButton1.setText(entry.getKey());
//                            break;
//                        case 2:
//                            myAnswerButton2.setText(entry.getKey());
//                            break;
//                        case 3:
//                            myAnswerButton3.setText(entry.getKey());
//                            break;
//                        case 4:
//                            myAnswerButton4.setText(entry.getKey());
//                        default:
//                    }
//                    optionCount3++;
//                    if (optionCount3 > 4) {
//                        break;
//                    }
//                }
//            case "Image":
//                setButton(true);
//                assert theQuestion instanceof ImageQuestion;
//                ImageQuestion imgQ = (ImageQuestion) theQuestion;
//                try {
//                    BufferedImage buffImage = ImageIO.read(new File("src/" + imgQ.getImagePath()));
//                    JOptionPane.showMessageDialog(null, new JLabel(new ImageIcon(buffImage)),
//                            imgQ.getQuestion(), JOptionPane.PLAIN_MESSAGE);
//                    // Cannot invoke "java.awt.Image.getProperty(String, java.awt.image.ImageObserver)" because "image" is null <-- FILE FORMAT OR COLOR PROFILE ERROR
//                    // NEED TO RE-EXPORT IN PHOTOSHOP OR GIMP AS sRGB, ASK KEN IF YOU NEED HELP
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//                int optionCount2 = 1;
//                for (Map.Entry<String, Boolean> entry : answerChoices.entrySet()) {
//                    switch (optionCount2) {
//                        case 1:
//                            myAnswerButton1.setText(entry.getKey());
//                            break;
//                        case 2:
//                            myAnswerButton2.setText(entry.getKey());
//                            break;
//                        case 3:
//                            myAnswerButton3.setText(entry.getKey());
//                            break;
//                        case 4:
//                            myAnswerButton4.setText(entry.getKey());
//                        default:
//                    }
//                    optionCount2++;
//                    if (optionCount2 > 4) {
//                        break;
//                    }
//                }
//        }
//        myCorrectAnswer = theQuestion.getCorrectAnswer();
//    }

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

        // Additional setup or handling for special cases
        switch (questionType) {
            case "Image":
                // Display the image
                // ...
                break;
            case "Audio":
                // Set up audio playback
                // ...
                break;
            // Add more cases if needed
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





//    private void setButton(boolean theState) {
//        myAnswerButton1.setEnabled(theState);
//        myAnswerButton2.setEnabled(theState);
//        myAnswerButton3.setEnabled(theState);
//        myAnswerButton4.setEnabled(theState);
//        myAnswerButton1.setVisible(theState);
//        myAnswerButton2.setVisible(theState);
//        myAnswerButton3.setVisible(theState);
//        myAnswerButton4.setVisible(theState);
//    }

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
            String userAnswer = myTextAnswer.getText();
            checkAnswers(userAnswer);
        } else {
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
    }
//    @Override
//    public void actionPerformed(ActionEvent event) {
//        Object source = event.getSource();
//
//        if (source == myTextAnswer) {
//            // Handle user's input for short answer question
//            String userAnswer = myTextAnswer.getText();
//            checkAnswers(userAnswer);
//        } else {
//            // Handle button clicks for other question types
//            JButton clickedButton = (JButton) source;
//            String userAnswer = clickedButton.getText();
//            checkAnswers(userAnswer);
//        }
//    }

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
//    public void popUpUI() {
//        // Pop-up dialog
//        int radiusCorner = 40;
//        int gapTop = 25;
//        int gapSides = 45;
//        myDialog.setTitle("Do you want to be our Maze expert? Type beat!");
//        myDialog.setPreferredSize(new Dimension(700, 200)); // Increased width for better button layout
//        myDialog.setLayout(new BorderLayout());
//        myDialog.setUndecorated(true);
//        myDialog.setShape(new RoundRectangle2D.Double(0, 0, 700, 200, radiusCorner, radiusCorner));
//        myQuestionPanel.setBackground(new Color(51, 51, 51)); // Dark gray background
//        myQuestionPanel.setLayout(new BorderLayout());
//        myAnswerOptionPanel.setPreferredSize(new Dimension(400, 120));
//
//        // Pack the dialog to the preferred size and center it relative to the game panel
//        myDialog.pack();
//        myDialog.setLocationRelativeTo(myGamePanel);
//
//        // Text area for the question
//        myQuestionArea.setFont(new Font("Arial", Font.PLAIN, 16));
//        myQuestionArea.setForeground(Color.WHITE); // White text color
//        myQuestionArea.setBackground(new Color(51, 51, 51)); // Dark gray background
//        myQuestionArea.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the question text
//        //myQuestionArea.setLineWrap(true);
//        //myQuestionArea.setWrapStyleWord(true);
//        //myQuestionArea.setEditable(false);
//        myQuestionArea.setFocusable(false);
//        //myQuestionArea.setBounds(gapSides, gapTop, myDialog.getWidth() - 2 * gapSides, 120); // Adjusted height for better layout
//        //myQuestionArea.setBounds(gapSides, gapTop,
//                //myDialog.getWidth() - 2 * gapSides, 180 - gapTop);
//        myQuestionPanel.add(myQuestionArea, BorderLayout.NORTH);
//        //myQuestionPanel.add(myQuestionArea);
//        //myQuestionPanel.add(myQuestionArea, BorderLayout.CENTER); // Add to the center of the panel
//
//        // Set up the answer option panel
////        myAnswerOptionPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Use FlowLayout with horizontal and vertical gaps
////        myAnswerOptionPanel.setBackground(new Color(51, 51, 51)); // Dark gray background
//
//        myAnswerOptionPanel.setLayout(new GridLayout(2, 2)); // 2x2 grid layout with gaps
//        myAnswerOptionPanel.setBackground(new Color(51, 51, 51)); // Dark gray background
//
//        // Add the answer buttons to the answer option panel
//        myAnswerButton1.setBackground(new Color(102, 102, 102)); // Grayish button color
//        myAnswerButton1.setForeground(Color.WHITE); // White text color
//        myAnswerButton1.setFont(new Font("Arial", Font.PLAIN, 20)); // Adjust the font size as needed
//        myAnswerButton1.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true)); // Rounded button border
//
//        myAnswerButton2.setBackground(new Color(102, 102, 102));
//        myAnswerButton2.setForeground(Color.WHITE);
//        myAnswerButton2.setFont(new Font("Arial", Font.PLAIN, 20));
//        myAnswerButton2.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
//
//        myAnswerButton3.setBackground(new Color(102, 102, 102));
//        myAnswerButton3.setForeground(Color.WHITE);
//        myAnswerButton3.setFont(new Font("Arial", Font.PLAIN, 20));
//        myAnswerButton3.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
//
//        myAnswerButton4.setBackground(new Color(102, 102, 102));
//        myAnswerButton4.setForeground(Color.WHITE);
//        myAnswerButton4.setFont(new Font("Arial", Font.PLAIN, 20));
//        myAnswerButton4.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
//
//        myAnswerOptionPanel.add(myAnswerButton1);
//        myAnswerOptionPanel.add(myAnswerButton2);
//        myAnswerOptionPanel.add(myAnswerButton3);
//        myAnswerOptionPanel.add(myAnswerButton4);
//        myAnswerOptionPanel.add(myTextAnswer);
//
//        // Add the question panel and answer option panel to the dialog
//        myDialog.add(myQuestionPanel, BorderLayout.NORTH);
//        myDialog.add(myAnswerOptionPanel, BorderLayout.CENTER);
//
//        myDialog.setVisible(true);
//    }

//    private void popUpUI () {
//        // JDialog for the pop up.
//        myDialog.setTitle("Who want to be Disney Expert");
//        myDialog.setSize(700, 300);
//        myDialog.setLayout(new BorderLayout());
//        myDialog.setUndecorated(true);
//        myDialog.setLocationRelativeTo(myGamePanel);
//        int cornerRadius = 50; // Adjust the value as per your preference
//        myDialog.setShape(new RoundRectangle2D.Double(0, 0, 700, 300, cornerRadius, cornerRadius));
//        myQuestionPanel.setBackground(Blue);
//        myQuestionPanel.setLayout(null);
//        // Text Area to contain the question.
//        myQuestionArea.setFont(new Font("Berlin Sans FB", Font.PLAIN, 20));
//        myQuestionArea.setBackground(Color.GREEN);
//        int GaponBothSides = 45;
//        int GaponTop = 25;
//        myQuestionArea.setBounds(GaponBothSides, GaponTop,
//                myDialog.getWidth() - 2 * GaponBothSides, 180 - GaponTop);
////        myQuestionArea.setLineWrap(true);
////        myQuestionArea.setWrapStyleWord(true);
//        myQuestionArea.setEditable(false);
//        myQuestionArea.setFocusable(false);
//        SimpleAttributeSet center = new SimpleAttributeSet();
//        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
//        myQuestionArea.setParagraphAttributes(center, false);
//
//
//        myQuestionPanel.add(myQuestionArea);
//        //------------------------------------------------------------------------------------------------------------
//        Font fontForButtons = new Font("Berlin Sans FB", Font.PLAIN, 16);
//        // All the Radio button to select the answer from.
//        myAnswerButton1.setFont(fontForButtons);
//        myAnswerButton1.setBackground(Color.yellow);
//        myAnswerButton2.setFont(fontForButtons);
//        myAnswerButton2.setBackground(Color.WHITE);
//        myAnswerButton3.setFont(fontForButtons);
//        myAnswerButton3.setBackground(Color.WHITE);
//        myAnswerButton4.setFont(fontForButtons);
//        myAnswerButton4.setBackground(Color.white);
//        myAnswerOptionPanel.add(myAnswerButton1);
//        myAnswerOptionPanel.add(myAnswerButton2);
//        myAnswerOptionPanel.add(myAnswerButton3);
//        myAnswerOptionPanel.add(myAnswerButton4);
//        myAnswerOptionPanel.setLayout(new GridLayout(2, 2));
//        myAnswerOptionPanel.setPreferredSize(new Dimension(400, 120));
//        //myOptionPanel.setOpaque(true);
//        // Add the question panel and option panel to the dialog
//        myDialog.add(myQuestionPanel, BorderLayout.CENTER);
//        myDialog.add(myAnswerOptionPanel, BorderLayout.SOUTH);
//        // Set the dialog to be visible
//        myDialog.setVisible(true);
//    }

    private void popUpUI() {
        // JDialog for the pop up.
        myDialog.setTitle("Gamers Rise");

        if (myDoor.getQuestionObject() instanceof ImageQuestion) {
            myDialog.setSize(new Dimension(700, 600));
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
//        JPanel image = new JPanel();
//        myDialog.add(image,BorderLayout.NORTH);
        myDialog.add(myQuestionPanel, BorderLayout.CENTER);
        myDialog.add(myAnswerOptionPanel, BorderLayout.SOUTH);

        // Set the dialog to be visible
        //myDialog.pack();
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

        // Set the dialog to be visible
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
