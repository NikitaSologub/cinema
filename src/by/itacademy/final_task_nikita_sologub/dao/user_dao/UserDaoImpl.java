package by.itacademy.final_task_nikita_sologub.dao.user_dao;

import by.itacademy.final_task_nikita_sologub.access.Access;
import by.itacademy.final_task_nikita_sologub.connection.DBConnection;
import by.itacademy.final_task_nikita_sologub.dao.AbstractJdbcDao;
import by.itacademy.final_task_nikita_sologub.encoder.Encoder;
import by.itacademy.final_task_nikita_sologub.exception.UserDaoException;
import by.itacademy.final_task_nikita_sologub.model.user.User;
import by.itacademy.final_task_nikita_sologub.model.user.UserFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static by.itacademy.final_task_nikita_sologub.dao.Constant.USER_ACCESS_FIELD;
import static by.itacademy.final_task_nikita_sologub.dao.Constant.USER_ID_FIELD;
import static by.itacademy.final_task_nikita_sologub.dao.Constant.USER_LOGIN_FIELD;
import static by.itacademy.final_task_nikita_sologub.dao.Constant.USER_PASSWORD_FIELD;
import static by.itacademy.final_task_nikita_sologub.dao.user_dao.Constant.ACCESS_INDEX;
import static by.itacademy.final_task_nikita_sologub.dao.user_dao.Constant.DELETE_BY_LOGIN;
import static by.itacademy.final_task_nikita_sologub.dao.user_dao.Constant.EMPTY_USER_ELEMENTS;
import static by.itacademy.final_task_nikita_sologub.dao.user_dao.Constant.ID_INDEX;
import static by.itacademy.final_task_nikita_sologub.dao.user_dao.Constant.INSERT_USER;
import static by.itacademy.final_task_nikita_sologub.dao.user_dao.Constant.LOGIN_INDEX;
import static by.itacademy.final_task_nikita_sologub.dao.user_dao.Constant.PASSWORD_INDEX;
import static by.itacademy.final_task_nikita_sologub.dao.user_dao.Constant.SELECT_ALL;
import static by.itacademy.final_task_nikita_sologub.dao.user_dao.Constant.SELECT_USER;
import static by.itacademy.final_task_nikita_sologub.dao.user_dao.Constant.UPDATE_ACCESS;
import static by.itacademy.final_task_nikita_sologub.dao.user_dao.Constant.UPDATE_LOGIN;
import static by.itacademy.final_task_nikita_sologub.service.Constant.INVALID_USER;

public class UserDaoImpl extends AbstractJdbcDao implements UserDao {
    private final UserFactory userFactory;

    public UserDaoImpl(UserFactory factory) {
        this.userFactory = factory;
    }

    @Override
    public UserBox readUser(String login, String password) throws UserDaoException {
        String[] userElements = getUserByLogin(login);
        UserBox userBox = new UserBox(null, UserBox.USER_NOT_FOUND);
        if (userElements != EMPTY_USER_ELEMENTS) {//массив элементов не равен null значит логин такой есть
            String hashedPassword = Encoder.getHashCode(password);
            if (hashedPassword.equals(userElements[PASSWORD_INDEX])) {//пароли совпали - возвращаем юзера
                User user = userFactory.createUser();
                user.setId(Integer.parseInt(userElements[ID_INDEX]));
                user.setLogin(userElements[LOGIN_INDEX]);
                user.setAccess(Access.valueOf(userElements[ACCESS_INDEX]));
                userBox.setUser(user);
                userBox.setOperationCode(UserBox.USER_FOUND_PASSWORD_RIGHT);
            } else {//пароли не совпали - возвращаем null
                userBox.setOperationCode(UserBox.USER_FOUND_PASSWORD_WRONG);
            }
        }
        return userBox;
    }

    private String[] getUserByLogin(String login) throws UserDaoException {
        String[] userElements = EMPTY_USER_ELEMENTS;
        try {
            initSqlQuery(SELECT_USER);
            setSqlParam(1, login);
            executeQuery();
            if (hasNextRaw()) {
                userElements = new String[4];
                userElements[0] = String.valueOf(getIntField(USER_ID_FIELD));
                userElements[1] = getStringField(USER_LOGIN_FIELD);
                userElements[2] = getStringField(USER_PASSWORD_FIELD);
                userElements[3] = getStringField(USER_ACCESS_FIELD);
            }
            closeResourcesAndConnection();
        } catch (SQLException e) {
            throw new UserDaoException("пытались взять юзера по имени", e);
        }
        return userElements;
    }

    @Override
    public boolean insertNewUser(String login, String password, Access access) throws UserDaoException {
        String[] elements = getUserByLogin(login);
        boolean isUserAdded = false;
        if (elements == EMPTY_USER_ELEMENTS) {
            try {
                initSqlQuery(INSERT_USER);
                setSqlParam(1, login);
                setSqlParam(2, Encoder.getHashCode(password));
                setSqlParam(3, access.name());
                isUserAdded = execute();
                closeResourcesAndConnection();
            } catch (SQLException e) {
                throw new UserDaoException("user не создан", e);
            }
        }
        return isUserAdded;
    }

    @Override
    public boolean updateLogin(String oldLogin, String newLogin) throws UserDaoException {
        boolean isLoginUpdated;
        try {
            initSqlQuery(UPDATE_LOGIN);
            setSqlParam(1, newLogin);
            setSqlParam(2, oldLogin);
            isLoginUpdated = executeUpdateAndGetResult();
            closeResourcesAndConnection();
        } catch (SQLException e) {
            throw new UserDaoException(DBConnection.ERROR_MESSAGE_2, e);
        }
        return isLoginUpdated;
    }

    @Override
    public boolean updateAccess(String login, Access newAccess) throws UserDaoException {
        boolean isAccessUpdated;
        try {
            initSqlQuery(UPDATE_ACCESS);
            setSqlParam(1, newAccess.name());
            setSqlParam(2, login);
            isAccessUpdated = executeUpdateAndGetResult();
            closeResourcesAndConnection();
        } catch (SQLException e) {
            throw new UserDaoException(DBConnection.ERROR_MESSAGE_2, e);
        }
        return isAccessUpdated;
    }

    @Override
    public boolean deleteUserByLogin(String login) throws UserDaoException {
        boolean isUserDeleted;
        try {
            initSqlQuery(DELETE_BY_LOGIN);
            setSqlParam(1, login);
            isUserDeleted = executeUpdateAndGetResult();
            closeResourcesAndConnection();
        } catch (SQLException e) {
            throw new UserDaoException(DBConnection.ERROR_MESSAGE_2, e);
        }
        return isUserDeleted;
    }

    @Override
    public List<User> getAllUsers() throws UserDaoException {
        List<User> users = new ArrayList<>();
        try {
            initSqlQuery(SELECT_ALL);
            executeQuery();
            while (hasNextRaw()) {
                int id = getIntField(USER_ID_FIELD);
                String login = getStringField(USER_LOGIN_FIELD);
                Access access = Access.valueOf(getStringField(USER_ACCESS_FIELD));
                User user = userFactory.createUser(id, login, access);
                users.add(user);
            }
            closeResourcesAndConnection();
        } catch (SQLException e) {
            throw new UserDaoException(DBConnection.ERROR_MESSAGE_2, e);
        }
        return users;
    }

    @Override
    public User getUser(String login) throws UserDaoException {
        String[] userElements = getUserByLogin(login);
        User user = INVALID_USER;
        if (isUserExists(userElements)) {
            int id = Integer.parseInt(userElements[ID_INDEX]);
            String userLogin = userElements[LOGIN_INDEX];
            Access access = Access.valueOf(userElements[ACCESS_INDEX]);
            user = userFactory.createUser(id, userLogin, access);
        }
        return user;
    }

    private boolean isUserExists(String[] userElements) {
        return userElements != EMPTY_USER_ELEMENTS;
    }
}