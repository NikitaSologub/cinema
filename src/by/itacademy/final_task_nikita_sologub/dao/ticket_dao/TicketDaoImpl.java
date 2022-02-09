package by.itacademy.final_task_nikita_sologub.dao.ticket_dao;

import by.itacademy.final_task_nikita_sologub.dao.AbstractJdbcDao;
import by.itacademy.final_task_nikita_sologub.exception.TicketDaoException;
import by.itacademy.final_task_nikita_sologub.model.film.Film;
import by.itacademy.final_task_nikita_sologub.model.ticket.Ticket;
import by.itacademy.final_task_nikita_sologub.model.ticket.TicketFactory;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static by.itacademy.final_task_nikita_sologub.dao.Constant.CINEMA_ID;
import static by.itacademy.final_task_nikita_sologub.dao.Constant.FILM_DATE_FIELD;
import static by.itacademy.final_task_nikita_sologub.dao.Constant.FILM_ID_FIELD;
import static by.itacademy.final_task_nikita_sologub.dao.Constant.FILM_TITLE_FIELD;
import static by.itacademy.final_task_nikita_sologub.dao.Constant.PLACE_NUMBER_FIELD;
import static by.itacademy.final_task_nikita_sologub.dao.Constant.TICKET_AMOUNT;
import static by.itacademy.final_task_nikita_sologub.dao.Constant.TICKET_AMOUNT_FIELD;
import static by.itacademy.final_task_nikita_sologub.dao.Constant.TICKET_ID_FIELD;
import static by.itacademy.final_task_nikita_sologub.dao.Constant.TICKET_PRICE_FIELD;
import static by.itacademy.final_task_nikita_sologub.dao.ticket_dao.Constant.DELETE_TICKETS;
import static by.itacademy.final_task_nikita_sologub.dao.ticket_dao.Constant.INSERT_TICKET;
import static by.itacademy.final_task_nikita_sologub.dao.ticket_dao.Constant.SELECT_FILM_ID;
import static by.itacademy.final_task_nikita_sologub.dao.ticket_dao.Constant.SELECT_TICKETS_BY_OWNER_ID;
import static by.itacademy.final_task_nikita_sologub.dao.ticket_dao.Constant.UPDATE_TICKET_OWNER;
import static by.itacademy.final_task_nikita_sologub.dao.ticket_dao.Constant.UPDATE_TICKET_OWNER_TO_CINEMA;

public class TicketDaoImpl extends AbstractJdbcDao implements TicketDao {
    private final TicketFactory ticketFactory;

    public TicketDaoImpl(TicketFactory factory) {
        this.ticketFactory = factory;
    }

    @Override
    public boolean insertTicket(int filmId, int ownerId, int ticketPrice, int placeNumber) throws TicketDaoException {
        boolean isTicketInserted;
        try {
            initSqlQuery(INSERT_TICKET);
            setSqlParam(1, filmId);
            setSqlParam(2, ticketPrice);
            setSqlParam(3, ownerId);
            setSqlParam(4, placeNumber);
            isTicketInserted = executeUpdateAndGetResult();
            closeResourcesAndConnection();
        } catch (SQLException e) {
            throw new TicketDaoException("Не удалось добавить билет", e);
        }
        return isTicketInserted;
    }

    @Override
    public boolean insertTicketsToCinema(int filmId, int ticketPrice) throws TicketDaoException {
        boolean isTicketInsertedToCinema = false;
        try {
            initSqlQuery(INSERT_TICKET);
            setSqlParam(1, filmId);
            setSqlParam(2, ticketPrice);
            setSqlParam(3, CINEMA_ID);
            for (int currentPlace = 1; currentPlace <= TICKET_AMOUNT; currentPlace++) {
                setSqlParam(4, currentPlace);
                addBatch();
                if (currentPlace == TICKET_AMOUNT) {
                    int[] resultCodes = executeBatch();
                    isTicketInsertedToCinema = Arrays.stream(resultCodes)
                            .allMatch(num -> num >= 0);
                }
            }
            closeResourcesAndConnection();
        } catch (SQLException e) {
            throw new TicketDaoException("Не удалось добавить билеты в кинотеатр", e);
        }
        return isTicketInsertedToCinema;
    }

    @Override
    public List<Ticket> getTicketsByOwnerId(int ownerId) throws TicketDaoException {
        try {
            List<Ticket> tickets = new ArrayList<>();
            initSqlQuery(SELECT_TICKETS_BY_OWNER_ID);
            setSqlParam(1, ownerId);
            executeQuery();
            while (hasNextRaw()) {
                Ticket ticket = extractTicket();
                tickets.add(ticket);
            }
            closeResourcesAndConnection();
            return tickets;
        } catch (SQLException e) {
            throw new TicketDaoException("Не удалось найти билет по id владельца", e);
        }
    }

    private Ticket extractTicket() throws SQLException {
        Ticket ticket = ticketFactory.createTicket();
        ticket.setId(getIntField(TICKET_ID_FIELD));
        ticket.setPrice(getIntField(TICKET_PRICE_FIELD));
        ticket.setPlace(getIntField(PLACE_NUMBER_FIELD));
        ticket.setFilm(extractFilm());
        return ticket;
    }

    private Film extractFilm() throws SQLException {
        int id = getIntField(FILM_ID_FIELD);
        String title = getStringField(FILM_TITLE_FIELD);
        LocalDateTime time = getTimestampField(FILM_DATE_FIELD).toLocalDateTime();
        int ticketsAmount = getIntField(TICKET_AMOUNT_FIELD);
        return new Film(id, title, time, ticketsAmount);
    }

    @Override
    public boolean buyTicket(int newOwnerId, int filmId, int placeNumber) throws TicketDaoException {
        boolean isTicketPurchased;
        try {
            initSqlQuery(UPDATE_TICKET_OWNER);
            setSqlParam(1, newOwnerId);
            setSqlParam(2, filmId);
            setSqlParam(3, placeNumber);
            isTicketPurchased = executeUpdateAndGetResult();
            closeResourcesAndConnection();
        } catch (SQLException e) {
            throw new TicketDaoException("Не удалось поменять id владельца билета", e);
        }
        return isTicketPurchased;
    }

    @Override
    public boolean returnTicketToCinema(int ticketId) throws TicketDaoException {
        try {
            initSqlQuery(UPDATE_TICKET_OWNER_TO_CINEMA);
            setSqlParam(1, ticketId);
            execute();
            closeResourcesAndConnection();
            return true;//TODO something wrong with true/false value to return. I must invented this using executeUpdateAndGetResult() case
        } catch (SQLException e) {
            throw new TicketDaoException("Не удалось вернуть билет в кассу", e);
        }
    }

    @Override
    public int getFilmId(int ticketId) throws TicketDaoException {
        int filmId = 0;
        try {
            initSqlQuery(SELECT_FILM_ID);
            setSqlParam(1, ticketId);
            executeQuery();
            while (hasNextRaw()) {
                filmId = getIntField(FILM_ID_FIELD);
            }
            closeResourcesAndConnection();
        } catch (SQLException e) {
            throw new TicketDaoException("Не удалось взять значение film_Id по полю ticket_id", e);
        }
        return filmId;
    }

    @Override
    public boolean removeTicketsByFilmId(int filmId) throws TicketDaoException {
        boolean isTicketsDeleted;
        try {
            initSqlQuery(DELETE_TICKETS);
            setSqlParam(1, filmId);
            isTicketsDeleted = executeUpdateAndGetResult();
            closeResourcesAndConnection();
        } catch (SQLException e) {
            throw new TicketDaoException("Не удалось удалить все билеты по заданному filmId", e);
        }
        return isTicketsDeleted;
    }
}