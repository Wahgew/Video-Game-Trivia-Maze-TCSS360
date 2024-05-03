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
     * Question constructor that creates a question with a question text,
     * AnswerData, and type.
     *
     * @param theQuestion the question text.
     * @param theAnswer the data representing the answer choices and correct index
     */
    protected Question(String theQuestion, AnswerData theAnswer, String theType) {
        myQuestion = theQuestion;
        myAnswers = theAnswer;
        myQuestionType = theType;
    }
    boolean checkAnswer(String userAnswer) {
        return myAnswers.equals(userAnswer); // TODO: WRITE A COMPARETO METHOD FOR ANSWERDATA
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
