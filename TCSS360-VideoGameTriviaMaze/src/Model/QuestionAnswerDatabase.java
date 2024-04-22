package Model;

import java.beans.JavaBean;
import java.lang.module.Configuration;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

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
    private static final String JDBC_URL = "jdbc:sqlite:" + System.getProperty("database.location");

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
     * @return
     */
    public Connection getConnection() {
        return myConnection;
    }
}
