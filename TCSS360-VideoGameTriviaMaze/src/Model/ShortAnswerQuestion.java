package Model;

public class ShortAnswerQuestion extends Question {
    private final String myCorrectAnswer;

    public ShortAnswerQuestion(String theQuestion, AnswerData theAnswerData, String theType) {
        super(theQuestion, theAnswerData, theType);
        myCorrectAnswer = theAnswerData.getAnswerChoices().toString();
    }

    @Override
    public String getType() {
        return super.getType();
    }

    @Override
    public String getQuestion() {
        return super.getQuestion();
    }

    public String getCorrectAnswer() {
        return myCorrectAnswer;
    }
}
