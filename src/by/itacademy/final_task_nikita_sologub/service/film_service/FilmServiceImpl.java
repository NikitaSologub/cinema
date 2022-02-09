package by.itacademy.final_task_nikita_sologub.service.film_service;

import by.itacademy.final_task_nikita_sologub.dao.film_dao.FilmDao;
import by.itacademy.final_task_nikita_sologub.exception.FilmDaoException;
import by.itacademy.final_task_nikita_sologub.exception.TicketDaoException;
import by.itacademy.final_task_nikita_sologub.logger.Log;
import by.itacademy.final_task_nikita_sologub.model.film.Film;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static by.itacademy.final_task_nikita_sologub.service.Constant.INVALID_FILM;

public class FilmServiceImpl implements FilmService {
    private final FilmDao filmDao;

    public FilmServiceImpl(FilmDao filmDao) {
        this.filmDao = filmDao;
    }

    @Override
    public boolean addFilmToCinema(String filmTitle, LocalDateTime dateTime) {
        boolean isFilmAdded;
        try {
            Log.getLogger().debug("Trying to add film in the cinema");
            isFilmAdded = filmDao.addFilmToCinema(filmTitle, dateTime);
        } catch (FilmDaoException e) {
            Log.getLogger().error("film not added to the cinema", e);
            isFilmAdded = false;
        }
        return isFilmAdded;
    }

    @Override
    public List<Film> getAllMovies() {
        try {
            Log.getLogger().debug("Trying to get List<Film> from the cinema");
            return filmDao.getAllMovies();
        } catch (FilmDaoException e) {
            Log.getLogger().error("Not success to get all movies from the cinema", e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<Film> getAllMoviesToShow() {
        try {
            Log.getLogger().debug("Trying to get List<Film> from the cinema to show");
            return filmDao.getAllMoviesToShow();
        } catch (FilmDaoException e) {
            Log.getLogger().error("Not success to get all movies from the cinema to show", e);
            return Collections.emptyList();
        }
    }

    @Override
    public void decrementVacantPlaces(int filmId) throws FilmDaoException {
        Log.getLogger().debug("Trying decrement vacant places by film_id= {}", filmId);
        filmDao.decrementVacantPlaces(filmId);
    }

    @Override
    public void incrementVacantPlaces(int filmId) throws FilmDaoException {
        Log.getLogger().debug("Trying increment vacant places by film_id= {}", filmId);
        filmDao.incrementVacantPlaces(filmId);
    }

    @Override
    public boolean changeFilmTitleByFilmId(String newFilmTitle, int filmId) {
        boolean isFilmTitleChanged;
        try {
            Log.getLogger().debug("Trying to change film name by film_id= {} on new name= {}", filmId, newFilmTitle);
            isFilmTitleChanged = filmDao.changeFilmTitleByFilmId(filmId, newFilmTitle);
        } catch (FilmDaoException e) {
            Log.getLogger().error("Changing film title error", e);
            isFilmTitleChanged = false;
        }
        return isFilmTitleChanged;
    }

    @Override
    public boolean changeFilmDateByFilmId(int filmId, LocalDateTime newFilmDate) {
        boolean isFilmDateChanged;
        try {
            Log.getLogger().debug("Trying to change film date by film_id= {} on new date= {}", filmId, newFilmDate);
            isFilmDateChanged = filmDao.changeFilmDateByFilmId(filmId, newFilmDate);
        } catch (FilmDaoException e) {
            Log.getLogger().error("Changing film date error", e);
            isFilmDateChanged = false;
        }
        return isFilmDateChanged;
    }

    @Override
    public boolean removeFilmById(int filmId) {
        boolean isFilmRemoved;
        try {
            isFilmRemoved = filmDao.removeFilmById(filmId);
            if (isFilmRemoved) {
                Log.getLogger().debug("Film by film_id= {} was removed from the cinema", filmId);
            } else {
                Log.getLogger().debug("Film by film_id= {} was not removed from the cinema", filmId);
            }
        } catch (FilmDaoException e) {
            Log.getLogger().error("Error to remove film from the cinema", e);
            return false;
        }
        return isFilmRemoved;
    }

    @Override
    public Film getFilmByFilmId(int filmId) {
        try {
            Log.getLogger().debug("Trying to get film by film+id = {} from the cinema", filmId);
            return getNotNullFilm(filmDao.getFilmByFilmId(filmId));
        } catch (TicketDaoException e) {
            Log.getLogger().debug("Error getting film by id from the cinema", e);
            return INVALID_FILM;
        }
    }

    private Film getNotNullFilm(Film film) {
        if (film == null) {
            film = INVALID_FILM;
        }
        return film;
    }

    @Override
    public int getMaxFilmId() {
        int maxFilmId = 0;
        try {
            maxFilmId = filmDao.getMaxFilmId();
            Log.getLogger().debug("Returning max film_id= {}", maxFilmId);
        } catch (FilmDaoException e) {
            Log.getLogger().error("Error returning max film_id", e);
            Log.getLogger().debug("Returning max film_id=0");
        }
        return maxFilmId;
    }
}