package by.itacademy.final_task_nikita_sologub.model.user;

import by.itacademy.final_task_nikita_sologub.access.Access;

public class UserFactoryImpl implements UserFactory {
    @Override
    public User createUser() {
        return new User();
    }

    @Override
    public User createUser(int id, String login, Access access) {
        return new User(id, login, access);
    }
}