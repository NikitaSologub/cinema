package by.itacademy.final_task_nikita_sologub.service.ticket_service;

import by.itacademy.final_task_nikita_sologub.dao.ticket_dao.TicketDao;
import by.itacademy.final_task_nikita_sologub.exception.TicketDaoException;
import by.itacademy.final_task_nikita_sologub.logger.Log;
import by.itacademy.final_task_nikita_sologub.model.ticket.Ticket;

import java.util.Collections;
import java.util.List;

import static by.itacademy.final_task_nikita_sologub.service.Constant.FILM_NOT_EXISTS;

public class TicketServiceImpl implements TicketService {

    private TicketDao ticketDao;

    public TicketServiceImpl(TicketDao ticketDao) {
        this.ticketDao = ticketDao;
    }

    @Override
    public boolean insertTicket(final int filmId, final int ownerId, final int ticketPrice, final int placeNumber) {
        boolean isTicketInserted;
        try {
            isTicketInserted = ticketDao.insertTicket(filmId, ownerId, ticketPrice, placeNumber);
            if (isTicketInserted) {
                Log.getLogger().debug("Ticket filmId_id={} was inserted", filmId);
            } else {
                Log.getLogger().debug("Ticket filmId_id={} wasn't inserted", filmId);
            }
        } catch (TicketDaoException e) {
            Log.getLogger().error("Failed ti insert ticker", e);
            isTicketInserted = false;
        }
        return isTicketInserted;
    }

    @Override
    public boolean insertTicketsToCinema(final int filmId, final int ticketPrice) {
        boolean isTicketsInserted;
        try {
            isTicketsInserted = ticketDao.insertTicketsToCinema(filmId, ticketPrice);
            if (isTicketsInserted) {
                Log.getLogger().debug("Ticket filmId_id={} was inserted to the cinema", filmId);
            } else {
                Log.getLogger().debug("Ticket filmId_id={} wasn't inserted to the cinema", filmId);
            }
        } catch (TicketDaoException e) {
            Log.getLogger().error("Failed insert tickets to the cinema", e);
            isTicketsInserted = false;
        }
        return isTicketsInserted;
    }

    @Override
    public List<Ticket> getTicketsByOwnerId(final int ownerId) {
        try {
            Log.getLogger().debug("Trying to get ticket list bu owner where owner_id= {}", ownerId);
            return ticketDao.getTicketsByOwnerId(ownerId);
        } catch (TicketDaoException e) {
            Log.getLogger().error("Failed to get ticket list by owner id", e);
            return Collections.emptyList();
        }
    }

    @Override
    public boolean buyTicket(final int newOwnerId, final int filmId, final int placeNumber) {
        boolean isTicketPurchased;
        try {
            isTicketPurchased = ticketDao.buyTicket(newOwnerId, filmId, placeNumber);
            if (isTicketPurchased) {
                Log.getLogger().debug("User with user_id={} bought ticket by film_id ={}", newOwnerId, filmId);
            } else {
                Log.getLogger().debug("User with user_id={} could't buy ticket by film_id ={}", newOwnerId, filmId);
            }
        } catch (TicketDaoException e) {
            Log.getLogger().error("Failed to buy the ticket", e);
            isTicketPurchased = false;
        }
        return isTicketPurchased;
    }

    @Override
    public boolean returnTicketToCinema(final int ticketId) {
        boolean isTicketReturned;
        try {
            isTicketReturned = ticketDao.returnTicketToCinema(ticketId);
            if (isTicketReturned) {
                Log.getLogger().debug("ticket returned to the cinema by ticket=id", ticketId);
            } else {
                Log.getLogger().debug("failed to return ticket to the cinema by ticket=id", ticketId);
            }
        } catch (TicketDaoException e) {
            Log.getLogger().error("Failed to return ticket to the cinema", e);
            isTicketReturned = false;
        }
        return isTicketReturned;
    }

    @Override
    public int getFilmId(final int ticketId) {
        int filmId;
        try {
            filmId = ticketDao.getFilmId(ticketId);
            if (filmId == FILM_NOT_EXISTS) {
                Log.getLogger().debug("У билета ticket_id={} несуществующий фильм", ticketId);
            } else {
                Log.getLogger().debug("У билета ticket_id={} фильм c film_id={}", ticketId, filmId);
            }
        } catch (TicketDaoException e) {
            filmId = FILM_NOT_EXISTS;
            Log.getLogger().error("У билета ticket_id=" + ticketId + "} фильм c film_id=0", e);
        }
        return filmId;
    }

    @Override
    public boolean removeTicketsByFilmId(final int filmId) {
        boolean isTickedRemoved;
        try {
            isTickedRemoved = ticketDao.removeTicketsByFilmId(filmId);
            if (isTickedRemoved) {
                Log.getLogger().info("Все билеты по film_id={} успешно удалены", filmId);
            } else {
                Log.getLogger().info("Билеты по film_id={} не удалены", filmId);
            }
        } catch (TicketDaoException e) {
            Log.getLogger().error("Ошибка удаления билетов по film_id=" + filmId, e);
            return false;
        }
        return isTickedRemoved;
    }
}