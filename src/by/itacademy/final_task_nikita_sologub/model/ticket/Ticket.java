package by.itacademy.final_task_nikita_sologub.model.ticket;

import by.itacademy.final_task_nikita_sologub.model.film.Film;

import java.util.Objects;

public class Ticket {
    private int id;
    private Film film;
    private int title;
    private int price;
    private int place;

    public Ticket(int id, Film film, int title, int price, int place) {
        this.id = id;
        this.film = film;
        this.title = title;
        this.price = price;
        this.place = place;
    }

    public Ticket() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return id == ticket.id && title == ticket.title && price == ticket.price && place == ticket.place &&
                Objects.equals(film, ticket.film);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, film, title, price, place);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Ticket{id=").append(id);
        sb.append(", Film title=").append(film.getTitle()).append(", price=").append(price);
        sb.append(",Date and time= ").append(film.getTime()).append(", place=").append(place);
        return sb.toString();
    }
}