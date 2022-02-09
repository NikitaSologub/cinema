package by.itacademy.final_task_nikita_sologub.service;

import java.time.LocalDateTime;

public interface FacadeService {
    public boolean buyTicket(int userId, int filmId, int placeNumber);

    public boolean returnTicketToCinemaByTicketId(int ticketId);

    public boolean changeUserLogin(String userLogin, String newLogin);

    public boolean changeFilmTitleByFilmId(String newFilmTitle, int filmId);

    public boolean changeFilmDateByFilmId(int filmId, LocalDateTime newFilmDate);

    public boolean removeFilmAndAllTicketsForItByFilmId(int filmId);

    public boolean removeUserAndReturnHisTicketsToCinema(String login);

    public boolean addFilmToCinema(String filmTitle, LocalDateTime time, int ticketPrice);
}