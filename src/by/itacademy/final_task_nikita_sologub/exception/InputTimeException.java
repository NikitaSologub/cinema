package by.itacademy.final_task_nikita_sologub.exception;

import java.io.IOException;

public class InputTimeException extends IOException {
    public InputTimeException(String message, Throwable cause) {
        super(message, cause);
    }
}