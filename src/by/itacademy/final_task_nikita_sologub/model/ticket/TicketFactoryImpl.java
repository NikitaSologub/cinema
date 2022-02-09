package by.itacademy.final_task_nikita_sologub.model.ticket;

public class TicketFactoryImpl implements TicketFactory {
    @Override
    public Ticket createTicket() {
        return new Ticket();
    }
}