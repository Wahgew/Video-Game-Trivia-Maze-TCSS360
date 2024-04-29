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

    public Question getRandomQuestion() {
        Question rndQuestion = null;

        try {
            int randomQID = getRandomQuestionID();
            String questionText = getQuestionText(randomQID);
            AnswerData answers = getAnswers(randomQID);
            String questionType = getQuestionType(randomQID);

            rndQuestion = switch (questionType) {
                case "Mutli" -> new MultipleChoiceQuestion(questionText, answers);
                case "T/F" -> new TrueFalseQuestion(questionType, answers);
                case "Short" -> new ShortAnswerQuestion(questionType, answers);
                case "Audio" -> {
                    String questionAudio = getQuestionAudio(randomQID);
                    yield new AuditoryQuestion(questionText, answers, questionAudio);
                }
                case "Image" -> {
                    String questionImage = getQuestionImage(randomQID);
                    yield new ImageQuestion(questionText, answers, questionImage);
                }
                default -> throw new IllegalArgumentException("Error unknown question type :" + questionType);
            };


        } catch (SQLException e) {
            throw new IllegalArgumentException("Error retrieve random question: " + e.getMessage());
        }
        return rndQuestion;
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

    private String getQuestionAudio(int theQuestionID) throws SQLException {
        String audioPath = "";
        PreparedStatement statement = myConnection.prepareStatement("SELECT AudioFile FROM Questions Where QuestionID = ?");
        statement.setInt(1, theQuestionID);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            audioPath = resultSet.getString("AudioPath");
        }
        return audioPath;
    }

    //TODO: May throw null pointer exception fix later.
    private String getQuestionImage(int theQuestionID) throws SQLException {
        String imagePath = "";
        PreparedStatement statement = myConnection.prepareStatement("SELECT ImageURL FROM Questions WHERE QuestionID = ?");
        statement.setInt(1,theQuestionID);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            imagePath = resultSet.getString("ImageURL");
        }
        return imagePath;
    }

    private AnswerData getAnswers(int theQuestionID) throws SQLException {
        List<String> answers = new ArrayList<>();
        int correctAnswerIndex = -1;

        PreparedStatement statement = myConnection.prepareStatement("SELECT AnswerText, IsCorrect FROM Answers WHERE QuestionID = ?");
        statement.setInt(1, theQuestionID);
        ResultSet resultSet = statement.executeQuery();

        int index = 0;
        while (resultSet.next()) {
            String ansText = resultSet.getString("AnswerText");
            boolean isCorrect = resultSet.getInt("IsCorrect") == 1;
            answers.add(ansText);

            if (isCorrect) {
                correctAnswerIndex = index;
            }
            index++;
        }

        return new AnswerData(answers, correctAnswerIndex);
    }

    private String getQuestionType(int theQuestionID) throws SQLException {
        String questionType = "";
        PreparedStatement statement = myConnection.prepareStatement("SELECT QuestionType FROM Questions WHERE QuestionID = ?");
        statement.setInt(1, theQuestionID);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            questionType = resultSet.getString("QuestionType");
        }
        return questionType;
    }

    //TODO: Will remove later keep it now just in case
//    //switch to private
//    static class QuestionsAndAnswers {
//        private final String myQuestionText;
//        private final List<Answer> myAnswers;
//
//        public QuestionsAndAnswers(String theQuestion, List<Answer> theAnswers) {
//            myQuestionText = theQuestion;
//            myAnswers = theAnswers;
//        }
//
//        public String getMyQuestionText() {
//            return myQuestionText;
//        }
//
//        public List<Answer> getMyAnswers() {
//            return myAnswers;
//        }
//    }
//
//    //switch to private
//    static class Answer {
//        private final String myAnswer;
//        private final boolean myCorrect;
//
//        public Answer(String theText, boolean theCorrect) {
//            myAnswer = theText;
//            myCorrect = theCorrect;
//        }
//
//        public String getMyAnswer() {
//            return myAnswer;
//        }
//
//        public Boolean isCorrect() {
//            return myCorrect;
//        }
//    }
}
