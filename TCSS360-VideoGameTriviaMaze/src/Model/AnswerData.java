package Model;

import java.util.List;

/**
 * AnswerData class represents a set of answer choices and the index of the correct answer.
 * It holds a list of string answers and the correct index of the answer.
 * This class is used to encapsulate answer choices and their correct index
 * within a question object.
 *
 * @author Peter Madin
 * @author Ken Egawa
 * @author Sopheanith Ny
 * @version 0.0.1 April 28, 2024
 */
public class AnswerData {
    private final List<String> myAnswers;
    private final int myCorrectIndex;


    /**
     * Constructs an AnswerData object with the specified list of answers and correct index.
     *
     * @param theAnswers a list of string answers
     * @param theCorrectIndex the index of the correct answer in the list
     */
    public AnswerData(List<String> theAnswers, int theCorrectIndex) {
        myAnswers = theAnswers;
        myCorrectIndex = theCorrectIndex;
    }

    /**
     * Gets the list of answer choices.
     *
     * @return the list of answer choices
     */
    public List<String> getAnswerChoices() {
        return myAnswers;
    }

    /**
     * Gets the index of the correct answer in the list of answer choices.
     *
     * @return the index of the correct answer
     */
    public int getCorrectAnswerIndex() {
        return myCorrectIndex;
    }
}
