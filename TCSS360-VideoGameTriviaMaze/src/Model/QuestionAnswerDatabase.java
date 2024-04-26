package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * QuestionAnswerDatabase represents an SQlite database that
 * holds Questions and answers
 *
 * @author Peter Madin
 * @author Ken Egawa
 * @author Sopheanith Ny
 * @version 0.0.1 April 21, 2024
 */
public class QuestionAnswerDatabase {
    /**
     * Get database file location from system property
     */
    private static final String JDBC_URL = "jdbc:sqlite:QuestionAnswerDB.db";
    //private static final String JDBC_URL = "jdbc:sqlite:" + System.getProperty("database.location");

    /**
     * Database connection
     */
    private Connection myConnection;

    public QuestionAnswerDatabase() {
        try {
            myConnection = DriverManager.getConnection(JDBC_URL);
            System.out.println("Connected to the database.");
        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
        }
    }

    /**
     * Getter for database connection.
     * @return returns database connection.
     */
    public Connection getConnection() {
        return myConnection;
    }

    public QuestionsAndAnswers getRandomQuestion() {
        QuestionsAndAnswers QNA = null;

        try {
            int randomQID = getRandomQuestionID();
            String questionText = getQuestionText(randomQID);
            List<Answer> answers = getAnswers(randomQID);

            QNA = new QuestionsAndAnswers(questionText, answers);
        } catch (SQLException e) {
            throw new IllegalArgumentException("Error retrieve random question: " + e.getMessage());
        }
        return QNA;
    }

    private int getRandomQuestionID() throws SQLException {
        int randomQID = 0;
        PreparedStatement statement = myConnection.prepareStatement("SELECT QuestionID FROM Questions ORDER BY RANDOM() LIMIT 1");
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            randomQID = resultSet.getInt("QuestionID");
        }
        return randomQID;
    }

    private String getQuestionText(int theQuestionID) throws SQLException {
        String questionText = "";
        PreparedStatement statement = myConnection.prepareStatement("SELECT QuestionText FROM Questions WHERE QuestionID = ?");
        statement.setInt(1, theQuestionID);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            questionText = resultSet.getString("QuestionText");
        }
        return questionText;
    }

    private List<Answer> getAnswers(int theQuestionID) throws SQLException {
        List<Answer> answers = new ArrayList<>();

        PreparedStatement statement = myConnection.prepareStatement("SELECT AnswerText, IsCorrect FROM Answers WHERE QuestionID = ?");
        statement.setInt(1, theQuestionID);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            String ansText = resultSet.getString("AnswerText");
            boolean isCorrect = resultSet.getInt("IsCorrect") == 1;
            answers.add(new Answer(ansText, isCorrect));
        }
        return answers;
    }

    static class QuestionsAndAnswers {
        private final String myQuestionText;
        private final List<Answer> myAnswers;

        public QuestionsAndAnswers(String theQuestion, List<Answer> theAnswers) {
            myQuestionText = theQuestion;
            myAnswers = theAnswers;
        }

        public String getMyQuestionText() {
            return myQuestionText;
        }

        public List<Answer> getMyAnswers() {
            return myAnswers;
        }
    }

    static class Answer {
        private final String myAnswer;
        private final boolean myCorrect;

        public Answer(String theText, boolean theCorrect) {
            myAnswer = theText;
            myCorrect = theCorrect;
        }

        public String getMyAnswer() {
            return myAnswer;
        }

        public Boolean isCorrect() {
            return myCorrect;
        }
    }
}
