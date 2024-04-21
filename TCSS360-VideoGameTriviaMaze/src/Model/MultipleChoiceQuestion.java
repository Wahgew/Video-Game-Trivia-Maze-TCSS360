package Model;

public class MultipleChoiceQuestion extends Question {
    private String myChoice1;
    private String myChoice2;
    private String myChoice3;
    private boolean isTrueFalse;

    /**
     * TODO: CONSTRUCTOR WILL NEED TO PULL FROM A SQLITE DATABASE FOR Q&A.
     * @param theQuestion
     * @param theAnswer
     * @param theChoice1
     * @param theChoice2
     * @param theChoice3
     * @param theTrueFalse
     */
    MultipleChoiceQuestion(String theQuestion, String theAnswer, String theChoice1, String theChoice2,
                           String theChoice3, boolean theTrueFalse) {
        super(theQuestion, theAnswer);
        myChoice1 = theChoice1;
        myChoice2 = theChoice2;
        myChoice3 = theChoice3;
        isTrueFalse = theTrueFalse;
    }
    MultipleChoiceQuestion() { //TEST CONSTRUCTOR
        super("What is Andrew Hwang's nickname?", "Andy");
        myChoice1 = "Drew";
        myChoice2 = "The Dee";
        myChoice3 = "Kim";
        isTrueFalse = false;
    }
}
