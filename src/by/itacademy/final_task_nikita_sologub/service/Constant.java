package by.itacademy.final_task_nikita_sologub.service;

import by.itacademy.final_task_nikita_sologub.model.film.Film;
import by.itacademy.final_task_nikita_sologub.model.ticket.Ticket;
import by.itacademy.final_task_nikita_sologub.model.user.User;

import java.time.LocalDateTime;

public class Constant {
    public static final Film INVALID_FILM;
    public static final Ticket INVALID_TICKET;
    public static final User INVALID_USER;
    public static final User USER_NOT_EXIST;
    public static final int FILM_NOT_EXISTS = 0;

    static {
        INVALID_FILM = new Film(0, "INVALID FILM", LocalDateTime.now(), 0);
        INVALID_TICKET = new Ticket(0, INVALID_FILM, 0, 0, 0);
        INVALID_USER = new User(0, "INVALID USER", null);
        USER_NOT_EXIST = new User(0, "USER NOT EXIST", null);
    }
}