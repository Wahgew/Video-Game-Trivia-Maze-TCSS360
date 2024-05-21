package Model;

import java.sql.*;
import java.util.*;

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
     * Singleton instance of a database.
     */
    private static QuestionAnswerDatabase myDatabase;

    /**
     * Create SQLite data source.
     */
    private SQLiteDataSource myDB = null;
    /**
     * Hashtable of Integer question id key to Question value.
     */
    private Hashtable<Integer, Question> myQuestionHash;

    private int myQuestionID;

    /**
     * Database connection.
     */
    private Connection myConnection;

    /**
     * Constructs a new QuestionAnswerDatabase object and establishes a connection
     * to the SQLite database.
     */
    public QuestionAnswerDatabase() {
        try {
            myDB = new SQLiteDataSource();
            myDB.setUrl("jdbc:sqlite:QuestionAnswerDB.db");
            myConnection = myDB.getConnection();
            System.out.println("Connected to the database.");
            myQuestionHash = new Hashtable<>(150); // update initial capacity as database of Q'A's grows.
            instantiateHash();


        } catch (Exception e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
        }
    }

    /**
     * Instantiates the Hashtable field.
     *
     * @throws SQLException if a database access error occurs.
     */
    private void instantiateHash() throws SQLException { // probably shouldn't throw SQLException like this
        PreparedStatement statement = myConnection.prepareStatement("SELECT QuestionID FROM Questions");
        ResultSet resultSet = statement.executeQuery();
        Question question;

        while (resultSet.next()) {
            int questionID = resultSet.getInt("QuestionID");
            String questionText = getQuestionText(questionID);
            AnswerData answers = getAnswers(questionID);
            String questionType = getQuestionType(questionID);

            question = switch (questionType) {
                case "Multi" -> new MultipleChoiceQuestion(questionText, answers, questionType, questionID);
                case "T/F" -> new TrueFalseQuestion(questionText, answers, questionType, questionID);
                case "Short" -> new ShortAnswerQuestion(questionText, answers, questionType, questionID);
                case "Audio" -> {
                    String questionAudio = getQuestionAudio(questionID);
                    yield new AuditoryQuestion(questionText, answers, questionAudio, questionType, questionID);
                }
                case "Image" -> {
                    String questionImage = getQuestionImage(questionID);
                    yield new ImageQuestion(questionText, answers, questionImage, questionType, questionID);
                }
                default -> throw new IllegalArgumentException("Error unknown question type :" + questionType);
            };
            myQuestionHash.put(questionID, question);
        }
    }

    public void removeSeenQuestions() {
        Player player = Player.getInstance();
        HashMap<Integer, Boolean> answeredQuestions = player.getMyQuestionsAnswered();

        for (Integer questionId : answeredQuestions.keySet()) {
            myQuestionHash.remove(questionId);
        }
    }

    /**
     * Getter for database connection.
     *
     * @return returns database connection.
     */
    public Connection getConnection() {
        return myConnection;
    }

    /**
     * Getter for the SQLiteDataSource object.
     *
     * @return the SQLiteDataSource object.
     */
    public SQLiteDataSource getMyDB() {
        return myDB;
    }

    /**
     * Getter for the database question ID.
     *
     * @return the integer question id.
     */
    public int getQuestionID() {
        return myQuestionID;
    }

    public static synchronized QuestionAnswerDatabase getInstance() {
        if (myDatabase == null) {
            myDatabase = new QuestionAnswerDatabase();
        }
        return myDatabase;
    }


    public Question getRandomQuestion() {
        Question rndQuestion = null;

        if (myQuestionHash.isEmpty()) {
            try {
                instantiateHash();
            } catch (Exception e) {
                System.err.println("Error instantiating hash: " + e.getMessage());
                return null;
            }
        }

        int randomQID = getRandomQuestionID(myQuestionHash.keySet());
        myQuestionID = randomQID;

        rndQuestion = myQuestionHash.remove(randomQID);

        return rndQuestion;
    }

    /**
     * Retrieves a random question ID from the database.
     *
     * @return the randomly selected question ID.
     * @throws SQLException if a database access error occurs.
     */
    private int getRandomQuestionID() throws SQLException {
        int randomQID = 0;
        PreparedStatement statement = myConnection.prepareStatement("SELECT QuestionID FROM Questions ORDER BY RANDOM() LIMIT 1");
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            randomQID = resultSet.getInt("QuestionID");
        }
        return randomQID;
    }

    /**
     * Retrieves a random question ID from the database.
     *
     * @param theQuestionID A set of question IDs to random select from.
     * @return the randomly selected question ID.
     */
    private int getRandomQuestionID(Set<Integer> theQuestionID) {
        Random random = new Random();
        List<Integer> qidList = new ArrayList<>(theQuestionID);
        int randomIndex = random.nextInt(qidList.size());
        return qidList.get(randomIndex);
    }

    /**
     * Retrieves the text of the question from the database.
     *
     * @param theQuestionID theQuestionID the ID of the question.
     * @return String text of the question.
     * @throws SQLException if a database access error occurs.
     */
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

    /**
     * Retrieves the audio file path of a question from the database.
     *
     * @param theQuestionID the ID of the question.
     * @return the path to the audio file of the question.
     * @throws SQLException if a database access error occurs.
     */
    private String getQuestionAudio(int theQuestionID) throws SQLException {
        String audioPath = "Audio not found!";
        PreparedStatement statement = myConnection.prepareStatement("SELECT AudioFile FROM Questions Where QuestionID = ?");
        statement.setInt(1, theQuestionID);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet != null && resultSet.next()) {
            audioPath = resultSet.getString("AudioFile");
        }
        return audioPath;
    }

    //TODO: May throw null pointer exception fix later.
    /**
     * Retrieves the image URL of a question from the database.
     *
     * @param theQuestionID the ID of the question.
     * @return the URL of the image associated with the question.
     * @throws SQLException if a database access error occurs.
     */
    private String getQuestionImage(int theQuestionID) throws SQLException {
        String imagePath = "Image not found!";
        PreparedStatement statement = myConnection.prepareStatement("SELECT ImageURL FROM Questions WHERE QuestionID = ?");
        statement.setInt(1,theQuestionID);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet != null && resultSet.next()) {
            imagePath = resultSet.getString("ImageURL");
        }

        return imagePath;
    }

    /**
     * Retrieves the answers for a question from the database.
     *
     * @param theQuestionID the ID of the question.
     * @return an AnswerData object containing the list of answers and the index of the correct answer.
     * @throws SQLException if a database access error occurs.
     */

   private AnswerData getAnswers(int theQuestionID) throws SQLException {
        TreeMap<String, Boolean> answers = new TreeMap<>();

        PreparedStatement statement = myConnection.prepareStatement("SELECT AnswerText, IsCorrect FROM Answers WHERE QuestionID = ?");
        statement.setInt(1, theQuestionID);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            String ansText = resultSet.getString("AnswerText");
            boolean isCorrect = resultSet.getInt("IsCorrect") == 1;
            answers.put(ansText, isCorrect);

        }

        return new AnswerData(answers);
    }

    /**
     * Retrieves the question 'type' of question from the database.
     *
     * @param theQuestionID the ID of the question.
     * @return the type of the question (e.g., "Multi", "T/F", "Short", "Audio", "Image").
     * @throws SQLException if a database access error occurs.
     */
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