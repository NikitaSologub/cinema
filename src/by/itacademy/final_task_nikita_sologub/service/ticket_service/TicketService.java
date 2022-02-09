package by.itacademy.final_task_nikita_sologub.service.ticket_service;

import by.itacademy.final_task_nikita_sologub.exception.TicketDaoException;
import by.itacademy.final_task_nikita_sologub.model.ticket.Ticket;

import java.util.List;

public interface TicketService {
    public boolean insertTicket(int filmId, int ownerId, int ticketPrice, int placeNumber);

    public boolean insertTicketsToCinema(int filmId, int ticketPrice);

    public List<Ticket> getTicketsByOwnerId(int ownerId);

    public boolean buyTicket(int newOwnerId, int filmId, int placeNumber);

    public boolean returnTicketToCinema(int ticketId);

    public int getFilmId(int ticketId) throws TicketDaoException;

    public boolean removeTicketsByFilmId(int filmId);
}