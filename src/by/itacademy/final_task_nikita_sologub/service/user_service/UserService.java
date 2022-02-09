package by.itacademy.final_task_nikita_sologub.service.user_service;

import by.itacademy.final_task_nikita_sologub.access.Access;
import by.itacademy.final_task_nikita_sologub.dao.user_dao.UserBox;
import by.itacademy.final_task_nikita_sologub.model.user.User;

import java.util.List;

public interface UserService {
    public UserBox readUser(String login, String password);

    public boolean insertNewUser(String login, String password, Access access);

    public boolean updateLogin(String oldLogin, String newLogin);

    public boolean updateAccess(String login, Access newAccess);

    public boolean deleteUserByLogin(String login);

    public List<User> getAllUsers();

    public User getUser(String login);
}