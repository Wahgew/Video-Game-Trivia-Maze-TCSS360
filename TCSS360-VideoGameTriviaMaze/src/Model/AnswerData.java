package Model;

import java.util.List;

/**
 * AnswerData class is an answer object that holds a list of string answers
 * and the correct index of the answer.
 *
 * @author Peter Madin
 * @author Ken Egawa
 * @author Sopheanith Ny
 * @version 0.0.1 April 28, 2024
 */
public class AnswerData {
    private final List<String> myAnswers;
    private final int myCorrectIndex;


    public AnswerData(List<String> theAnswers, int theCorrectIndex) {
        myAnswers = theAnswers;
        myCorrectIndex = theCorrectIndex;
    }

    public List<String> getAnswerChoices() {
        return myAnswers;
    }

    public int getCorrectAnswerIndex() {
        return myCorrectIndex;
    }
}
