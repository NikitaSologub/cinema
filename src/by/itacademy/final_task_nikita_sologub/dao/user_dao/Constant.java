package by.itacademy.final_task_nikita_sologub.dao.user_dao;

class Constant {
    static final String INSERT_USER = "INSERT INTO user (user_login,user_password,user_access) VALUES (?,?,?)";
    static final String SELECT_USER = "SELECT user_id,user_login,user_password,user_access FROM user WHERE user_login=?";
    static final String SELECT_ALL = "SELECT user_id,user_login,user_password,user_access FROM user";
    static final String DELETE_BY_LOGIN = "DELETE FROM user WHERE user_login=?";
    static final String UPDATE_LOGIN = "UPDATE user SET user_login=? WHERE user_login=?";
    static final String UPDATE_ACCESS = "UPDATE user SET user_access=? WHERE user_login=?";

    static final String[] EMPTY_USER_ELEMENTS = new String[0];

    static final int ID_INDEX = 0;
    static final int LOGIN_INDEX = 1;
    static final int PASSWORD_INDEX = 2;
    static final int ACCESS_INDEX = 3;
}