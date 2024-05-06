package Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuestionTest {
    private List<String> ansTF;
    private List<String> ansShort;
    private List<String> ansMulti;
    private List<String> ansImage;
    private List<String> ansAudio;

    private AnswerData answerTF;
    private AnswerData answerMulti;
    private AnswerData answerShort;
    private AnswerData answerImage;
    private AnswerData answerAudio;

    private Question trueFalseQuestion;
    private Question shortQuestion;
    private Question multiQuestion;
    private Question imageQuestion;
    private Question audioQuestion;


    @BeforeEach
    void setUp() {
        ansTF = new ArrayList<>();
        ansShort = new ArrayList<>();
        ansMulti = new ArrayList<>();
        ansImage = new ArrayList<>();
        ansAudio = new ArrayList<>();

        ansTF.add("False");
        ansTF.add("True");

        ansShort.add("test");

        ansMulti.add("Ken Egawa");
        ansMulti.add("Sopheanith Ny");
        ansMulti.add("Peter W Madin");
        ansMulti.add("All of the above");

        ansImage.add("Hideo Kojima");
        ansImage.add("Gabe Newell");
        ansImage.add("John Carmack");
        ansImage.add("Shigeru Miyamoto");

        ansAudio.add("Mortal Kombat");
        ansAudio.add("Mortal Kombat II");
        ansAudio.add("Mortal Kombat 3");
        ansAudio.add("Mortal Kombat 11");


        answerTF = new AnswerData(ansTF, 0);
        answerShort = new AnswerData(ansShort, 0);
        answerMulti = new AnswerData(ansMulti, 0);
        answerImage = new AnswerData(ansImage, 0);
        answerAudio = new AnswerData(ansAudio, 0);

        String questionTF = "Sims can take a job as a taxi driver?";
        String questionShort = "Just type test";
        String questionMulti = "Who are the devs of this trivia game?";
        String questionImage = "Who is this man?";
        String questionAudio = "Testing Audio";

       trueFalseQuestion = new TrueFalseQuestion(questionTF, answerTF, "T/F");
       shortQuestion = new ShortAnswerQuestion(questionShort, answerShort, "Short");
       multiQuestion = new MultipleChoiceQuestion(questionMulti, answerMulti, "Multi");
       imageQuestion = new ImageQuestion(questionImage, answerImage, "None", "Image");
       audioQuestion = new AuditoryQuestion(questionAudio, answerAudio, "None", "Audio");
    }


    @Test
    void checkAnswer() {
        List<String> ans = new ArrayList<>();
        ans.add("microsoft");
        AnswerData answerData = new AnswerData(ans, 0);

        //Create short answer question.
        String question = "Who released the first flight simulator game?";
        Question shortAnswer = new ShortAnswerQuestion(question, answerData, "Short");

        assertTrue(shortAnswer.checkAnswer("microsoft"));
        assertTrue(shortAnswer.checkAnswer("Microsoft"));
        assertTrue(shortAnswer.checkAnswer("MIcrOSofT"));
        assertFalse(shortAnswer.checkAnswer("Apple"));
    }

    @Test
    void getType() {
        assertEquals(trueFalseQuestion.getType(), "T/F");
        assertEquals(shortQuestion.getType(), "Short");
        assertEquals(multiQuestion.getType(), "Multi");
        assertEquals(imageQuestion.getType(), "Image");
        assertEquals(audioQuestion.getType(), "Audio");
    }

    @Test
    void getQuestion() {
        assertEquals(trueFalseQuestion.getQuestion(), "Sims can take a job as a taxi driver?");
        assertEquals(shortQuestion.getQuestion(),"Just type test");
        assertEquals(multiQuestion.getQuestion(),"Who are the devs of this trivia game?");
        assertEquals(imageQuestion.getQuestion(),"Who is this man?");
        assertEquals(audioQuestion.getQuestion(),"Testing Audio");
    }

    @Test
    void getAnswers() {
        assertEquals("False, True", trueFalseQuestion.getAnswers().toString());
        assertEquals("test", shortQuestion.getAnswers().toString());
        assertEquals("Ken Egawa, Sopheanith Ny, Peter W Madin, All of the above", multiQuestion.getAnswers().toString());
        assertEquals("Hideo Kojima, Gabe Newell, John Carmack, Shigeru Miyamoto", imageQuestion.getAnswers().toString());
        assertEquals("Mortal Kombat, Mortal Kombat II, Mortal Kombat 3, Mortal Kombat 11", audioQuestion.getAnswers().toString());
    }

    @Test
    void testToString() {
    }
}