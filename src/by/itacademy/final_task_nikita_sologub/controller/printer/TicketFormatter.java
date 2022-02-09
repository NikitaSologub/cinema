package by.itacademy.final_task_nikita_sologub.controller.printer;

import by.itacademy.final_task_nikita_sologub.model.ticket.Ticket;

import java.util.List;

public interface TicketFormatter {
    String getFormattedView(final List<Ticket> tickets);
}