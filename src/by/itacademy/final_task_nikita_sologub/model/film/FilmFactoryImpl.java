package by.itacademy.final_task_nikita_sologub.model.film;

import java.time.LocalDateTime;

public class FilmFactoryImpl implements FilmFactory {
    @Override
    public Film createFilm(int id, String title, LocalDateTime time, int ticketAmount) {
        return new Film(id, title, time, ticketAmount);
    }
}