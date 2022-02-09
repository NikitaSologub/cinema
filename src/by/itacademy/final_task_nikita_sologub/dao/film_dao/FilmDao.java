package by.itacademy.final_task_nikita_sologub.dao.film_dao;

import by.itacademy.final_task_nikita_sologub.exception.FilmDaoException;
import by.itacademy.final_task_nikita_sologub.exception.TicketDaoException;
import by.itacademy.final_task_nikita_sologub.model.film.Film;

import java.time.LocalDateTime;
import java.util.List;

public interface FilmDao {
    public boolean addFilmToCinema(String filmTitle, LocalDateTime dateTime) throws FilmDaoException;

    public List<Film> getAllMovies() throws FilmDaoException;

    public List<Film> getAllMoviesToShow() throws FilmDaoException;

    public void incrementVacantPlaces(int filmId) throws FilmDaoException;

    public void decrementVacantPlaces(int filmId) throws FilmDaoException;

    public List<Film> getFilmsByOwnerId(int ownerId) throws FilmDaoException;

    public boolean changeFilmTitleByFilmId(int filmId, String newFilmTitle) throws FilmDaoException;

    public boolean changeFilmDateByFilmId(int filmId, LocalDateTime newFilmDate) throws FilmDaoException;

    public boolean removeFilmById(int filmId) throws FilmDaoException;

    public Film getFilmByFilmId(int filmId) throws TicketDaoException;

    public int getMaxFilmId() throws FilmDaoException;
}