package by.itacademy.final_task_nikita_sologub.model.film;

import java.time.LocalDateTime;
import java.util.Objects;

public class Film {
    private int id;
    private String title;
    private LocalDateTime time;
    private int ticketsAmount;

    public Film(int id, String title, LocalDateTime time, int ticketsAmount) {
        this.id = id;
        this.title = title;
        this.time = time;
        this.ticketsAmount = ticketsAmount;
    }

    public Film() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public int getTicketsAmount() {
        return ticketsAmount;
    }

    public void setTicketsAmount(int ticketsAmount) {
        this.ticketsAmount = ticketsAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return id == film.id &&
                ticketsAmount == film.ticketsAmount &&
                Objects.equals(title, film.title) &&
                Objects.equals(time, film.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, time, ticketsAmount);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Film{id=").append(id).append(", title='").append(title);
        sb.append(", date=").append(time).append(", ticketsAmount=").append(ticketsAmount);
        return sb.toString();
    }
}