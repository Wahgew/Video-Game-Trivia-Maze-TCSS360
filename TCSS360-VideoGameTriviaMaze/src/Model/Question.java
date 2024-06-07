package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    private String myQuestion;

    /**
     * The correct answer to the question.
     */
    private AnswerData myAnswers;

    /**
     * The question Type (e.g., "Multi", "T/F", "Short", "Audio", "Image").
     */
    private String myQuestionType;

    /**
     * the question ID.
     */
    private int myID;

    /**
     * Question constructor that creates a question with a question text,
     * AnswerData, and type.
     *
     * @param theQuestion the question text.
     * @param theAnswer the data representing the answer choices and correct index
     */
    protected Question(final String theQuestion, final AnswerData theAnswer, final String theType, final int theID) {
        setQuestion(theQuestion);
        setAnswers(theAnswer);
        setQuestionType(theType);
        setQuestionID(theID);
    }

    boolean checkAnswer(final String userAnswer) {
        return  myAnswers.getRightAnswer().equals(userAnswer.toLowerCase());
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
     * Gets the hash tree of answers to the question.
     *
     * @return hash tree of possible answers
     */
    public AnswerData getAnswers() {
        return myAnswers;
    }

    /**
     * Gets the correct answer to the question.
     *
     * @return the correct answer
     */
    public String getCorrectAnswer() {
        return myAnswers.getRightAnswer();
    }

    public int getID() {
        return myID;
    }

    public String getHint() {
        switch (myQuestionType) {
            case "Multi", "Image", "Audio" -> {
                List<String> possibleAnswers = new ArrayList<>();
                boolean falseAns = false;
                boolean trueAns = false;
                for (Map.Entry<String, Boolean> entryMap : myAnswers.getAnswerChoices().entrySet()) {
                    if (entryMap.getValue() && !trueAns) {
                        trueAns = true;
                        possibleAnswers.add(entryMap.getKey());
                    }
                    if (!entryMap.getValue() && !falseAns) {
                        falseAns = true;
                        possibleAnswers.add(entryMap.getKey());
                    }
                }
                return possibleAnswers.toString();
            }
            case "T/F" -> { // TODO: figure out another way to do T/F hints?
                return myAnswers.getRightAnswer();
            }
            case "Short" -> {
                String shortAns = myAnswers.getRightAnswer();
                return shortAns.substring(0, shortAns.length() / 2);
            }
            default -> throw new IllegalArgumentException("Error unknown question type: " + myQuestionType);
        }
    }

    public void setQuestion(final String theQuestion) {
         if (theQuestion == null) {
             throw new IllegalArgumentException("Question can't be null");
         }

         myQuestion = theQuestion;
    }

    public void setAnswers(final AnswerData theAnswer) {
        if (theAnswer == null) {
            throw new IllegalArgumentException("Answers cannot be null");
        }
        myAnswers = theAnswer;
    }

    public void setQuestionType(final String theType) {
        if (theType == null || theType.isEmpty()) {
            throw new IllegalArgumentException("Question type cannot be null or empty");
        }
        myQuestionType = theType;
    }

    public void setQuestionID(final int theID) {
        if (theID <= 0) {
            throw new IllegalArgumentException("Question ID must be a positive integer");
        }
        myID = theID;
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
        sb.append("Question ID: ").append(myID).append("\n");
        sb.append("Question Type: ").append(myQuestionType).append("\n");
        sb.append("Question: ").append(myQuestion).append("\n");
        sb.append("Answers:\n");

        Map<String, Boolean> answerMap = myAnswers.getAnswerChoices();
        int index = 1;

        for (Map.Entry<String, Boolean> entry : answerMap.entrySet()) {
            String answer = entry.getKey();
            boolean isCorrect = entry.getValue();
            sb.append(" ").append(index).append(". ").append(answer);

            if (isCorrect) {
                sb.append(" (Correct)");
            }
            sb.append("\n");
            index++;
        }

        return sb.toString();
    }
}
