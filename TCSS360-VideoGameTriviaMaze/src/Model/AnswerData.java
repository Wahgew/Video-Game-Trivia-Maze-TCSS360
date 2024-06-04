package Model;

import java.util.Map;
import java.util.TreeMap;

/**
 * AnswerData class represents a set of answer choices and the index of the correct answer.
 * It holds a list of string answers and the correct index of the answer.
 * This class is used to encapsulate answer choices and their correct index
 * within a question object.
 *
 * @author Peter Madin
 * @author Ken Egawa
 * @author Sopheanith Ny
 * @version 0.0.2 May 9, 2024
 */
public class AnswerData {

    /**
     * TreeMap to store all answer data entries and their correctness.
     */
    private final TreeMap<String, Boolean> myAnswers;

    /**
     * The correct answer String.
     */
    private final String myCorrectAnswer;

    /**
     * Constructs an AnswerData object with the specified list of answers and correct index.
     *
     * @param theAnswers a list of string answers
     */
    public AnswerData(TreeMap<String, Boolean> theAnswers) {
        myAnswers = new TreeMap<>(theAnswers);
        myCorrectAnswer = findCorrectAns();
    }

    /**
     * Finds the correct answer from the map of answers.
     *
     * @return the correct answer as a String, or null if no correct answer is found
     */
    public String findCorrectAns() {
        for (Map.Entry<String, Boolean> entry : myAnswers.entrySet()) {
            if (entry.getValue()) {
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * Gets the correct answer stored in the myCorrectAnswer field.
     *
     * @return the correct answer as a String
     */
    public String getRightAnswer() {
        return myCorrectAnswer;
    }

    /**
     * Gets the list of answer choices.
     *
     * @return the list of answer choices
     */
    public TreeMap<String, Boolean> getAnswerChoices() {
        return myAnswers;
    }

    /**
     * Returns a string representation of all the answers.
     * Each answer is separated by a comma and a space.
     *
     * @return a comma-separated string of all answers
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Boolean> entry : myAnswers.entrySet()) {
            String answer = entry.getKey();
            sb.append(answer).append(", ");
        }

        if (sb.length() > 0) {
            sb.setLength(sb.length() - 2);
        }
        return sb.toString();
    }
}
