package by.itacademy.final_task_nikita_sologub.service.film_service;

import by.itacademy.final_task_nikita_sologub.exception.FilmDaoException;
import by.itacademy.final_task_nikita_sologub.model.film.Film;

import java.time.LocalDateTime;
import java.util.List;

public interface FilmService {
    public boolean addFilmToCinema(String filmTitle, LocalDateTime dateTime);

    public List<Film> getAllMovies();

    public List<Film> getAllMoviesToShow();

    public void decrementVacantPlaces(int filmId) throws FilmDaoException;

    public void incrementVacantPlaces(int filmId) throws FilmDaoException;

    public boolean changeFilmTitleByFilmId(String newFilmTitle, int filmId);

    public boolean changeFilmDateByFilmId(int filmId, LocalDateTime newFilmDate);

    public boolean removeFilmById(int filmId);

    public Film getFilmByFilmId(int filmId);

    public int getMaxFilmId();
}