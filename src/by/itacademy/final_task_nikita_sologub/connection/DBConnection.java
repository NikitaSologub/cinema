package by.itacademy.final_task_nikita_sologub.connection;

import by.itacademy.final_task_nikita_sologub.exception.DBConnectionException;
import by.itacademy.final_task_nikita_sologub.logger.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class DBConnection {
    private static final String SUCCESS_MESSAGE_1 = "Database connection successfully established";
    private static final String SUCCESS_MESSAGE_2 = "Database connection is closed successfully";
    private static final String ERROR_MESSAGE_1 = "JDBC Driver class is not found";
    public static final String ERROR_MESSAGE_2 = "Unsuccessful connection closure";
    private static final String ERROR_MESSAGE_3 = "Unsuccessful connection opening";
    private static final String URL = "jdbc:mysql://localhost/cinema";
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static Connection connection = null;

    private DBConnection() {
    }

    public static Connection getConnection() throws DBConnectionException {
        if (connection == null) {
            try {
                Class.forName(DRIVER);
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                Log.getLogger().debug(SUCCESS_MESSAGE_1);
            } catch (ClassNotFoundException e) {
                Log.getLogger().debug(ERROR_MESSAGE_1);
                throw new DBConnectionException(ERROR_MESSAGE_1, e);
            } catch (SQLException e) {
                Log.getLogger().error(ERROR_MESSAGE_3);
                throw new DBConnectionException(ERROR_MESSAGE_3, e);
            }
        }
        return connection;
    }

    public static void closeConnection() throws DBConnectionException {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                Log.getLogger().debug(SUCCESS_MESSAGE_2);
            } catch (SQLException e) {
                Log.getLogger().error(ERROR_MESSAGE_2);
                throw new DBConnectionException(ERROR_MESSAGE_2, e);
            }
        }
    }
}