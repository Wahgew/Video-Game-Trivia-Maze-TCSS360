package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.sqlite.SQLiteDataSource;

/**
 * QuestionAnswerDatabase represents an SQlite database that
 * holds Questions and answers
 *
 * @author Peter Madin
 * @author Ken Egawa
 * @author Sopheanith Ny
 * @version 0.0.2 April 29, 2024
 */
public class QuestionAnswerDatabase {
    /**
     * Get database file location from system property
     */
    private SQLiteDataSource myDB = null;

    /**
     * Database connection
     */
    private Connection myConnection;

    public QuestionAnswerDatabase() {
        try {
            myDB = new SQLiteDataSource();
            myDB.setUrl("jdbc:sqlite:QuestionAnswerDB.db");
            myConnection = myDB.getConnection();
            System.out.println("Connected to the database.");
        } catch (Exception e) {
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

    public SQLiteDataSource getMyDB() {
        return myDB;
    }

    public Question getRandomQuestion() {
        Question rndQuestion = null;

        try {
            int randomQID = getRandomQuestionID();
            String questionText = getQuestionText(randomQID);
            AnswerData answers = getAnswers(randomQID);
            String questionType = getQuestionType(randomQID);

            rndQuestion = switch (questionType) {
                case "Multi" -> new MultipleChoiceQuestion(questionText, answers, questionType);
                case "T/F" -> new TrueFalseQuestion(questionText, answers, questionType);
                case "Short" -> new ShortAnswerQuestion(questionText, answers, questionType);
                case "Audio" -> {
                    String questionAudio = getQuestionAudio(randomQID);
                    yield new AuditoryQuestion(questionText, answers, questionAudio, questionType);
                }
                case "Image" -> {
                    String questionImage = getQuestionImage(randomQID);
                    yield new ImageQuestion(questionText, answers, questionImage, questionType);
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
            audioPath = resultSet.getString("AudioFile");
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
}