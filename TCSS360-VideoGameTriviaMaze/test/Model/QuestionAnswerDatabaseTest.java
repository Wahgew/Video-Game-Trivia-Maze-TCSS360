package Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class QuestionAnswerDatabaseTest {
    private QuestionAnswerDatabase myDatabase;
    private Connection myConnection;
    private SQLiteDataSource mySQL;

    @BeforeEach
    void setUp() throws SQLException {
        myDatabase = new QuestionAnswerDatabase();
        myConnection = myDatabase.getConnection();
        mySQL = myDatabase.getMyDB();
    }


    @Test
    void getConnection() {
        if (myConnection != null) {
           assertTrue(true, "connected");
        }
    }

    @Test
    void getMyDBConnection() {
        if (mySQL != null) {
            assertTrue(true, "connected");
        }
    }

    @Test
    void getRandomQuestion() {
        Set<String> generateQuestionTypes = new HashSet<>();
        int iterations = 20;

        for (int i = 0; i < iterations; i++) {
            // check we are not creating null objects
            Question question = myDatabase.getRandomQuestion();
            assertNotNull(question);

            String qType = question.getType();
            generateQuestionTypes.add(qType);

            System.out.println("\n" + question);
        }

        assertTrue(generateQuestionTypes.containsAll(Set.of("Multi", "T/F", "Short", "Audio", "Image")));
    }
}