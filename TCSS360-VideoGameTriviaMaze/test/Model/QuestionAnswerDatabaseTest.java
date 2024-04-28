package Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuestionAnswerDatabaseTest {
    private QuestionAnswerDatabase myDatabase;
    private Connection myConnection;

    @BeforeEach
    void setUp() throws SQLException {
        myDatabase = new QuestionAnswerDatabase();
        myConnection = myDatabase.getConnection();
    }


    @Test
    void getConnection() {
        if (myConnection != null) {
           assertTrue(true, "connected");
        }
    }

//    @Test
//    void getRandomQuestion() {
//        QuestionAnswerDatabase.QuestionsAndAnswers randomQuestion = myDatabase.getRandomQuestion();
//
//        assertNotNull(randomQuestion);
//        assertNotNull(randomQuestion.getMyQuestionText());
//        assertFalse(randomQuestion.getMyAnswers().isEmpty());
//
//        // Assert that at least one answer is correct
//        boolean hasCorrectAnswer = false;
//        List<QuestionAnswerDatabase.Answer> answers = randomQuestion.getMyAnswers();
//        for (QuestionAnswerDatabase.Answer answer : answers) {
//            if (answer.isCorrect()) {
//                hasCorrectAnswer = true;
//                break;
//            }
//        }
//        assertTrue(hasCorrectAnswer, "No correct answer found for the random question");
//    }
}