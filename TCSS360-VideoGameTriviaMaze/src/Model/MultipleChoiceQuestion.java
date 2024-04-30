package Model;

import java.util.List;

/**
 * The MultipleChoiceQuestion class represents a multiple-choice question with options.
 * Each multiple-choice question has a question, an answer, and three choices.
 * Additionally, it can be flagged as a true/false question.
 *
 * @author Ken Egawa
 * @author Peter Madin
 * @author Sopheanith Ny
 * @version 0.0.3 April 29, 2024
 */
public class MultipleChoiceQuestion extends Question {
    //TODO: This is the new real constructor delete old one later
    public MultipleChoiceQuestion(String theQuestion, AnswerData theAnswer, String theType) {
        super(theQuestion, theAnswer, theType);
    }

    @Override
    public String getType() {
        return super.getType();
    }

    @Override
    public String getQuestion() {
        return super.getQuestion();
    }

    @Override
    public AnswerData getAnswers() {
        return super.getAnswers();
    }

}
