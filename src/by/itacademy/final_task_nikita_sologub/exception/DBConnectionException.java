package by.itacademy.final_task_nikita_sologub.exception;

import java.sql.SQLException;

public class DBConnectionException extends SQLException {
    public DBConnectionException(String reason, Throwable cause) {
        super(reason, cause);
    }
}