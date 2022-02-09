package by.itacademy.final_task_nikita_sologub.dao.ticket_dao;

class Constant {
    static final String INSERT_TICKET = "INSERT INTO ticket (film_id,owner_id,ticket_price,place_number) VALUES (?,?,?,?)";
    static final String SELECT_TICKETS_BY_OWNER_ID = "SELECT * FROM ticket INNER JOIN film ON ticket.film_id = film.film_id WHERE owner_id=?";
    static final String SELECT_FILM_ID = "SELECT film_id FROM ticket WHERE ticket_id=?";
    static final String UPDATE_TICKET_OWNER = "UPDATE ticket SET owner_id=? WHERE film_id=? AND place_number=?";
    static final String UPDATE_TICKET_OWNER_TO_CINEMA = "UPDATE ticket SET owner_id=-1 WHERE ticket_id=?";
    static final String DELETE_TICKETS = "DELETE FROM ticket WHERE film_id=?";
}