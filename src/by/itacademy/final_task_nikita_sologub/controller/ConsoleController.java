package by.itacademy.final_task_nikita_sologub.controller;

import by.itacademy.final_task_nikita_sologub.access.Access;
import by.itacademy.final_task_nikita_sologub.connection.DBConnection;
import by.itacademy.final_task_nikita_sologub.controller.printer.FilmFormatter;
import by.itacademy.final_task_nikita_sologub.controller.printer.TicketFormatter;
import by.itacademy.final_task_nikita_sologub.dao.user_dao.UserBox;
import by.itacademy.final_task_nikita_sologub.exception.DBConnectionException;
import by.itacademy.final_task_nikita_sologub.exception.InputTimeException;
import by.itacademy.final_task_nikita_sologub.model.film.Film;
import by.itacademy.final_task_nikita_sologub.model.ticket.Ticket;
import by.itacademy.final_task_nikita_sologub.model.user.User;
import by.itacademy.final_task_nikita_sologub.service.FacadeService;
import by.itacademy.final_task_nikita_sologub.service.FacadeServiceImpl;
import by.itacademy.final_task_nikita_sologub.service.film_service.FilmService;
import by.itacademy.final_task_nikita_sologub.service.ticket_service.TicketService;
import by.itacademy.final_task_nikita_sologub.service.user_service.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static by.itacademy.final_task_nikita_sologub.controller.Constant.ACCESS;
import static by.itacademy.final_task_nikita_sologub.controller.Constant.ADMIN_MENU;
import static by.itacademy.final_task_nikita_sologub.controller.Constant.ALL_TICKETS_ARE_SOLD_OUT;
import static by.itacademy.final_task_nikita_sologub.controller.Constant.AUTHORISATION_ERROR_MESSAGE;
import static by.itacademy.final_task_nikita_sologub.controller.Constant.CINEMA_OWNER_ID;
import static by.itacademy.final_task_nikita_sologub.controller.Constant.CLOSE_DB_CONNECTION_MESSAGE;
import static by.itacademy.final_task_nikita_sologub.controller.Constant.CLOSE_SYSTEM_IN_MESSAGE;
import static by.itacademy.final_task_nikita_sologub.controller.Constant.DAY_FORMAT;
import static by.itacademy.final_task_nikita_sologub.controller.Constant.DAY_ITEM;
import static by.itacademy.final_task_nikita_sologub.controller.Constant.DAY_REG_EXP;
import static by.itacademy.final_task_nikita_sologub.controller.Constant.ERROR_MESSAGE;
import static by.itacademy.final_task_nikita_sologub.controller.Constant.EXIT_INFO;
import static by.itacademy.final_task_nikita_sologub.controller.Constant.FILM_DATE_CHANGED;
import static by.itacademy.final_task_nikita_sologub.controller.Constant.FILM_ID_NOT_EXIST;
import static by.itacademy.final_task_nikita_sologub.controller.Constant.FILM_TITLE_CHANGED;
import static by.itacademy.final_task_nikita_sologub.controller.Constant.FIVE;
import static by.itacademy.final_task_nikita_sologub.controller.Constant.FOUR;
import static by.itacademy.final_task_nikita_sologub.controller.Constant.HELLO_MESSAGE_1;
import static by.itacademy.final_task_nikita_sologub.controller.Constant.HELLO_MESSAGE_2;
import static by.itacademy.final_task_nikita_sologub.controller.Constant.HOUR_FORMAT;
import static by.itacademy.final_task_nikita_sologub.controller.Constant.HOUR_ITEM;
import static by.itacademy.final_task_nikita_sologub.controller.Constant.HOUR_REG_EXP;
import static by.itacademy.final_task_nikita_sologub.controller.Constant.INCORRECT_INPUT;
import static by.itacademy.final_task_nikita_sologub.controller.Constant.INITIAL_INFO;
import static by.itacademy.final_task_nikita_sologub.controller.Constant.INITIAL_MENU;
import static by.itacademy.final_task_nikita_sologub.controller.Constant.INPUT_FILM_DATE;
import static by.itacademy.final_task_nikita_sologub.controller.Constant.INPUT_FILM_ID_TO_CHANGE_FILM;
import static by.itacademy.final_task_nikita_sologub.controller.Constant.INPUT_FILM_ID_TO_CHANGE_FILM_DATA;
import static by.itacademy.final_task_nikita_sologub.controller.Constant.INPUT_FILM_ID_TO_DELETE;
import static by.itacademy.final_task_nikita_sologub.controller.Constant.INPUT_FILM_ID_YOU_WANT_TO_BUY_A_TICKET;
import static by.itacademy.final_task_nikita_sologub.controller.Constant.INPUT_FILM_PRICE_BY_COINS;
import static by.itacademy.final_task_nikita_sologub.controller.Constant.INPUT_FILM_TITLE;
import static by.itacademy.final_task_nikita_sologub.controller.Constant.INPUT_LOGIN;
import static by.itacademy.final_task_nikita_sologub.controller.Constant.INPUT_NEW_FILM_DATE;
import static by.itacademy.final_task_nikita_sologub.controller.Constant.INPUT_NEW_FILM_TITLE;
import static by.itacademy.final_task_nikita_sologub.controller.Constant.INPUT_NEW_LOGIN;
import static by.itacademy.final_task_nikita_sologub.controller.Constant.INPUT_PASSWORD;
import static by.itacademy.final_task_nikita_sologub.controller.Constant.INPUT_TICKET_ID_TO_RETURN;
import static by.itacademy.final_task_nikita_sologub.controller.Constant.INPUT_VACANT_PLACE_NUMBER;
import static by.itacademy.final_task_nikita_sologub.controller.Constant.LOG_OUT_MESSAGE;
import static by.itacademy.final_task_nikita_sologub.controller.Constant.MANAGER_ACCESS_CHANGE_MENU;
import static by.itacademy.final_task_nikita_sologub.controller.Constant.MANAGER_MENU;
import static by.itacademy.final_task_nikita_sologub.controller.Constant.MINUTE_FORMAT;
import static by.itacademy.final_task_nikita_sologub.controller.Constant.MINUTE_ITEM;
import static by.itacademy.final_task_nikita_sologub.controller.Constant.MINUTE_REG_EXP;
import static by.itacademy.final_task_nikita_sologub.controller.Constant.MONTH_FORMAT;
import static by.itacademy.final_task_nikita_sologub.controller.Constant.MONTH_ITEM;
import static by.itacademy.final_task_nikita_sologub.controller.Constant.MONTH_REG_EXP;
import static by.itacademy.final_task_nikita_sologub.controller.Constant.NOT_SUCCESS_OPERATION;
import static by.itacademy.final_task_nikita_sologub.controller.Constant.ONE;
import static by.itacademy.final_task_nikita_sologub.controller.Constant.SERVER_CONNECTION_ERROR;
import static by.itacademy.final_task_nikita_sologub.controller.Constant.SEVEN;
import static by.itacademy.final_task_nikita_sologub.controller.Constant.SIX;
import static by.itacademy.final_task_nikita_sologub.controller.Constant.SUCCESSFULLY_CHANGED;
import static by.itacademy.final_task_nikita_sologub.controller.Constant.THE_TICKET_NOT_IN_SALE;
import static by.itacademy.final_task_nikita_sologub.controller.Constant.THREE;
import static by.itacademy.final_task_nikita_sologub.controller.Constant.TICKET_PURCHASED;
import static by.itacademy.final_task_nikita_sologub.controller.Constant.TICKET_RETURNED;
import static by.itacademy.final_task_nikita_sologub.controller.Constant.TIME_ITEM_INPUT;
import static by.itacademy.final_task_nikita_sologub.controller.Constant.TWO;
import static by.itacademy.final_task_nikita_sologub.controller.Constant.UNABLE_TO_CHANGE_ACCESS_FOR_ADMIN;
import static by.itacademy.final_task_nikita_sologub.controller.Constant.UNKNOWN_ACCESS;
import static by.itacademy.final_task_nikita_sologub.controller.Constant.USER_ACCESS_CHANGE_MENU;
import static by.itacademy.final_task_nikita_sologub.controller.Constant.USER_LOGIN;
import static by.itacademy.final_task_nikita_sologub.controller.Constant.USER_LOGIN_NOT_EXIST;
import static by.itacademy.final_task_nikita_sologub.controller.Constant.USER_LOGIN_WAS_NOT_CHANGED;
import static by.itacademy.final_task_nikita_sologub.controller.Constant.USER_MENU;
import static by.itacademy.final_task_nikita_sologub.controller.Constant.WAS_CHANGED;
import static by.itacademy.final_task_nikita_sologub.controller.Constant.WAS_NOT_CHANGED;
import static by.itacademy.final_task_nikita_sologub.controller.Constant.WRONG_INPUT;
import static by.itacademy.final_task_nikita_sologub.controller.Constant.WRONG_PASSWORD;
import static by.itacademy.final_task_nikita_sologub.controller.Constant.YEAR_FORMAT;
import static by.itacademy.final_task_nikita_sologub.controller.Constant.YEAR_ITEM;
import static by.itacademy.final_task_nikita_sologub.controller.Constant.YEAR_REG_EXP;
import static by.itacademy.final_task_nikita_sologub.service.Constant.INVALID_USER;

public class ConsoleController implements Controller {
    private final UserService userService;
    private final FilmService filmService;
    private final TicketService ticketService;
    private final FacadeService facadeService;
    private final TicketFormatter ticketFormatter;
    private final FilmFormatter filmFormatter;
    private BufferedReader reader;
    private User client;

    public ConsoleController(BufferedReader br, TicketFormatter tf, FilmFormatter ff,
                             UserService us, FilmService fs, TicketService ts) {
        this.reader = br;
        this.filmFormatter = ff;
        this.ticketFormatter = tf;
        this.userService = us;
        this.filmService = fs;
        this.ticketService = ts;
        facadeService = new FacadeServiceImpl(ts, fs, us);
    }

    @Override
    public void start() {
        System.out.println(INITIAL_INFO);
        System.out.println(INITIAL_MENU);
        while (true) {
            try {
                startGuestPage();
                System.out.println(INITIAL_MENU);
            } catch (IOException e) {
                System.out.println(SERVER_CONNECTION_ERROR);
                exitApp();
                return;
            }
        }
    }

    private void exitApp() {
        System.out.println(EXIT_INFO);
        closeDbConnection();
        closeSystemIn();
        System.exit(0);
    }

    private void closeDbConnection() {
        try {
            DBConnection.closeConnection();
        } catch (DBConnectionException e) {
            System.err.println(CLOSE_DB_CONNECTION_MESSAGE);
        }
    }

    private void closeSystemIn() {
        try {
            if (reader != null) {
                reader.close();
                reader = null;
            }
        } catch (IOException e) {
            System.err.println(CLOSE_SYSTEM_IN_MESSAGE);
        }
    }

    private void startGuestPage() throws IOException {
        switch (reader.readLine()) {
            case ONE:
                logIn();
                startLogicByUserAccess();
                break;
            case TWO:
                createNewAccountAndLogIn();
                startLogicByUserAccess();
                break;
            case THREE:
                exitApp();
                break;
            default:
                System.out.println(WRONG_INPUT);
                break;
        }
    }

    private void startLogicByUserAccess() {
        if (client == null) {
            System.out.println(AUTHORISATION_ERROR_MESSAGE);
        } else if (client.getAccess() == Access.MANAGER) {
            startManagerLogic();
        } else if (client.getAccess() == Access.USER) {
            startUserLogic();
        } else if (client.getAccess() == Access.ADMIN) {
            startAdminLogic();
        } else {
            System.out.println(AUTHORISATION_ERROR_MESSAGE);
        }
    }

    private void startAdminLogic() {
        while (true) {
            try {
                System.out.println(ADMIN_MENU);
                String choice = reader.readLine();
                switch (choice) {
                    case ONE:
                        logOut();
                        return;
                    case TWO:
                        deleteFilmAndAllTicketsForIt();
                        break;
                    case THREE:
                        deleteUserByLoginAndReturnAllHisTicketsToCinema();
                        break;
                    case FOUR:
                        changeAccessLevel();
                        break;
                    default:
                        System.out.println(WRONG_INPUT);
                        break;
                }
            } catch (IOException e) {
                System.out.println(WRONG_INPUT);
            }
        }
    }

    private void startManagerLogic() {
        while (true) {
            try {
                System.out.println(MANAGER_MENU);
                String choice = reader.readLine();
                switch (choice) {
                    case ONE:
                        logOut();
                        return;
                    case TWO:
                        changeFilmName();
                        break;
                    case THREE:
                        changeFilmDate();
                        break;
                    case FOUR:
                        updateUserLogin();
                        break;
                    case FIVE:
                        addFilm();
                        break;
                    case SIX:
                        buyTicketByLogin();
                        break;
                    case SEVEN:
                        returnUserTicket();
                        break;
                    default:
                        System.out.println(WRONG_INPUT);
                        break;
                }
            } catch (IOException e) {
                System.out.println(WRONG_INPUT);
            }
        }
    }

    private void startUserLogic() {
        while (true) {
            try {
                System.out.println(USER_MENU);
                String choice = reader.readLine();
                switch (choice) {
                    case ONE:
                        logOut();
                        return;
                    case TWO:
                        showAccessibleFilms();
                        break;
                    case THREE:
                        showBoughtTickets();
                        break;
                    case FOUR:
                        buyTicket(client.getId());
                        break;
                    case FIVE:
                        showBoughtTickets();
                        returnClientTicketToCinema(client.getId());
                        break;
                    default:
                        System.out.println(WRONG_INPUT);
                        break;
                }
            } catch (IOException e) {
                System.out.println(WRONG_INPUT);
            }
        }
    }

    private void showAccessibleFilms() {
        List<Film> films = filmService.getAllMoviesToShow();
        String formattedFilmsView = filmFormatter.getFormattedView(films);
        System.out.println(formattedFilmsView);
    }

    private void showBoughtTickets() {
        List<Ticket> tickets = ticketService.getTicketsByOwnerId(client.getId());
        String formattedTicketsView = ticketFormatter.getFormattedView(tickets);
        System.out.println(formattedTicketsView);
    }

    private void logIn() {
        while (true) {
            try {
                String login = input(INPUT_LOGIN);
                String password = input(INPUT_PASSWORD);
                UserBox box = userService.readUser(login, password);
                if (box.getOperationCode() == UserBox.USER_FOUND_PASSWORD_RIGHT) {
                    client = box.getUser();
                    System.out.printf(HELLO_MESSAGE_1, client.getAccess(), login);
                    return;
                } else if (box.getOperationCode() == UserBox.USER_FOUND_PASSWORD_WRONG) {
                    System.out.println(WRONG_PASSWORD);
                    while (true) {
                        password = reader.readLine();
                        box = userService.readUser(login, password);
                        if (box.getOperationCode() == UserBox.USER_FOUND_PASSWORD_RIGHT) {
                            client = box.getUser();
                            System.out.printf(HELLO_MESSAGE_1, client.getAccess(), login);
                            return;
                        } else {
                            System.out.println(WRONG_PASSWORD);
                        }
                    }
                } else if (box.getOperationCode() == UserBox.USER_NOT_FOUND) {
                    System.out.println(USER_LOGIN_NOT_EXIST);
                } else if (box.getOperationCode() == UserBox.USER_FAILED_OPERATION) {
                    System.out.println("Не удалось осущестить операцию по входу. Попробуйте еще раз с новыми данными");
                } else {
                    System.out.println("Неизвестное состояние ввода.Пожалуйста введите корректные данные");
                }
            } catch (IOException e) {
                System.out.println(WRONG_INPUT);
            }
        }
    }

    private void logOut() {
        System.out.println(LOG_OUT_MESSAGE);
        client = null;
    }

    private void changeAccessLevel() {
        try {
            String userLogin = input(INPUT_LOGIN);
            changeAccess(userService.getUser(userLogin));
        } catch (IOException e) {
            System.out.println(NOT_SUCCESS_OPERATION);
        }
    }

    private void changeAccess(User user) throws IOException {
        if (user == INVALID_USER) {
            System.out.println(USER_LOGIN_NOT_EXIST);
        } else {
            changeUserAccess(user);
        }
    }

    private void changeUserAccess(User user) throws IOException {
        if (user.getAccess() == Access.ADMIN) {
            System.out.println(UNABLE_TO_CHANGE_ACCESS_FOR_ADMIN);
        } else if (user.getAccess() == Access.MANAGER) {
            changeAccessForManager(user);
        } else if (user.getAccess() == Access.USER) {
            changeAccessForUser(user);
        } else {
            System.out.println(UNKNOWN_ACCESS);
        }
    }

    private void changeAccessForManager(User user) throws IOException {
        updateUserAccessDependingCurrentUserAccess(user, Access.USER, MANAGER_ACCESS_CHANGE_MENU);
    }

    private void changeAccessForUser(User user) throws IOException {
        updateUserAccessDependingCurrentUserAccess(user, Access.MANAGER, USER_ACCESS_CHANGE_MENU);
    }

    private void updateUserAccessDependingCurrentUserAccess(User user, Access newAccess, String menu) throws IOException {
        while (true) {
            System.out.println(menu);
            String choice = reader.readLine();
            switch (choice) {
                case ONE:
                    System.out.println(ACCESS + user.getLogin() + WAS_CHANGED + newAccess);
                    userService.updateAccess(user.getLogin(), newAccess);
                    return;
                case TWO:
                    System.out.println(ACCESS + user.getLogin() + WAS_NOT_CHANGED + user.getAccess());
                    return;
                default:
                    System.out.println(WRONG_INPUT);
                    break;
            }
        }
    }

    private void returnUserTicket() {
        try {
            tryingReturnUserTicket();
        } catch (IOException e) {
            System.out.println(NOT_SUCCESS_OPERATION);
        }
    }

    private void tryingReturnUserTicket() throws IOException {
        String login = input(INPUT_LOGIN);
        User user = userService.getUser(login);
        if (user == INVALID_USER) {
            System.out.println(USER_LOGIN_NOT_EXIST);
        } else {
            List<Ticket> userTickets = ticketService.getTicketsByOwnerId(user.getId());
            String formattedTicketsView = ticketFormatter.getFormattedView(userTickets);
            System.out.println(formattedTicketsView);
            returnClientTicketToCinema(user.getId());
        }
    }

    private void buyTicketByLogin() {
        try {
            tryingBuyTicketByLogin();
        } catch (IOException e) {
            System.out.println(NOT_SUCCESS_OPERATION);
        }
    }

    private void tryingBuyTicketByLogin() throws IOException {
        String userLogin = input(INPUT_LOGIN);
        User user = userService.getUser(userLogin);
        if (user == INVALID_USER) {
            System.out.println(USER_LOGIN_NOT_EXIST);
        } else {
            showAccessibleFilms();
            buyTicket(user.getId());
        }
    }

    private void addFilm() {
        while (true) {
            try {
                String filmTitle = input(INPUT_FILM_TITLE);
                LocalDateTime time = inputDate(INPUT_FILM_DATE);
                int ticketPrice = inputNumber(INPUT_FILM_PRICE_BY_COINS);
                boolean isFilmAdded = facadeService.addFilmToCinema(filmTitle, time, ticketPrice);
                if (isFilmAdded) {
                    System.out.println("Новое мероприятие успешно добавлено");
                } else {
                    System.out.println("Не удалось добавить мероприятие. Проверьте исходные данные");
                }
                return;
            } catch (IOException | NumberFormatException e) {
                System.out.println(WRONG_INPUT);
            }
        }
    }

    private void deleteUserByLoginAndReturnAllHisTicketsToCinema() {
        while (true) {
            try {
                String userLogin = input(INPUT_LOGIN);
                if (facadeService.removeUserAndReturnHisTicketsToCinema(userLogin)) {
                    System.out.println("Пользователь " + userLogin + " удален. Все билеты возвращены в кассу");
                } else {
                    System.out.println("Не удалось удалить пользователя. Пожалуйста проверьте правильность логина");
                }
                return;
            } catch (IOException e) {
                System.out.println(NOT_SUCCESS_OPERATION);
            }
        }
    }

    private void deleteFilmAndAllTicketsForIt() {
        List<Film> films = filmService.getAllMovies();
        int[] filmIds = extractAllFilmIds(films);
        while (true) {
            try {
                String formattedFilmView = filmFormatter.getFormattedView(films);
                System.out.println(formattedFilmView);
                int currentFilmId = inputNumber(INPUT_FILM_ID_TO_DELETE + Arrays.toString(filmIds));
                if (isCurrentFilmIdMatchAnyFilmIds(currentFilmId, filmIds)) {
                    removeFilmAndAllItsTickets(currentFilmId);
                    return;
                } else {
                    System.out.println("Вы ввели не существующий шифр. Для удаления фильма введите существующий шифр.");
                }
            } catch (IOException e) {
                System.out.println(NOT_SUCCESS_OPERATION);
                return;
            } catch (NumberFormatException e) {
                System.out.println(WRONG_INPUT);
            }
        }
    }

    private boolean isCurrentFilmIdMatchAnyFilmIds(int currentFilmId, int[] filmIds) {
        return checkNumbers(filmIds, currentFilmId);
    }

    private void removeFilmAndAllItsTickets(int filmId) {
        if (facadeService.removeFilmAndAllTicketsForItByFilmId(filmId)) {
            System.out.println("Фильм и все билеты по текущему шифру мероприятия были удалены");
        } else {
            System.out.println("Не удалось удалить фильм. Пожалуйста поворите позже.");
        }
    }

    private void changeFilmDate() {
        List<Film> films = filmService.getAllMovies();
        int[] filmIds = extractAllFilmIds(films);
        while (true) {
            try {
                String formattedFilmsView = filmFormatter.getFormattedView(films);
                System.out.println(formattedFilmsView);
                int currentFilmId = inputNumber(INPUT_FILM_ID_TO_CHANGE_FILM_DATA + Arrays.toString(filmIds));
                if (isCurrentFilmIdMatchAnyFilmIds(currentFilmId, filmIds)) {
                    LocalDateTime time = inputDate(INPUT_NEW_FILM_DATE);
                    if (facadeService.changeFilmDateByFilmId(currentFilmId, time)) {
                        System.out.println(FILM_DATE_CHANGED);
                    } else {
                        System.out.println("Не удалось произвести операцию. Пожалуйста проверьте исходные данные.");
                    }
                    return;
                } else {
                    System.out.println("Сеанса, с таким шифром не существует. Введите существующий сеанс");
                }
            } catch (IOException e) {
                System.out.println(NOT_SUCCESS_OPERATION);
                return;
            } catch (NumberFormatException e) {
                System.out.println(WRONG_INPUT);
            }
        }
    }

    private int[] extractAllFilmIds(List<Film> films) {
        return films.stream()
                .mapToInt(Film::getId)
                .toArray();
    }

    private LocalDateTime inputDate(String message) throws IOException {
        System.out.println(message);
        return getTime();
    }

    private LocalDateTime getTime() throws IOException {
        LocalDateTime time;//format yyyy-MM-dd-HH-mm
        while (true) {
            int years = getCheckedTimeElement(YEAR_REG_EXP, YEAR_FORMAT, YEAR_ITEM);
            int months = getCheckedTimeElement(MONTH_REG_EXP, MONTH_FORMAT, MONTH_ITEM);
            int days = getCheckedTimeElement(DAY_REG_EXP, DAY_FORMAT, DAY_ITEM);
            int hours = getCheckedTimeElement(HOUR_REG_EXP, HOUR_FORMAT, HOUR_ITEM);
            int minutes = getCheckedTimeElement(MINUTE_REG_EXP, MINUTE_FORMAT, MINUTE_ITEM);
            try {
                time = LocalDateTime.of(years, months, days, hours, minutes);
                return time;
            } catch (DateTimeException e) {
                System.out.println(INCORRECT_INPUT);
            }
        }
    }

    private int getCheckedTimeElement(String regExp, String format, String timeItem) throws InputTimeException {
        while (true) {
            System.out.printf(TIME_ITEM_INPUT, timeItem, format);//formats yyyy, MM , dd ,HH , mm
            try {
                String userInput = reader.readLine();
                if (isInputMatchedTimeElement(userInput, regExp)) {
                    try {
                        return Integer.parseInt(userInput);
                    } catch (NumberFormatException e) {
                        System.out.println(INCORRECT_INPUT);
                    }
                } else {
                    System.out.println(INCORRECT_INPUT);
                }
            } catch (IOException e) {
                throw new InputTimeException(ERROR_MESSAGE, e);
            }
        }
    }

    private boolean isInputMatchedTimeElement(String input, String regExp) {
        return input.matches(regExp);
    }

    private void changeFilmName() {
        List<Film> films = filmService.getAllMovies();
        String formattedFilmsView = filmFormatter.getFormattedView(films);
        int[] filmIds = extractAllFilmIds(films);
        while (true) {
            try {
                System.out.println(formattedFilmsView);
                int currentFilmId = inputNumber(INPUT_FILM_ID_TO_CHANGE_FILM + Arrays.toString(filmIds));
                if (isCurrentFilmIdMatchAnyFilmIds(currentFilmId, filmIds)) {
                    String newFilmTitle = input(INPUT_NEW_FILM_TITLE);
                    if (facadeService.changeFilmTitleByFilmId(newFilmTitle, currentFilmId)) {
                        System.out.println(FILM_TITLE_CHANGED);
                    } else {
                        System.out.println("Не удалось произвести операцию. Пожалуйста проверьте исходные данные.");
                    }
                    return;
                } else {
                    System.out.println("Сеанса, с таким шифром не существует. Введите существующий сеанс");
                }
            } catch (IOException e) {
                System.out.println(NOT_SUCCESS_OPERATION);
                return;
            } catch (NumberFormatException e) {
                System.out.println(WRONG_INPUT);
            }
        }
    }

    private void updateUserLogin() {
        while (true) {
            try {
                String oldLogin = input(INPUT_LOGIN);
                String newLogin = input(INPUT_NEW_LOGIN);
                boolean isLoginUpdated = facadeService.changeUserLogin(oldLogin, newLogin);
                if (isLoginUpdated) {
                    System.out.println(USER_LOGIN + oldLogin + SUCCESSFULLY_CHANGED + newLogin);
                } else {
                    System.out.println(USER_LOGIN_WAS_NOT_CHANGED);
                }
                return;
            } catch (IOException e) {
                System.out.println(WRONG_INPUT);
            }
        }
    }

    private void returnClientTicketToCinema(int clientId) {
        int[] ticketIds = extractAllTicketIds(ticketService.getTicketsByOwnerId(clientId));
        if (ticketIds.length == 0) {
            System.out.println("У вас нет билетов на руках");
            return;
        }
        while (true) {
            try {
                int ticketId = inputNumber(INPUT_TICKET_ID_TO_RETURN + Arrays.toString(ticketIds));
                if (isCurrentPlaceNumberMatchAnyPlaces(ticketIds, ticketId)) {
                    facadeService.returnTicketToCinemaByTicketId(ticketId);
                    System.out.println(TICKET_RETURNED);
                    return;
                } else {
                    System.out.println(WRONG_INPUT);
                }
            } catch (NumberFormatException | IOException e) {
                System.out.println(WRONG_INPUT);
            }
        }
    }

    private int[] extractAllTicketIds(final List<Ticket> tickets) {
        return tickets.stream()
                .mapToInt(Ticket::getId)
                .toArray();
    }

    private void buyTicket(int clientId) {
        boolean isBought;
        while (true) {
            try {
                List<Ticket> tickets = ticketService.getTicketsByOwnerId(CINEMA_OWNER_ID);
                if (tickets.isEmpty()) {
                    System.out.println(ALL_TICKETS_ARE_SOLD_OUT);
                    return;
                }
                int filmId = inputNumber(INPUT_FILM_ID_YOU_WANT_TO_BUY_A_TICKET);
                tickets = tickets.stream()
                        .filter(ticket -> ticket.getFilm().getId() == filmId)
                        .collect(Collectors.toList());
                if (tickets.isEmpty()) {
                    System.out.println(FILM_ID_NOT_EXIST);
                    showAccessibleFilms();
                } else {
                    isBought = buyTicketByPlace(tickets, filmId, clientId);
                    if (isBought) {
                        System.out.println(TICKET_PURCHASED);
                    } else {
                        System.out.println(NOT_SUCCESS_OPERATION);
                    }
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println(WRONG_INPUT);
            } catch (IOException | NullPointerException e) {
                System.out.println("Сервер временно не доступен. Пожалуйста попробуйте позже.");
                return;
            }
        }
    }

    private boolean buyTicketByPlace(List<Ticket> tickets, int filmId, int clientId) {
        while (true) {
            try {
                while (true) {
                    int[] allPlaces = extractAllPlaces(tickets);
                    int placeNumber = inputNumber(INPUT_VACANT_PLACE_NUMBER + Arrays.toString(allPlaces));
                    if (isCurrentPlaceNumberMatchAnyPlaces(allPlaces, placeNumber)) {
                        boolean isFindTicket = isPlaceMatch(placeNumber, tickets);
                        if (isFindTicket) {
                            return facadeService.buyTicket(clientId, filmId, placeNumber);
                        } else {
                            System.out.println(THE_TICKET_NOT_IN_SALE);
                        }
                    } else {
                        System.out.println(WRONG_INPUT);
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println(WRONG_INPUT);
            } catch (IOException e) {
                System.out.println(NOT_SUCCESS_OPERATION);
                return false;
            }
        }
    }

    private boolean isPlaceMatch(int place, List<Ticket> tickets) {
        return tickets.stream()
                .anyMatch(ticket -> ticket.getPlace() == place);
    }

    private int[] extractAllPlaces(List<Ticket> tickets) {
        return tickets.stream()
                .mapToInt(Ticket::getPlace)
                .toArray();
    }

    private boolean isCurrentPlaceNumberMatchAnyPlaces(int[] allPlaces, int currentPlaceNumber) {
        return checkNumbers(allPlaces, currentPlaceNumber);
    }

    private boolean checkNumbers(int[] arrayNum, int inputNum) {
        return Arrays.stream(arrayNum)
                .anyMatch(s -> s == inputNum);
    }

    private void createNewAccountAndLogIn() {
        System.out.println("Создание нового аккаунта:");
        try {
            String login = input(INPUT_LOGIN);
            String password = input(INPUT_PASSWORD);
            userService.insertNewUser(login, password, Access.USER);
            UserBox box = userService.readUser(login, password);
            if (isUserExists(box)) {
                client = box.getUser();
                System.out.printf(HELLO_MESSAGE_2, client.getAccess(), login);
            } else {
                System.out.println("Не получилось создать юзера. Сделайте логин или пароль короче");
            }
        } catch (IOException e) {
            System.out.println(WRONG_INPUT);
        }
    }

    private String input(String message) throws IOException {
        System.out.println(message);
        return reader.readLine();
    }

    private int inputNumber(String message) throws IOException {
        System.out.println(message);
        return Integer.parseInt(reader.readLine());
    }

    private boolean isUserExists(UserBox box) {
        return UserBox.USER_FOUND_PASSWORD_RIGHT == box.getOperationCode();
    }
}