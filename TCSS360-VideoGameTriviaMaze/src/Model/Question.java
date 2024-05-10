package Model;

import java.util.List;
import java.util.Map;

/**
 * Abstract Question class represents a generic question with a question text and answer.
 * Each question has a question text and an answer. This class serves as a base class
 * for more specific types of questions, such as multiple-choice or short answer questions.
 *
 * @author Ken Egawa
 * @author Peter Madin
 * @author Sopheanith Ny
 * @version 0.0.2 April 27, 2024
 */
public abstract class Question {

    /**
     * The question text.
     */
    private final String myQuestion;

    /**
     * The correct answer to the question.
     */
    private final AnswerData myAnswers;

    /**
     * The question Type (e.g., "Multi", "T/F", "Short", "Audio", "Image").
     */
    private final String myQuestionType;

    /**
     * the question ID.
     */
    private final int myID;
//    /**
//     * Answer option 1
//     */
//    private String myAnswerOption1;
//    /**
//     * Answer option 2
//     */
//    private String myAnswerOption2;
//    /**
//     * Answer option 3
//     */
//    private String myAnswerOption3;
//    /**
//     * Answer option 4
//     */
//    private String myAnswerOption4;

    /**
     * Question constructor that creates a question with a question text,
     * AnswerData, and type.
     *
     * @param theQuestion the question text.
     * @param theAnswer the data representing the answer choices and correct index
     */
    protected Question(final String theQuestion, final AnswerData theAnswer, final String theType, final int theID) {
        myQuestion = theQuestion;
        myAnswers = theAnswer;
        myQuestionType = theType;
        myID = theID;
    }

//    /**
//     * Constructor for initialize the fields of the question.
//     * @param theQuestion
//     * @param theAnswer
//     * @param theOption1
//     * @param theOption2
//     * @param theOption3
//     * @param theOption4
//     */
//    public Question(final String theQuestion, final AnswerData theAnswer, String myQuestionType, final String theOption1,
//                    final String theOption2, final String theOption3, final String theOption4) {
//        this.myQuestionType = myQuestionType;
//        if (theQuestion == null || theAnswer == null || theOption1 == null || theOption2 == null) {
//            throw new IllegalArgumentException("Question and Answer are required and cannot be null");
//        }
//        myQuestion = theQuestion;
//        myAnswers = theAnswer;
//        myAnswerOption1 = theOption1;
//        myAnswerOption2 = theOption2;
//        myAnswerOption3 = theOption3;
//        myAnswerOption4 = theOption4;
//    }

    //TODO: For now this just checks the answer of user input
    // we will need to make check for the four button option the user clicks on.
    boolean checkAnswer(final String userAnswer) {
        return  myAnswers.getRightAnswer().equals(userAnswer.toLowerCase());
    }

    /**
     * Gets the question type.
     *
     * @return the question type
     */
    public String getType() {
        return myQuestionType;
    }

    /**
     * Gets the question text.
     *
     * @return the question text
     */
    public String getQuestion() {
        return myQuestion;
    }

    /**
     * Gets the hash tree of answers to the question.
     *
     * @return hash tree of possible answers
     */
    public AnswerData getAnswers() {
        return myAnswers;
    }

    /**
     * Gets the correct answer to the question.
     *
     * @return the correct answer
     */
    public String getCorrectAnswer() {
        return myAnswers.getRightAnswer();
    }

    public int getID() {
        return myID;
    }

    //    /**
//     * Getter for answer option 1
//     *
//     * @return the answer option
//     */
//    public String getAnswerOption1() {
//        return myAnswerOption1;
//    }
//    /**
//     * Getter for answer option 2
//     *
//     * @return the answer option 2
//     */
//    public String getAnswerOption2() {
//        return myAnswerOption2;
//    }
//    /**
//     * Getter for answer option 3
//     *
//     * @return the answer option 3
//     */
//    public String getAnswerOption3() {
//        return myAnswerOption3;
//    }
//    /**
//     * Getter for answer option 4
//     *
//     * @return the answer option 4
//     */
//    public String getAnswerOption4() {
//        return myAnswerOption4;
//    }

    /**
     * toString displaying question, possible answer with
     * the correct answer marked.
     *
     * @return String displaying question object information
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Question ID: ").append(myID).append("\n");
        sb.append("Question Type: ").append(myQuestionType).append("\n");
        sb.append("Question: ").append(myQuestion).append("\n");
        sb.append("Answers:\n");

        Map<String, Boolean> answerMap = myAnswers.getAnswerChoices();
        int index = 1;

        for (Map.Entry<String, Boolean> entry : answerMap.entrySet()) {
            String answer = entry.getKey();
            boolean isCorrect = entry.getValue();
            sb.append(" ").append(index).append(". ").append(answer);

            if (isCorrect) {
                sb.append(" (Correct)");
            }
            sb.append("\n");
            index++;
        }

        return sb.toString();
    }
}
