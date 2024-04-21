package Model;

public class Question { // Could just use this base parent class for short answer questions? Don't need specific child.
    private final String myQuestion;
    private final String myAnswer;

    /**
     * TODO: CONSTRUCTOR WILL NEED TO PULL FROM A SQLITE DATABASE FOR Q&A.
     * @param theQuestion
     * @param theAnswer
     */
    Question(String theQuestion, String theAnswer) {
        myQuestion = theQuestion;
        myAnswer = theAnswer;
    }
    Question() { // TEST CONSTRUCTOR
        myQuestion = "What is Andrew Hwang's nickname?";
        myAnswer = "Andy";
    }
    String getMyQuestion() {
        return myQuestion;
    }
    String getMyAnswer() {
        return myAnswer;
    }
    boolean checkAnswer(String userAnswer) {
        return myAnswer.equals(userAnswer);
    }
}
