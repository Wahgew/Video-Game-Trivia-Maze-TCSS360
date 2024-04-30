package Model;

public class AuditoryQuestion extends MultipleChoiceQuestion {
    private final String myAudioPath;

    public AuditoryQuestion(String theQuestion, AnswerData theAnswer, String theAudioPath, String theType) {
        super(theQuestion, theAnswer, theType);
        myAudioPath = theAudioPath;
    }

    public String getAudioPath() {
        return myAudioPath;
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
