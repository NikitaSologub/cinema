package by.itacademy.final_task_nikita_sologub.model.user;

import by.itacademy.final_task_nikita_sologub.access.Access;

public interface UserFactory {
    User createUser();

    User createUser(int id, String login, Access access);
}