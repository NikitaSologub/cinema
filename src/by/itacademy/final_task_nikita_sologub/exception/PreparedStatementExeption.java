package by.itacademy.final_task_nikita_sologub.exception;

import java.sql.SQLException;

public class PreparedStatementExeption extends SQLException {
    public PreparedStatementExeption(String reason, Throwable cause) {
        super(reason, cause);
    }
}