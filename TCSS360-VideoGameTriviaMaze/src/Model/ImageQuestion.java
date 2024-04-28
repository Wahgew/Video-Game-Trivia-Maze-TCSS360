package Model;

public class ImageQuestion extends MultipleChoiceQuestion{

    private final String myImagePath;

    public ImageQuestion(String theQuestion, AnswerData theAnswer, String theImageFile) {
        super(theQuestion, theAnswer);
        myImagePath = theImageFile;
    }
}
