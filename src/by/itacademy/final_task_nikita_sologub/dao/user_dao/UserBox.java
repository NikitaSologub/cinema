package by.itacademy.final_task_nikita_sologub.dao.user_dao;

import by.itacademy.final_task_nikita_sologub.model.user.User;

public class UserBox {
    public static final int USER_FOUND_PASSWORD_RIGHT = 0;
    public static final int USER_FOUND_PASSWORD_WRONG = 1;
    public static final int USER_NOT_FOUND = 2;
    public static final int USER_FAILED_OPERATION = 3;
    private User user;
    private int operationCode;

    public UserBox(User user, int operationCode) {
        this.user = user;
        this.operationCode = operationCode;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getOperationCode() {
        return operationCode;
    }

    public void setOperationCode(int operationCode) {
        this.operationCode = operationCode;
    }
}