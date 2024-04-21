package Model;

/**
 * The MultipleChoiceQuestion class represents a multiple-choice question with options.
 * Each multiple-choice question has a question, an answer, and three choices.
 * Additionally, it can be flagged as a true/false question.
 *
 * @author Ken Egawa
 * @author Peter Madin
 * @author Sopheanith Ny
 * @version 0.0.1 April 20, 2024
 */
public class MultipleChoiceQuestion extends Question {
    /**
     * The first choice for the multiple-choice question.
     */
    private String myChoice1;

    /**
     * The second choice for the multiple-choice question.
     */
    private String myChoice2;

    /**
     * The third choice for the multiple-choice question.
     */
    private String myChoice3;

    /**
     * A boolean flag indicating whether the question is a true/false question.
     */
    private boolean isTrueFalse;

    /**
     * Constructs a new MultipleChoiceQuestion object with
     * the specified question, answer, choices, and true/false flag.
     *
     * @param theQuestion
     * @param theAnswer
     * @param theChoice1
     * @param theChoice2
     * @param theChoice3
     * @param theTrueFalse
     */
    MultipleChoiceQuestion(String theQuestion, String theAnswer, String theChoice1, String theChoice2,
                           String theChoice3, boolean theTrueFalse) {
        // TODO:CONSTRUCTOR WILL NEED TO PULL FROM A SQLITE DATABASE FOR Q&A.
        super(theQuestion, theAnswer);
        myChoice1 = theChoice1;
        myChoice2 = theChoice2;
        myChoice3 = theChoice3;
        isTrueFalse = theTrueFalse;
    }

    /**
     * Constructs a new MultipleChoiceQuestion object with predefined values.
     *
     * This constructor is for testing purposes and provides predefined values for the question,
     * answer, and choices.
     */
    MultipleChoiceQuestion() { //TEST CONSTRUCTOR
        super("What is Andrew Hwang's nickname?", "Andy");
        myChoice1 = "Drew";
        myChoice2 = "The Dee";
        myChoice3 = "Kim";
        isTrueFalse = false;
    }
}
