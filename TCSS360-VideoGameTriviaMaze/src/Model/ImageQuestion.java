package Model;

/**
 * ImageQuestion represents a multiple-choice question with an associated image.
 * Retrieves a question text, answer data, question type, and image path.
 *
 * @author Ken Egawa
 * @author Peter Madin
 * @author Sopheanith Ny
 * @version 0.0.3 April 29, 2024
 */
public class ImageQuestion extends MultipleChoiceQuestion {

    /**
     * The path to the image associated with the question.
     */
    private final String myImagePath;

    /**
     * Constructs an ImageQuestion object with the specified question text,
     * answer data, image path, and question type.
     *
     * @param theQuestion   the text of the question
     * @param theAnswer     the data representing the answer choices and correct index
     * @param theImageFile  the path to the image associated with the question
     * @param theType       the type of the question
     */
    public ImageQuestion(String theQuestion, AnswerData theAnswer, String theImageFile, String theType, int theID) {
        super(theQuestion, theAnswer, theType, theID);
        myImagePath = theImageFile;
    }

    /**
     * Gets the path to the image associated with the question.
     *
     * @return the path to the image
     */
    public String getImagePath() {
        return myImagePath;
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
