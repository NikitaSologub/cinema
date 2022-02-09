package by.itacademy.final_task_nikita_sologub.controller.printer;

import by.itacademy.final_task_nikita_sologub.model.film.Film;

import java.util.List;

public interface FilmFormatter {
    String getFormattedView(final List<Film> films);
}