package Model;

public class TrueFalseQuestion extends MultipleChoiceQuestion {
    public TrueFalseQuestion(String theQuestion, AnswerData theAnswer) {
        super(theQuestion, theAnswer);
    }

    @Override
    public String getType() {
        return "T/F";
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
