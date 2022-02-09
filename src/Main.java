import by.itacademy.final_task_nikita_sologub.controller.ConsoleController;
import by.itacademy.final_task_nikita_sologub.controller.Controller;
import by.itacademy.final_task_nikita_sologub.dao.film_dao.FilmDaoImpl;
import by.itacademy.final_task_nikita_sologub.dao.ticket_dao.TicketDaoImpl;
import by.itacademy.final_task_nikita_sologub.dao.user_dao.UserDaoImpl;
import by.itacademy.final_task_nikita_sologub.controller.printer.FilmFormatterImpl;
import by.itacademy.final_task_nikita_sologub.controller.printer.TicketFormatterImpl;
import by.itacademy.final_task_nikita_sologub.model.film.FilmFactoryImpl;
import by.itacademy.final_task_nikita_sologub.model.ticket.TicketFactoryImpl;
import by.itacademy.final_task_nikita_sologub.model.user.UserFactoryImpl;
import by.itacademy.final_task_nikita_sologub.service.film_service.FilmServiceImpl;
import by.itacademy.final_task_nikita_sologub.service.ticket_service.TicketServiceImpl;
import by.itacademy.final_task_nikita_sologub.service.user_service.UserServiceImpl;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        Controller controller = new ConsoleController(
                new BufferedReader(new InputStreamReader(System.in)),
                new TicketFormatterImpl(new StringBuilder(1000)),
                new FilmFormatterImpl(new StringBuilder(1000)),
                new UserServiceImpl(new UserDaoImpl(new UserFactoryImpl())),
                new FilmServiceImpl(new FilmDaoImpl(new FilmFactoryImpl())),
                new TicketServiceImpl(new TicketDaoImpl(new TicketFactoryImpl())));
        controller.start();
    }
}