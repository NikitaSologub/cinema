package by.itacademy.final_task_nikita_sologub.exception;

import java.sql.SQLException;

public class ResultSetExeption extends SQLException {
    public ResultSetExeption(String reason, Throwable cause) {
        super(reason, cause);
    }
}