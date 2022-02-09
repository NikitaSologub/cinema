package by.itacademy.final_task_nikita_sologub.dao.film_dao;

class Constant {
    static final String ERROR_MESSAGE_1 = "Не получилось достать все фильмы на которые еще есть билеты";
    static final String ERROR_MESSAGE_2 = "Не получилось достать все фильмы";
    static final String ERROR_MESSAGE_3 = "Не удалось взять фильмы которые купил пользователь с ID № ";

    static final String INSERT_FILM = "INSERT INTO film (film_title,film_date,ticket_amount) VALUES (?,?,?)";
    static final String SELECT_ALL_MOVIES = "SELECT film_id,film_title,film_date,ticket_amount FROM film";
    static final String WHERE_TICKETS_EXIST = " WHERE ticket_amount > 0";
    static final String WHERE_OWNER_ID_IS = " WHERE owner_id=?";
    static final String DECREMENT_TICKETS = "UPDATE film SET ticket_amount = ticket_amount -1 WHERE film_id=? AND ticket_amount >0";
    static final String INCREMENT_TICKETS = "UPDATE film SET ticket_amount = ticket_amount +1 WHERE film_id=? AND ticket_amount <?";
    static final String UPDATE_FILM_TITLE = "UPDATE film SET film_title=? WHERE film_id=?";
    static final String UPDATE_FILM_DATE = "UPDATE film SET film_date=? WHERE film_id=?";
    static final String DELETE_FILM = "DELETE FROM film WHERE film_id=?";
    static final String SELECT_FILM_BY_FILM_ID = "SELECT * FROM film WHERE film_id=?";
    static final String SELECT_MAX_FILM_ID = "SELECT MAX(film_id) FROM film";
}