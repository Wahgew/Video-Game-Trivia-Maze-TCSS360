package Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

class QuestionTest {
    private TreeMap<String, Boolean> ansTF;
    private TreeMap<String, Boolean> ansShort;
    private TreeMap<String, Boolean> ansMulti;
    private TreeMap<String, Boolean> ansImage;
    private TreeMap<String, Boolean> ansAudio;

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
        ansTF = new TreeMap<>();
        ansShort = new TreeMap<>();
        ansMulti = new TreeMap<>();
        ansImage = new TreeMap<>();
        ansAudio = new TreeMap<>();

        ansTF.put("True", false);
        ansTF.put("False", true);

        ansShort.put("test", true);

        ansMulti.put("Ken Egawa", false);
        ansMulti.put("Sopheanith Ny", false);
        ansMulti.put("Peter W Madin", false);
        ansMulti.put("All of the above", true);

        ansImage.put("Hideo Kojima", true);
        ansImage.put("Gabe Newell", false);
        ansImage.put("John Carmack", false);
        ansImage.put("Shigeru Miyamoto", false);

        ansAudio.put("Mortal Kombat", true);
        ansAudio.put("Mortal Kombat II", false);
        ansAudio.put("Mortal Kombat 3", false);
        ansAudio.put("Mortal Kombat 11", false);


        answerTF = new AnswerData(ansTF);
        answerShort = new AnswerData(ansShort);
        answerMulti = new AnswerData(ansMulti);
        answerImage = new AnswerData(ansImage);
        answerAudio = new AnswerData(ansAudio);

        String questionTF = "Sims can take a job as a taxi driver?";
        String questionShort = "Just type test";
        String questionMulti = "Who are the devs of this trivia game?";
        String questionImage = "Who is this man?";
        String questionAudio = "Testing Audio";

       trueFalseQuestion = new TrueFalseQuestion(questionTF, answerTF, "T/F",1);
       shortQuestion = new ShortAnswerQuestion(questionShort, answerShort, "Short", 2);
       multiQuestion = new MultipleChoiceQuestion(questionMulti, answerMulti, "Multi", 3);
       imageQuestion = new ImageQuestion(questionImage, answerImage, "None", "Image", 4);
       audioQuestion = new AuditoryQuestion(questionAudio, answerAudio, "None", "Audio", 5);
    }


    @Test
    void checkAnswer() {
        TreeMap<String, Boolean> ans = new TreeMap<>();
        ans.put("microsoft", true);
        AnswerData answerData = new AnswerData(ans);

        //Create short answer question.
        String question = "Who released the first flight simulator game?";
        Question shortAnswer = new ShortAnswerQuestion(question, answerData, "Short",6);

        assertTrue(shortAnswer.checkAnswer("microsoft"));
        assertTrue(shortAnswer.checkAnswer("Microsoft"));
        assertTrue(shortAnswer.checkAnswer("MIcrOSofT"));
        assertFalse(shortAnswer.checkAnswer("Apple"));
    }

    @Test
    void getType() {
        assertEquals("T/F", trueFalseQuestion.getType());
        assertEquals("Short", shortQuestion.getType());
        assertEquals("Multi", multiQuestion.getType());
        assertEquals("Image", imageQuestion.getType());
        assertEquals("Audio", audioQuestion.getType());
    }

    @Test
    void getQuestion() {
        assertEquals("Sims can take a job as a taxi driver?", trueFalseQuestion.getQuestion());
        assertEquals("Just type test", shortQuestion.getQuestion());
        assertEquals("Who are the devs of this trivia game?", multiQuestion.getQuestion());
        assertEquals("Who is this man?", imageQuestion.getQuestion());
        assertEquals("Testing Audio", audioQuestion.getQuestion());
    }

    @Test
    void getAnswers() {
        assertEquals("False, True", trueFalseQuestion.getAnswers().toString());
        assertEquals("test", shortQuestion.getAnswers().toString());
        assertEquals("All of the above, Ken Egawa, Peter W Madin, Sopheanith Ny", multiQuestion.getAnswers().toString());
        assertEquals("Gabe Newell, Hideo Kojima, John Carmack, Shigeru Miyamoto", imageQuestion.getAnswers().toString());
        assertEquals("Mortal Kombat, Mortal Kombat 11, Mortal Kombat 3, Mortal Kombat II", audioQuestion.getAnswers().toString());
    }

    @Test
    void getCorrectAnswer() {
        assertEquals("False", trueFalseQuestion.getCorrectAnswer());
        assertEquals("test", shortQuestion.getCorrectAnswer());
        assertEquals("All of the above", multiQuestion.getCorrectAnswer());
        assertEquals("Hideo Kojima", imageQuestion.getCorrectAnswer());
        assertEquals("Mortal Kombat", audioQuestion.getCorrectAnswer());
    }

    @Test
    void getID() {
        assertEquals(1, trueFalseQuestion.getID());
        assertEquals(2, shortQuestion.getID());
        assertEquals(3, multiQuestion.getID());
        assertEquals(4, imageQuestion.getID());
        assertEquals(5, audioQuestion.getID());
    }
}