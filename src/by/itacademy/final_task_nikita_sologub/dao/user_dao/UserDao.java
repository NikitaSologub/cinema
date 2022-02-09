package by.itacademy.final_task_nikita_sologub.dao.user_dao;

import by.itacademy.final_task_nikita_sologub.access.Access;
import by.itacademy.final_task_nikita_sologub.exception.UserDaoException;
import by.itacademy.final_task_nikita_sologub.model.user.User;

import java.util.List;

public interface UserDao {
    public UserBox readUser(String login, String password) throws UserDaoException;

    public boolean insertNewUser(String login, String password, Access access) throws UserDaoException;

    public boolean updateLogin(String oldLogin, String newLogin) throws UserDaoException;

    public boolean updateAccess(String login, Access newAccess) throws UserDaoException;

    public boolean deleteUserByLogin(String login) throws UserDaoException;

    public List<User> getAllUsers() throws UserDaoException;

    public User getUser(String login) throws UserDaoException;
}