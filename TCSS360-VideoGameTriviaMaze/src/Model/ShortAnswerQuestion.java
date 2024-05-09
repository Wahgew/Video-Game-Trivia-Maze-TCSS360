package Model;

/**
 * ShortAnswerQuestion represents a question where the user provides a short text answer.
 * Retrieves the question text, answer data, question type, and correct answer.
 *
 * @author Ken Egawa
 * @author Peter Madin
 * @author Sopheanith Ny
 * @version 0.0.1 April 28, 2024
 */
public class ShortAnswerQuestion extends Question {

    /**
     * The correct answer to the short answer question.
     */
    private final String myCorrectAnswer;

    /**
     * Constructs a ShortAnswerQuestion object with the specified question text,
     * answer data, and question type.
     *
     * @param theQuestion    the text of the question
     * @param theAnswerData  the data representing the answer choices and correct index
     * @param theType        the type of the question
     */
    public ShortAnswerQuestion(String theQuestion, AnswerData theAnswerData, String theType) {
        super(theQuestion, theAnswerData, theType);
        myCorrectAnswer = theAnswerData.getRightAnswer();
    }

    /**
     * Gets the type of the question.
     *
     * @return the type of the question
     */
    @Override
    public String getType() {
        return super.getType();
    }

    /**
     * Gets the text of the question.
     *
     * @return the text of the question
     */
    @Override
    public String getQuestion() {
        return super.getQuestion();
    }

    /**
     * Gets the correct answer to the short answer question.
     *
     * @return the correct answer
     */
    public String getCorrectAnswer() {
        return myCorrectAnswer;
    }
}
