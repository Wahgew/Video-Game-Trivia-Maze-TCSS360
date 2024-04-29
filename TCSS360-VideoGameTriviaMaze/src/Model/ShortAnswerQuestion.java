package Model;

public class ShortAnswerQuestion extends Question {
    private final String myCorrectAnswer;

    public ShortAnswerQuestion(String theQuestion, AnswerData theAnswerData) {
        super(theQuestion, theAnswerData);
        myCorrectAnswer = theAnswerData.getAnswerChoices().toString();
    }

    @Override
    public String getType() {
        return "Short";
    }

    @Override
    public String getQuestion() {
        return super.getQuestion();
    }

    public String getMyCorrectAnswer() {
        return myCorrectAnswer;
    }
}
