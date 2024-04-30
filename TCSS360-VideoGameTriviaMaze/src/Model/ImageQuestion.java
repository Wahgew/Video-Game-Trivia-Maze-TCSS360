package Model;

public class ImageQuestion extends MultipleChoiceQuestion{

    private final String myImagePath;

    public ImageQuestion(String theQuestion, AnswerData theAnswer, String theImageFile, String theType) {
        super(theQuestion, theAnswer, theType);
        myImagePath = theImageFile;
    }

    public String getImagePath() {
        return myImagePath;
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
