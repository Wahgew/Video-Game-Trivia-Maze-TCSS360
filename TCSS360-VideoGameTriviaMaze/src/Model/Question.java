package Model;

import java.util.List;

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
     * Answer option 1
     */
    private String myAnswerOption1;
    /**
     * Answer option 2
     */
    private String myAnswerOption2;
    /**
     * Answer option 3
     */
    private String myAnswerOption3;
    /**
     * Answer option 4
     */
    private String myAnswerOption4;

    /**
     * Question constructor that creates a question with a question text,
     * AnswerData, and type.
     *
     * @param theQuestion the question text.
     * @param theAnswer the data representing the answer choices and correct index
     */
    protected Question(final String theQuestion, final AnswerData theAnswer, final String theType) {
        myQuestion = theQuestion;
        myAnswers = theAnswer;
        myQuestionType = theType;
    }

    /**
     * Constructor for initialize the fields of the question.
     * @param theQuestion
     * @param theAnswer
     * @param theOption1
     * @param theOption2
     * @param theOption3
     * @param theOption4
     */
    public Question(final String theQuestion, final AnswerData theAnswer, String myQuestionType, final String theOption1,
                    final String theOption2, final String theOption3, final String theOption4) {
        this.myQuestionType = myQuestionType;
        if (theQuestion == null || theAnswer == null || theOption1 == null || theOption2 == null) {
            throw new IllegalArgumentException("Question and Answer are required and cannot be null");
        }
        myQuestion = theQuestion;
        myAnswers = theAnswer;
        myAnswerOption1 = theOption1;
        myAnswerOption2 = theOption2;
        myAnswerOption3 = theOption3;
        myAnswerOption4 = theOption4;
    }

    boolean checkAnswer(final String userAnswer) {
        String correctAns = myAnswers.getAnswerChoices().get(myAnswers.getCorrectAnswerIndex());
        return correctAns.equals(userAnswer.toLowerCase());
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
     * Gets the correct answer to the question.
     *
     * @return the correct answer
     */
    public AnswerData getAnswers() {
        return myAnswers;
    }
    /**
     * Getter for answer option 1
     *
     * @return the answer option
     */
    public String getAnswerOption1() {
        return myAnswerOption1;
    }
    /**
     * Getter for answer option 2
     *
     * @return the answer option 2
     */
    public String getAnswerOption2() {
        return myAnswerOption2;
    }
    /**
     * Getter for answer option 3
     *
     * @return the answer option 3
     */
    public String getAnswerOption3() {
        return myAnswerOption3;
    }
    /**
     * Getter for answer option 4
     *
     * @return the answer option 4
     */
    public String getAnswerOption4() {
        return myAnswerOption4;
    }

    /**
     * toString displaying question, possible answer with
     * the correct answer marked.
     *
     * @return String displaying question object information
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Question Type: ").append(myQuestionType).append("\n");
        sb.append("Question: ").append(myQuestion).append("\n");
        sb.append("Answers:\n");

        List<String> answers = myAnswers.getAnswerChoices();
        int correctAnswerIndex = myAnswers.getCorrectAnswerIndex();

        for (int i = 0; i < answers.size(); i++) {
            sb.append("  ").append(i + 1).append(". ").append(answers.get(i));
            if (i == correctAnswerIndex) {
                sb.append(" (Correct)");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
