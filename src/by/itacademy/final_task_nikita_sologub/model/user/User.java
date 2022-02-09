package by.itacademy.final_task_nikita_sologub.model.user;

import by.itacademy.final_task_nikita_sologub.access.Access;

import java.util.Objects;

public class User {
    private int id;
    private String login;
    private Access access;

    public User(int id, String login, Access access) {
        this.id = id;
        this.login = login;
        this.access = access;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Access getAccess() {
        return access;
    }

    public void setAccess(Access access) {
        this.access = access;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && login.equals(user.login) && access == user.access;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, access);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("User{id=").append(id);
        sb.append(", login=").append(login).append(", access=").append(access.name());
        return sb.toString();
    }
}