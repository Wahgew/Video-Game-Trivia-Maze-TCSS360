package Model;

/**
 * MultipleChoiceQuestion represents a multiple-choice question with options.
 * It extends the Question class and provides functionality to store and retrieve
 * a question text, answer data, and question type.
 *
 * @author Ken Egawa
 * @author Peter Madin
 * @author Sopheanith Ny
 * @version 0.0.3 April 29, 2024
 */
public class MultipleChoiceQuestion extends Question {

    /**
     * Constructs a MultipleChoiceQuestion object with the specified question text,
     * answer data, and question type.
     *
     * @param theQuestion   the text of the question
     * @param theAnswer     the data representing the answer choices and correct index
     * @param theType       the type of the question
     */
    public MultipleChoiceQuestion(String theQuestion, AnswerData theAnswer, String theType) {
        super(theQuestion, theAnswer, theType);
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
     * Gets the answer data associated with the question.
     *
     * @return the answer data
     */
    @Override
    public AnswerData getAnswers() {
        return super.getAnswers();
    }

}
