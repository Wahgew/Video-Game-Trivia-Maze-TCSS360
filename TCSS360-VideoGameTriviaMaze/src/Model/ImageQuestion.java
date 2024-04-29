package Model;

public class ImageQuestion extends MultipleChoiceQuestion{

    private final String myImagePath;

    public ImageQuestion(String theQuestion, AnswerData theAnswer, String theImageFile) {
        super(theQuestion, theAnswer);
        myImagePath = theImageFile;
    }

    public String getImagePath() {
        return myImagePath;
    }

    @Override
    public String getType() {
        return "Image";
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
