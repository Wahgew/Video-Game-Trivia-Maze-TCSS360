package Model;

/**
 * TrueFalseQuestion represents a true/false question.
 * It is a specialized type of MultipleChoiceQuestion with only two possible answers: true or false.
 *
 * @author Peter Madin
 * @author Ken Egawa
 * @author Sopheanith Ny
 * @version 0.0.1 April 28, 2024
 */
public class TrueFalseQuestion extends MultipleChoiceQuestion {

    /**
     * Constructs a new TrueFalseQuestion object with the
     * specified question text, answer data, and question type.
     *
     * @param theQuestion the text of the question
     * @param theAnswer the answer data, including the correct answer index
     * @param theType the type of the question
     */
    public TrueFalseQuestion(String theQuestion, AnswerData theAnswer, String theType) {
        super(theQuestion, theAnswer, theType);
    }

    /**
     * Retrieves the type of the question.
     *
     * @return the type of the question
     */
    @Override
    public String getType() {
        return super.getType();
    }

    /**
     * Retrieves the text of the question.
     *
     * @return the text of the question
     */
    @Override
    public String getQuestion() {
        return super.getQuestion();
    }

    /**
     * Retrieves the answer data of the question.
     *
     * @return the answer data of the question
     */
    @Override
    public AnswerData getAnswers() {
        return super.getAnswers();
    }
}
