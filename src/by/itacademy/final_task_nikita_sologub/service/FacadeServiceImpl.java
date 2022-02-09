package by.itacademy.final_task_nikita_sologub.service;

import by.itacademy.final_task_nikita_sologub.date.DateFormatter;
import by.itacademy.final_task_nikita_sologub.exception.FilmDaoException;
import by.itacademy.final_task_nikita_sologub.exception.TicketDaoException;
import by.itacademy.final_task_nikita_sologub.logger.Log;
import by.itacademy.final_task_nikita_sologub.model.ticket.Ticket;
import by.itacademy.final_task_nikita_sologub.model.user.User;
import by.itacademy.final_task_nikita_sologub.service.film_service.FilmService;
import by.itacademy.final_task_nikita_sologub.service.ticket_service.TicketService;
import by.itacademy.final_task_nikita_sologub.service.user_service.UserService;

import java.time.LocalDateTime;
import java.util.List;

public class FacadeServiceImpl implements FacadeService {
    private final TicketService ticketService;
    private final FilmService filmService;
    private final UserService userService;

    public FacadeServiceImpl(TicketService ticketService, FilmService filmService, UserService userService) {
        this.ticketService = ticketService;
        this.filmService = filmService;
        this.userService = userService;
    }

    @Override
    public boolean buyTicket(int newOwnerId, int filmId, int placeNumber) {
        boolean isTicketBought;
        try {
            isTicketBought = buyTicketAndDecrementVacantPlaces(newOwnerId, filmId, placeNumber);
        } catch (FilmDaoException e) {
            Log.getLogger().error("user could not to buy a ticket", e);
            isTicketBought = false;
        }
        return isTicketBought;
    }

    private boolean buyTicketAndDecrementVacantPlaces(int newOwnerId, int filmId, int placeNumber) throws FilmDaoException {
        boolean isTicketBought = ticketService.buyTicket(newOwnerId, filmId, placeNumber);
        if (isTicketBought) {
            filmService.decrementVacantPlaces(filmId);
            Log.getLogger().info("user bought a ticket on the film_id{} and place_id{}", filmId, placeNumber);
        } else {
            Log.getLogger().info("user could not to buy a ticket on the film_id{} and place_id{}", filmId, placeNumber);
        }
        return isTicketBought;
    }

    @Override
    public boolean returnTicketToCinemaByTicketId(int ticketId) {
        boolean isTicketReturned;
        try {
            isTicketReturned = returnTicketToCinemaAndIncrementVacantPlaces(ticketId);
            Log.getLogger().info("user returned a ticket to the cinema the ticket_id={}", ticketId);
        } catch (FilmDaoException | TicketDaoException e) {
            Log.getLogger().error("user could not to return a ticket to the cinema", e);
            isTicketReturned = false;
        }
        return isTicketReturned;
    }

    private boolean returnTicketToCinemaAndIncrementVacantPlaces(int ticketId) throws TicketDaoException, FilmDaoException {
        int filmId = ticketService.getFilmId(ticketId);
        if (ticketService.returnTicketToCinema(ticketId)) {
            filmService.incrementVacantPlaces(filmId);
            Log.getLogger().debug("Успешно вернули билет ticketId={} в кинотеатр", ticketId);
            return true;
        } else {
            Log.getLogger().debug("Не удалось вернуть билет ticketId={} в кинотеатр", ticketId);
            return false;
        }
    }

    @Override
    public boolean changeUserLogin(String userLogin, String newLogin) {
        boolean isLoginChanged = userService.updateLogin(userLogin, newLogin);
        if (isLoginChanged) {
            Log.getLogger().info("Логин пользователя={} изменен на новый={}", userLogin, newLogin);
        } else {
            Log.getLogger().info("Логин пользователя={} не был изменен", userLogin);
        }
        return isLoginChanged;
    }

    @Override
    public boolean changeFilmTitleByFilmId(String newFilmTitle, int filmId) {
        boolean isTitleChanged = filmService.changeFilmTitleByFilmId(newFilmTitle, filmId);
        if (isTitleChanged) {
            Log.getLogger().info("Название фильма film_id={} изменено на {}", filmId, newFilmTitle);
        } else {
            Log.getLogger().info("Название фильма film_id={} не изменено", filmId);
        }
        return isTitleChanged;
    }

    @Override
    public boolean changeFilmDateByFilmId(int filmId, LocalDateTime newFilmDate) {
        boolean isFilmDateChanged = filmService.changeFilmDateByFilmId(filmId, newFilmDate);
        if (isFilmDateChanged) {
            Log.getLogger().info("Изменена дата film_id={} новая дата={}", filmId, DateFormatter.getFormattedDate(newFilmDate));
        } else {
            Log.getLogger().info("Дата film_id={} не изменена", filmId);
        }
        return isFilmDateChanged;
    }

    @Override
    public boolean removeFilmAndAllTicketsForItByFilmId(int filmId) {
        boolean isFilmDeleted = filmService.removeFilmById(filmId);
        if (isFilmDeleted) {
            isFilmDeleted = ticketService.removeTicketsByFilmId(filmId);
        }
        if (isFilmDeleted) {
            Log.getLogger().info("film by film_id={} was removed", filmId);
        } else {
            Log.getLogger().info("film by film_id={} was not removed", filmId);
        }
        return isFilmDeleted;
    }

    @Override
    public boolean removeUserAndReturnHisTicketsToCinema(String userLogin) {
        boolean isUserRemovedAndHisTicketsReturned = false;
        User user = userService.getUser(userLogin);
        if (user != Constant.INVALID_USER) {
            returnAllUserTicketsToCinemaByUserId(user.getId());
            isUserRemovedAndHisTicketsReturned = userService.deleteUserByLogin(userLogin);
        }
        if (isUserRemovedAndHisTicketsReturned) {
            Log.getLogger().info("user {} was removed and all his tickets was returned to the cinema", userLogin);
        } else {
            Log.getLogger().info("user {} was not removed", userLogin);
        }
        return isUserRemovedAndHisTicketsReturned;
    }

    private void returnAllUserTicketsToCinemaByUserId(int userId) {
        List<Ticket> tickets = ticketService.getTicketsByOwnerId(userId);
        tickets.stream()
                .mapToInt(Ticket::getId)
                .forEach(this::returnTicketToCinemaByTicketId);
    }

    @Override
    public boolean addFilmToCinema(String filmTitle, LocalDateTime time, int ticketPrice) {
        boolean result = filmService.addFilmToCinema(filmTitle, time);
        if (result) {
            Log.getLogger().debug("film {} was added to the cinema", filmTitle);
            int currentFilmId = filmService.getMaxFilmId();
            if (currentFilmId != 0) {
                result = ticketService.insertTicketsToCinema(currentFilmId, ticketPrice);
                Log.getLogger().debug("tickets by film {} was added to the cinema", filmTitle);
            } else {
                Log.getLogger().debug("film_id=0 it means what film not exists");
                result = false;
            }
        }
        if (result) {
            Log.getLogger().info("Film {} was added to the cinema", filmTitle);
        } else {
            Log.getLogger().info("Film {} was not added to the cinema", filmTitle);
        }
        return result;
    }
}