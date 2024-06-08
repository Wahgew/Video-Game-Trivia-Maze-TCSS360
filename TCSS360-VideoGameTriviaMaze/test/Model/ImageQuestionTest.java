package Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

class ImageQuestionTest {
    private TreeMap<String, Boolean> ansImage;

    private AnswerData answerImage;

    private ImageQuestion imageQuestion;

    @BeforeEach
    void setup() {
        ansImage = new TreeMap<>();

        ansImage.put("Clash of Clans", true);
        ansImage.put("War Frame", false);
        ansImage.put("Doddle Jump", false);
        ansImage.put("Jet Pack Joy Ride", false);

        answerImage = new AnswerData(ansImage);
        String questionImage = "Which video game is made by Supercell?";
        imageQuestion = new ImageQuestion(questionImage, answerImage,"Resource/Images/supercell_logo.png", "Image", 2);
    }

    @Test
    void getImagePath() {
        assertEquals("Resource/Images/supercell_logo.png", imageQuestion.getImagePath());
    }

    @Test
    void getType() {
        assertEquals("Image", imageQuestion.getType());
    }

    @Test
    void getQuestion() {
        assertEquals("Which video game is made by Supercell?", imageQuestion.getQuestion());
    }

    @Test
    void getAnswers() {
        assertEquals("Clash of Clans, Doddle Jump, Jet Pack Joy Ride, War Frame", imageQuestion.getAnswers().toString());
    }
}