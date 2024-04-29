package Model;

import java.util.List;

/**
 * Abstract Question class represents a generic question with a question text and answer.
 *
 * Each question has a question text and an answer. This class serves as a base class
 * for more specific types of questions, such as multiple-choice or short answer questions.
 *
 * @author Ken Egawa
 * @author Peter Madin
 * @author Sopheanith Ny
 * @version 0.0.2 April 27, 2024
 */
public abstract class Question {
    //TODO: Consider if this class should be reformed into a interface or an abstract class
    // Since we have 5 different question types Multiple choice, short answer, true/false, image, and audio
    // it maybe beneficial to give some abstraction.

    /**
     * The question text.
     */
    private final String myQuestion;

    /**
     * The correct answer to the question.
     */
    private final AnswerData myAnswers;


    /**
     * TODO: CONSTRUCTOR WILL NEED TO PULL FROM A SQLITE DATABASE FOR Q&A.
     * @param theQuestion
     * @param theAnswer
     */
    protected Question(String theQuestion, AnswerData theAnswer) {
        myQuestion = theQuestion;
        myAnswers = theAnswer;
    }

//    /**
//     * Constructs a new Question object with predefined values for testing purposes.
//     * This constructor provides predefined values for the question and answer.
//     */
//    Question() { // TEST CONSTRUCTOR
//        myQuestion = "What is Andrew Hwang's nickname?";
//        myAnswer = "Andy";
//    }


    public abstract String getType();

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
     * Checks if the specified user answer matches the correct answer.
     *
     * @param userAnswer the user's answer to check
     * @return true if the user's answer is correct, false otherwise
     */
    boolean checkAnswer(String userAnswer) {
        return myAnswers.equals(userAnswer);
    }

    @Override
    public String toString() {
        return myQuestion + "\n" + "Input Answer: ";
    }
}
