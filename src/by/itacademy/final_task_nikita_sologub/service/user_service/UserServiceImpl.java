package by.itacademy.final_task_nikita_sologub.service.user_service;

import by.itacademy.final_task_nikita_sologub.access.Access;
import by.itacademy.final_task_nikita_sologub.dao.user_dao.UserBox;
import by.itacademy.final_task_nikita_sologub.dao.user_dao.UserDao;
import by.itacademy.final_task_nikita_sologub.exception.UserDaoException;
import by.itacademy.final_task_nikita_sologub.logger.Log;
import by.itacademy.final_task_nikita_sologub.model.user.User;
import by.itacademy.final_task_nikita_sologub.service.Constant;

import java.util.Collections;
import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserBox readUser(String userLogin, String userPassword) {
        UserBox userBox;
        try {
            userBox = userDao.readUser(userLogin, userPassword);
            Log.getLogger().debug("Ищем пользователя с логином {} в базе данных", userLogin);
        } catch (UserDaoException e) {
            userBox = new UserBox(null, UserBox.USER_FAILED_OPERATION);
            Log.getLogger().error("Ошибка нахождения пользователя " + userLogin, e);
        }
        return userBox;
    }

    @Override
    public boolean insertNewUser(String userLogin, String userPassword, Access userAccess) {
        boolean isNewUserAdded;
        try {
            isNewUserAdded = userDao.insertNewUser(userLogin, userPassword, userAccess);
            if (isNewUserAdded) {
                Log.getLogger().debug("Пользователь с логином {} успешно добавлен", userLogin);
            } else {
                Log.getLogger().debug("Пользователь с логином {} не добавлен в базу данных", userLogin);
            }
        } catch (UserDaoException e) {
            isNewUserAdded = false;
            Log.getLogger().error("Ошибка добавления пользователя" + userLogin + " в базу данных", e);
        }
        return isNewUserAdded;
    }

    @Override
    public boolean updateLogin(String oldLogin, String newLogin) {
        boolean isLoginUpdated;
        try {
            isLoginUpdated = userDao.updateLogin(oldLogin, newLogin);
            if (isLoginUpdated) {
                Log.getLogger().debug("Пользователь с логином={} успешно обновил свой логин на новый={}", oldLogin, newLogin);
            } else {
                Log.getLogger().debug("Пользователь с логином={} не смог обновить свой логин", oldLogin);
            }
        } catch (UserDaoException e) {
            isLoginUpdated = false;
            Log.getLogger().error("Ошибка обновления логина пользователя" + oldLogin + " в базе данных", e);
        }
        return isLoginUpdated;
    }

    @Override
    public boolean updateAccess(String userLogin, Access newUserAccess) {
        boolean isAccessUpdated;
        try {
            isAccessUpdated = userDao.updateAccess(userLogin, newUserAccess);
            if (isAccessUpdated) {
                Log.getLogger().debug("Пользователь с логином={} успешно обновил свой уровень доступа на {}", userLogin, newUserAccess);
            } else {
                Log.getLogger().debug("Пользователь с логином={} не смог обновить уровень доступа", userLogin);
            }
        } catch (UserDaoException e) {
            isAccessUpdated = false;
            Log.getLogger().error("Ошибка обновления уровня доступа пользователя" + userLogin, e);
        }
        return isAccessUpdated;
    }

    @Override
    public boolean deleteUserByLogin(String userLogin) {
        boolean isUserDeleted;
        try {
            isUserDeleted = userDao.deleteUserByLogin(userLogin);
            if (isUserDeleted) {
                Log.getLogger().debug("Пользователь с логином={} успешно удалён", userLogin);
            } else {
                Log.getLogger().debug("Пользователь с логином={} не был удалён из базы данных", userLogin);
            }
        } catch (UserDaoException e) {
            Log.getLogger().error("Ошибка удаления пользователя " + userLogin + " по логину", e);
            isUserDeleted = false;
        }
        return isUserDeleted;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users;
        try {
            users = userDao.getAllUsers();
            Log.getLogger().debug("Пользователи были взяты с базы данных в колличестве {} штук", users.size());
        } catch (UserDaoException e) {
            users = Collections.emptyList();
            Log.getLogger().error("Ошибка взятия всех пользователей", e);
        }
        return users;
    }

    @Override
    public User getUser(String login) {
        User user;
        try {
            user = userDao.getUser(login);
            if (user != Constant.INVALID_USER) {
                Log.getLogger().debug("Пользователь с логином {} успешно изъят из базы данных", login);
            } else {
                Log.getLogger().debug("Пользователь с логином {} не был изъят из базы данных", login);
            }
        } catch (UserDaoException e) {
            user = Constant.INVALID_USER;
            Log.getLogger().error("Ошибка нахождения пользователя", e);
        }
        return user;
    }
}