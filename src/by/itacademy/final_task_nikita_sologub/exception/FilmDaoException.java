package by.itacademy.final_task_nikita_sologub.exception;

public class FilmDaoException extends Exception {
    public FilmDaoException(String message) {
        super(message);
    }

    public FilmDaoException(String message, Throwable cause) {
        super(message, cause);
    }
}