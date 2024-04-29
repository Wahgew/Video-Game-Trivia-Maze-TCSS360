package Model;

public class AuditoryQuestion extends MultipleChoiceQuestion {
    private final String myAudioPath;

    public AuditoryQuestion(String theQuestion, AnswerData theAnswer, String theAudioPath) {
        super(theQuestion, theAnswer);
        myAudioPath = theAudioPath;
    }

    public String getAudioPath() {
        return myAudioPath;
    }

    @Override
    public String getType() {
        return "Audio";
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
