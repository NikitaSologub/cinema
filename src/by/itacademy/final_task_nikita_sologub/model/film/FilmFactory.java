package by.itacademy.final_task_nikita_sologub.model.film;

import java.time.LocalDateTime;

public interface FilmFactory {
    Film createFilm(int id, String title, LocalDateTime time, int ticketAmount);
}