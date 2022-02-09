package by.itacademy.final_task_nikita_sologub.dao;

import by.itacademy.final_task_nikita_sologub.connection.DBConnection;
import by.itacademy.final_task_nikita_sologub.exception.DBConnectionException;
import by.itacademy.final_task_nikita_sologub.exception.PreparedStatementExeption;
import by.itacademy.final_task_nikita_sologub.exception.ResultSetExeption;
import by.itacademy.final_task_nikita_sologub.logger.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public abstract class AbstractJdbcDao {
    private static final String RESULT_SET_CLOSURE_SUCCESS_MESSAGE = "Result set is closed successfully";
    private static final String RESULT_SET_CLOSURE_ERROR_MESSAGE = "Result set closure failing";
    private static final String RESULT_SET_ESTABLISH_SUCCESS_MESSAGE = "Result set is successfully established";
    private static final String RESULT_SET_ESTABLISH_ERROR_MESSAGE = "Result set establish failing";
    private static final String PREPARED_STATEMENT_CLOSURE_SUCCESS_MESSAGE = "Prepared statement is closed successfully";
    private static final String PREPARED_STATEMENT_CLOSURE_ERROR_MESSAGE = "Prepared statement closure failing";
    private static final String PREPARED_STATEMENT_ESTABLISH_SUCCESS_MESSAGE = "Prepared statement is successfully established";
    private static final String PREPARED_STATEMENT_ESTABLISH_ERROR_MESSAGE = "Prepared statement establish failing";
    private static final String EXECUTE_UPDATE_IS_FAILED = "execute update is failed";
    private static final String COULDNT_SET_PARAMETER = "Couldn't set parameter";
    private static final String PARAMETER_INCLUDED_SUCCESSFULLY_ON_POSITION = "Parameter {} included successfully on position № {}";
    private static final String RESULT_OF_EXECUTE_UPDATE_IS_LINES_CHANGED = "result of execute update is {} lines changed";
    private static final String COULDNT_GET_FIELD = "Couldn't get field";
    private static final String TRYING_GET_FIELD_FROM_RESULT_SET = "Trying get field {} from result set";
    private Connection connection;
    private PreparedStatement statement;
    private ResultSet resultSet;

    protected void initSqlQuery(String sql) throws DBConnectionException, PreparedStatementExeption {
        connection = DBConnection.getConnection();
        initSQL(sql);
    }

    private void initSQL(String sqlQuery) throws PreparedStatementExeption {
        try {
            statement = connection.prepareStatement(sqlQuery);
            Log.getLogger().debug(PREPARED_STATEMENT_ESTABLISH_SUCCESS_MESSAGE);
        } catch (SQLException e) {
            Log.getLogger().debug(PREPARED_STATEMENT_ESTABLISH_ERROR_MESSAGE);
            throw new PreparedStatementExeption(PREPARED_STATEMENT_ESTABLISH_ERROR_MESSAGE, e);
        }
    }

    protected void setSqlParam(int num, String param) throws SQLException {
        try {
            statement.setString(num, param);
            Log.getLogger().debug(PARAMETER_INCLUDED_SUCCESSFULLY_ON_POSITION, param, num);
        } catch (SQLException e) {
            Log.getLogger().error(COULDNT_SET_PARAMETER, e);
            throw new SQLException(COULDNT_SET_PARAMETER, e);
        }
    }

    protected void setSqlParam(int num, int param) throws SQLException {
        try {
            statement.setInt(num, param);
            Log.getLogger().debug(PARAMETER_INCLUDED_SUCCESSFULLY_ON_POSITION, param, num);
        } catch (SQLException e) {
            Log.getLogger().error(COULDNT_SET_PARAMETER, e);
            throw new SQLException(COULDNT_SET_PARAMETER, e);
        }
    }

    protected void setSqlParam(int num, Timestamp param) throws SQLException {
        try {
            statement.setTimestamp(num, param);
            Log.getLogger().debug(PARAMETER_INCLUDED_SUCCESSFULLY_ON_POSITION, param, num);
        } catch (SQLException e) {
            Log.getLogger().error(COULDNT_SET_PARAMETER, e);
            throw new SQLException(COULDNT_SET_PARAMETER, e);
        }
    }

    protected boolean hasNextRaw() throws SQLException {
        return resultSet.next();
    }

    protected void executeQuery() throws SQLException {
        try {
            resultSet = statement.executeQuery();
            Log.getLogger().debug(RESULT_SET_ESTABLISH_SUCCESS_MESSAGE);
        } catch (SQLException e) {
            Log.getLogger().error(RESULT_SET_ESTABLISH_ERROR_MESSAGE, e);
            throw new SQLException(RESULT_SET_ESTABLISH_ERROR_MESSAGE, e);
        }
    }

    protected boolean executeUpdateAndGetResult() throws SQLException {
        boolean result;
        try {
            result = statement.executeUpdate() > 0;
            Log.getLogger().debug(RESULT_OF_EXECUTE_UPDATE_IS_LINES_CHANGED, result);
        } catch (SQLException e) {
            Log.getLogger().error(EXECUTE_UPDATE_IS_FAILED, e);
            throw new SQLException(EXECUTE_UPDATE_IS_FAILED, e);
        }
        return result;
    }

    protected boolean execute() throws SQLException {
        boolean result;
        try {
            result = statement.execute();
            Log.getLogger().debug(RESULT_OF_EXECUTE_UPDATE_IS_LINES_CHANGED, result);
        } catch (SQLException e) {
            Log.getLogger().error(EXECUTE_UPDATE_IS_FAILED, e);
            throw new SQLException(EXECUTE_UPDATE_IS_FAILED, e);
        }
        return result;
    }

    protected void addBatch() throws SQLException {
        statement.addBatch();
    }

    protected int[] executeBatch() throws SQLException {
        return statement.executeBatch();
    }

    protected int getIntField(String field) throws SQLException {
        try {
            Log.getLogger().debug(TRYING_GET_FIELD_FROM_RESULT_SET, field);
            return resultSet.getInt(field);
        } catch (SQLException e) {
            Log.getLogger().error(COULDNT_GET_FIELD, e);
            throw new SQLException(COULDNT_GET_FIELD, e);
        }
    }

    protected Timestamp getTimestampField(String field) throws SQLException {
        try {
            Log.getLogger().debug(TRYING_GET_FIELD_FROM_RESULT_SET, field);
            return resultSet.getTimestamp(field);
        } catch (SQLException e) {
            Log.getLogger().error(COULDNT_GET_FIELD, e);
            throw new SQLException(COULDNT_GET_FIELD, e);
        }
    }

    protected String getStringField(String field) throws SQLException {
        try {
            Log.getLogger().debug(TRYING_GET_FIELD_FROM_RESULT_SET, field);
            return resultSet.getString(field);
        } catch (SQLException e) {
            Log.getLogger().error(COULDNT_GET_FIELD, e);
            throw new SQLException(COULDNT_GET_FIELD, e);
        }
    }

    protected void closeResourcesAndConnection() throws SQLException {
        closeResultSet();
        closePreparedStatement();
        closeConnection();
    }

    private void closeResultSet() throws ResultSetExeption {
        if (resultSet != null) {
            try {
                resultSet.close();
                resultSet = null;
                Log.getLogger().debug(RESULT_SET_CLOSURE_SUCCESS_MESSAGE);
            } catch (SQLException e) {
                Log.getLogger().debug(RESULT_SET_CLOSURE_ERROR_MESSAGE);
                throw new ResultSetExeption(RESULT_SET_CLOSURE_ERROR_MESSAGE, e);
            }
        }
    }

    private void closePreparedStatement() throws PreparedStatementExeption {
        if (statement != null) {
            try {
                statement.close();
                statement = null;
                Log.getLogger().debug(PREPARED_STATEMENT_CLOSURE_SUCCESS_MESSAGE);
            } catch (SQLException e) {
                Log.getLogger().debug(PREPARED_STATEMENT_CLOSURE_ERROR_MESSAGE);
                throw new PreparedStatementExeption(PREPARED_STATEMENT_CLOSURE_ERROR_MESSAGE, e);
            }
        }
    }

    private void closeConnection() throws DBConnectionException {
        if (connection != null) {
            DBConnection.closeConnection();
            connection = null;
        }
    }
}