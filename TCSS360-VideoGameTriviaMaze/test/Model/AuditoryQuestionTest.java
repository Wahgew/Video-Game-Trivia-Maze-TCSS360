package Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

class AuditoryQuestionTest {
    private TreeMap<String, Boolean> ansAudio;

    private AnswerData answerAudio;

    private Question audioQuestion;

    @BeforeEach
    void setUp() {
        ansAudio = new TreeMap<>();

        ansAudio.put("Little Big Planet 2", true);
        ansAudio.put("Fallout 4", false);
        ansAudio.put("Battlefield 4", false);
        ansAudio.put("Pay Day 2", false);

        answerAudio = new AnswerData(ansAudio);
        String questionAudio = "Which video theme song is this?";
        audioQuestion = new AuditoryQuestion(questionAudio, answerAudio, "Resource/Sounds/Katamari_damacy_intro.mp3","Audio", 1);
    }

    @Test
    void getAudioPath() {
        assertEquals();
    }

    @Test
    void getType() {
    }

    @Test
    void getQuestion() {
    }

    @Test
    void getAnswers() {
    }
}