package by.itacademy.final_task_nikita_sologub.dao.ticket_dao;

import by.itacademy.final_task_nikita_sologub.exception.TicketDaoException;
import by.itacademy.final_task_nikita_sologub.model.ticket.Ticket;

import java.util.List;

public interface TicketDao {
    public boolean insertTicket(int filmId, int ownerId, int ticketPrice, int placeNumber) throws TicketDaoException;

    public boolean insertTicketsToCinema(int filmId, int ticketPrice) throws TicketDaoException;

    public List<Ticket> getTicketsByOwnerId(int ownerId) throws TicketDaoException;

    public boolean buyTicket(int newOwnerId, int filmId, int placeNumber) throws TicketDaoException;

    public boolean returnTicketToCinema(int ticketId) throws TicketDaoException;

    public int getFilmId(int ticketId) throws TicketDaoException;

    public boolean removeTicketsByFilmId(int filmId) throws TicketDaoException;
}