package by.itacademy.final_task_nikita_sologub.controller.printer;

import by.itacademy.final_task_nikita_sologub.date.DateFormatter;
import by.itacademy.final_task_nikita_sologub.model.ticket.Ticket;

import java.util.List;

import static by.itacademy.final_task_nikita_sologub.controller.printer.Constant.BLR_CURRENCY;
import static by.itacademy.final_task_nikita_sologub.controller.printer.Constant.COMMA;
import static by.itacademy.final_task_nikita_sologub.controller.printer.Constant.COST;
import static by.itacademy.final_task_nikita_sologub.controller.printer.Constant.DATE;
import static by.itacademy.final_task_nikita_sologub.controller.printer.Constant.EMPTY_TICKETS_BODY;
import static by.itacademy.final_task_nikita_sologub.controller.printer.Constant.EVENT_CODE;
import static by.itacademy.final_task_nikita_sologub.controller.printer.Constant.FILM;
import static by.itacademy.final_task_nikita_sologub.controller.printer.Constant.HUNDRED;
import static by.itacademy.final_task_nikita_sologub.controller.printer.Constant.NEXT_LINE;
import static by.itacademy.final_task_nikita_sologub.controller.printer.Constant.PLACE_NUMBER;
import static by.itacademy.final_task_nikita_sologub.controller.printer.Constant.PURPLE;
import static by.itacademy.final_task_nikita_sologub.controller.printer.Constant.SEMICOLON;
import static by.itacademy.final_task_nikita_sologub.controller.printer.Constant.TICKET_HEADER;
import static by.itacademy.final_task_nikita_sologub.controller.printer.Constant.TICKET_NUMBER;
import static by.itacademy.final_task_nikita_sologub.controller.printer.Constant.YELLOW;

public class TicketFormatterImpl extends AbstractFormatter implements TicketFormatter {
    public TicketFormatterImpl(StringBuilder builder) {
        super(builder);
    }

    @Override
    public String getFormattedView(List<Ticket> tickets) {
        setMainAndContrastColours(PURPLE, YELLOW);
        appendHeader(TICKET_HEADER);
        if (tickets.isEmpty()) {
            appendEmptyBody(EMPTY_TICKETS_BODY);
        } else {
            appendTicketsBody(tickets);
        }
        appendFooter();
        String view = builder.toString();
        flushResources();
        return view;
    }

    private void appendTicketsBody(final List<Ticket> tickets) {
        //БИЛЕТ_№:%s; ШИФР_МЕРОПРИЯТИЯ:%s; ДАТА:%s; МЕСТО_№:%s; ФИЛЬМ:%s; СТОИМОСТЬ:%s
        for (Ticket ticket : tickets) {
            appendElementAndBorder(TICKET_NUMBER, ticket.getId(), SEMICOLON);
            appendElementAndBorder(EVENT_CODE, ticket.getFilm().getId(), SEMICOLON);
            appendElementAndBorder(DATE, DateFormatter.getFormattedDate(ticket.getFilm().getTime()), SEMICOLON);
            appendElementAndBorder(PLACE_NUMBER, ticket.getPlace(), SEMICOLON);
            appendElementAndBorder(FILM, ticket.getFilm().getTitle(), SEMICOLON);
            appendElementAndBorder(COST, getPriceView(ticket.getPrice()), NEXT_LINE);
        }
    }

    private String getPriceView(int coins) {
        return ((coins / HUNDRED) + COMMA + (coins % HUNDRED) + BLR_CURRENCY);
    }
}