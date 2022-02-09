package by.itacademy.final_task_nikita_sologub.dao.film_dao;

import by.itacademy.final_task_nikita_sologub.dao.AbstractJdbcDao;
import by.itacademy.final_task_nikita_sologub.exception.FilmDaoException;
import by.itacademy.final_task_nikita_sologub.exception.TicketDaoException;
import by.itacademy.final_task_nikita_sologub.model.film.Film;
import by.itacademy.final_task_nikita_sologub.model.film.FilmFactory;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static by.itacademy.final_task_nikita_sologub.dao.Constant.FILM_DATE_FIELD;
import static by.itacademy.final_task_nikita_sologub.dao.Constant.FILM_ID_FIELD;
import static by.itacademy.final_task_nikita_sologub.dao.Constant.FILM_TITLE_FIELD;
import static by.itacademy.final_task_nikita_sologub.dao.Constant.TICKET_AMOUNT;
import static by.itacademy.final_task_nikita_sologub.dao.Constant.TICKET_AMOUNT_FIELD;
import static by.itacademy.final_task_nikita_sologub.dao.film_dao.Constant.DECREMENT_TICKETS;
import static by.itacademy.final_task_nikita_sologub.dao.film_dao.Constant.DELETE_FILM;
import static by.itacademy.final_task_nikita_sologub.dao.film_dao.Constant.ERROR_MESSAGE_1;
import static by.itacademy.final_task_nikita_sologub.dao.film_dao.Constant.ERROR_MESSAGE_2;
import static by.itacademy.final_task_nikita_sologub.dao.film_dao.Constant.ERROR_MESSAGE_3;
import static by.itacademy.final_task_nikita_sologub.dao.film_dao.Constant.INCREMENT_TICKETS;
import static by.itacademy.final_task_nikita_sologub.dao.film_dao.Constant.INSERT_FILM;
import static by.itacademy.final_task_nikita_sologub.dao.film_dao.Constant.SELECT_ALL_MOVIES;
import static by.itacademy.final_task_nikita_sologub.dao.film_dao.Constant.SELECT_FILM_BY_FILM_ID;
import static by.itacademy.final_task_nikita_sologub.dao.film_dao.Constant.SELECT_MAX_FILM_ID;
import static by.itacademy.final_task_nikita_sologub.dao.film_dao.Constant.UPDATE_FILM_DATE;
import static by.itacademy.final_task_nikita_sologub.dao.film_dao.Constant.UPDATE_FILM_TITLE;
import static by.itacademy.final_task_nikita_sologub.dao.film_dao.Constant.WHERE_OWNER_ID_IS;
import static by.itacademy.final_task_nikita_sologub.dao.film_dao.Constant.WHERE_TICKETS_EXIST;

public class FilmDaoImpl extends AbstractJdbcDao implements FilmDao {
    private final FilmFactory filmFactory;

    public FilmDaoImpl(FilmFactory filmFactory) {
        this.filmFactory = filmFactory;
    }

    @Override
    public boolean addFilmToCinema(String filmTitle, LocalDateTime filmDateTime) throws FilmDaoException {
        boolean isFilmAdded;
        try {
            initSqlQuery(INSERT_FILM);
            setSqlParam(1, filmTitle);
            setSqlParam(2, Timestamp.valueOf(filmDateTime));
            setSqlParam(3, TICKET_AMOUNT);
            isFilmAdded = execute();
            closeResourcesAndConnection();
        } catch (SQLException e) {
            throw new FilmDaoException("Не удалось добавить фильм в базу данных");
        }
        return isFilmAdded;
    }

    @Override
    public List<Film> getAllMovies() throws FilmDaoException {
        return getAllMoviesBySqlQuery(SELECT_ALL_MOVIES, ERROR_MESSAGE_2);
    }

    @Override
    public List<Film> getAllMoviesToShow() throws FilmDaoException {
        return getAllMoviesBySqlQuery(SELECT_ALL_MOVIES + WHERE_TICKETS_EXIST, ERROR_MESSAGE_1);
    }

    private List<Film> getAllMoviesBySqlQuery(String sqlQuery, String errorMessage) throws FilmDaoException {
        List<Film> films;
        try {
            initSqlQuery(sqlQuery);
            executeQuery();
            films = extractFilms();
            closeResourcesAndConnection();
        } catch (SQLException e) {
            throw new FilmDaoException(errorMessage, e);
        }
        return films;
    }

    @Override
    public List<Film> getFilmsByOwnerId(int ownerId) throws FilmDaoException {
        try {
            initSqlQuery(SELECT_ALL_MOVIES + WHERE_OWNER_ID_IS);
            setSqlParam(1, ownerId);
            executeQuery();
            List<Film> films = extractFilms();
            closeResourcesAndConnection();
            return films;
        } catch (SQLException e) {
            throw new FilmDaoException(ERROR_MESSAGE_3 + ownerId, e);
        }
    }

    private List<Film> extractFilms() throws SQLException {
        List<Film> films = new ArrayList<>();
        while (hasNextRaw()) {
            films.add(extractFilm());
        }
        return films;
    }

    private Film extractFilm() throws SQLException {
        int filmId = getIntField(FILM_ID_FIELD);
        String filmTitle = getStringField(FILM_TITLE_FIELD);
        LocalDateTime time = getTimestampField(FILM_DATE_FIELD).toLocalDateTime();
        int ticketAmount = getIntField(TICKET_AMOUNT_FIELD);
        return filmFactory.createFilm(filmId, filmTitle, time, ticketAmount);
    }

    @Override
    public void decrementVacantPlaces(int filmId) throws FilmDaoException {
        try {
            initSqlQuery(DECREMENT_TICKETS);
            setSqlParam(1, filmId);
            execute();
            closeResourcesAndConnection();
        } catch (SQLException e) {
            throw new FilmDaoException("Не удалось уменьшить кол-во вакантных билетов на фильм", e);
        }
    }

    @Override
    public void incrementVacantPlaces(int filmId) throws FilmDaoException {
        try {
            initSqlQuery(INCREMENT_TICKETS);
            setSqlParam(1, filmId);
            setSqlParam(2, TICKET_AMOUNT);
            execute();
            closeResourcesAndConnection();
        } catch (SQLException e) {
            throw new FilmDaoException("Не удалось увеличить кол-во вакантных билетов на фильм", e);
        }
    }

    @Override
    public boolean changeFilmTitleByFilmId(int filmId, String newFilmTitle) throws FilmDaoException {
        boolean isTitleChanged;
        try {
            initSqlQuery(UPDATE_FILM_TITLE);
            setSqlParam(1, newFilmTitle);
            setSqlParam(2, filmId);
            isTitleChanged = executeUpdateAndGetResult();
            closeResourcesAndConnection();
        } catch (SQLException e) {
            throw new FilmDaoException("Не удалось поменять название фильма в сеансе", e);
        }
        return isTitleChanged;
    }

    @Override
    public boolean changeFilmDateByFilmId(int filmId, LocalDateTime newFilmDate) throws FilmDaoException {
        boolean isDateChanged;
        try {
            initSqlQuery(UPDATE_FILM_DATE);
            setSqlParam(1, Timestamp.valueOf(newFilmDate));
            setSqlParam(2, filmId);
            isDateChanged = executeUpdateAndGetResult();
            closeResourcesAndConnection();
        } catch (SQLException e) {
            throw new FilmDaoException("Не удалось поменять дату фильма в сеансе", e);
        }
        return isDateChanged;
    }

    @Override
    public boolean removeFilmById(int filmId) throws FilmDaoException {
        boolean isFilmRemoved;
        try {
            initSqlQuery(DELETE_FILM);
            setSqlParam(1, filmId);
            isFilmRemoved = executeUpdateAndGetResult();
            closeResourcesAndConnection();
        } catch (SQLException e) {
            throw new FilmDaoException("Не удалось удалить фильм", e);
        }
        return isFilmRemoved;
    }

    @Override
    public Film getFilmByFilmId(int filmId) throws TicketDaoException {
        Film film = null;//TODO to set special values for not Null responses
        try {
            initSqlQuery(SELECT_FILM_BY_FILM_ID);
            setSqlParam(1, filmId);
            executeQuery();
            while (hasNextRaw()) {
                String filmTitle = getStringField(FILM_TITLE_FIELD);
                LocalDateTime time = getTimestampField(FILM_DATE_FIELD).toLocalDateTime();
                int ticketAmount = getIntField(TICKET_AMOUNT_FIELD);
                film = filmFactory.createFilm(filmId, filmTitle, time, ticketAmount);
            }
            closeResourcesAndConnection();
            return film;
        } catch (SQLException e) {
            throw new TicketDaoException("Не смог взять фильм по id для требуемого билета", e);
        }
    }

    @Override
    public int getMaxFilmId() throws FilmDaoException {
        int maxFilmId = 0;
        try {
            initSqlQuery(SELECT_MAX_FILM_ID);
            executeQuery();
            while (hasNextRaw()) {
                maxFilmId = getIntField(FILM_ID_FIELD); //.getInt(1);
            }
            closeResourcesAndConnection();
        } catch (SQLException e) {
            throw new FilmDaoException("Не получилось достать следующий id фильма", e);
        }
        return maxFilmId;
    }
}