package by.itacademy.final_task_nikita_sologub.controller;

class Constant {
    static final String INITIAL_MENU = "\nВыберите действие\n1.Вход в учётную запись.\n2.Регистрация учётной записи." +
            "\n3.Выход из приложения";
    static final String INITIAL_INFO = "Приветствуем вас в нашем Мини Кинотеатре.\nДля того чтобы пользоватся нашими" +
            " услугами требуется войти в свой аккаунт.\nЕсли у вас еще нет аккаунта - нужно зарегистрироваться.\n";
    static final String USER_MENU = "\nМы в главном меню пользователя пожалуйста, выберите предложенное действие\n" +
            "1.Выйти в гостевое меню(logout)\n2.Посмотреть список из доступных к просмотру фильмов\n3.Посмотреть список из " +
            "купленных билетов\n4.Купить билет\n5.Сдать билет обратно";
    static final String MANAGER_MENU = "\nМы в главном меню менеджера пожалуйста, выберите предложенное действие\n" +
            "1.Выйти в гостевое меню(logout)\n2.Изменить название фильма в сеансе\n3.Изменить дату сеанса требуемого кинофильма" +
            "\n4.Изменить логин пользователя на новый\n5.Добавляем мероприятие и билеты на весь кинозал\n6.Покупаем билет определённому" +
            " пользовалелю\n7.Возвращаем билет определённому пользовалелю\n";
    static final String ADMIN_MENU = "\nМы в главном меню менеджера пожалуйста, выберите предложенное действие\n" +
            "1.Выйти в гостевое меню(logout)\n2.Удалить сеанс и вернуть все купленные билеты\n" +
            "3.Удалить пользователя по логину и вернуть все его купленные билеты\n4.Изменить уровень доступа пользователя по логину\n";
    static final String MANAGER_ACCESS_CHANGE_MENU = "Данный пользователь - Manager. Выберите предложенные действия:" +
            "\n1.Понизить уровень доступа до User\n2.Оставить уровень доступа Manager без изменений";
    static final String USER_ACCESS_CHANGE_MENU = "Данный пользователь - User. Выберите предложенные действия:" +
            "\n1.Повысить уровень доступа до Manager\n2.Оставить уровень доступа User без изменений";

    static final int CINEMA_OWNER_ID = -1;

    static final String WRONG_PASSWORD = "Вы ввели не верный пароль. Попробуйте еще раз";
    static final String WRONG_INPUT = "Введённые данные не корректны, пожалуйста попробуйте снова.";
    static final String HELLO_MESSAGE_1 = "Мы снова рады вас видеть %s %s. Приятного времяпровождения!\n";
    static final String HELLO_MESSAGE_2 = "Здравствуй новый %s %s. Наслажайся просмотром наших фильмов!\n";
    static final String NOT_SUCCESS_OPERATION = "Данная операция не доступна. Пожалуйста поворите позже.";
    static final String SERVER_CONNECTION_ERROR = "Ошибка соединения с сервером.Программа временно не доступна.";

    static final String INPUT_LOGIN = "Введите логин";
    static final String INPUT_NEW_LOGIN = "Введите новый логин пользователя";
    static final String INPUT_PASSWORD = "Введите пароль";
    static final String INPUT_FILM_DATE = "Введите дату кинопоказа:";
    static final String INPUT_NEW_FILM_DATE = "Введите название новой даты показв фильма";
    static final String INPUT_FILM_TITLE = "Введите название картины:";
    static final String INPUT_NEW_FILM_TITLE = "Введите название нового фильма";
    static final String INPUT_FILM_PRICE_BY_COINS = "Введите стоимость билета в копейках";
    static final String INPUT_FILM_ID_YOU_WANT_TO_BUY_A_TICKET = "Введите шифр фильма на который хотите пойти";
    static final String INPUT_FILM_ID_TO_DELETE = "Введите шифр фильма который хотите удалить.\nВcе доступные шифры мероприятий: ";
    static final String INPUT_FILM_ID_TO_CHANGE_FILM = "Введите шифр сеанса, где хотите изменить фильм.\nВсе доступные шифры сеансов: ";
    static final String INPUT_FILM_ID_TO_CHANGE_FILM_DATA = "Введите шифр сеанса, где хотите изменить дату показа.\nВсе доступные шифры сеансов: ";
    static final String INPUT_VACANT_PLACE_NUMBER = "Введите номер места из теx что в наличии.\nМеста в наличии: ";

    static final String INPUT_TICKET_ID_TO_RETURN = "Введите номер билета который хотите вернуть в кассу.\nСписок билетов у вас на руках: ";

    static final String EXIT_INFO = "Спасибо что воспользовались нашим кинотеатром.";
    static final String CLOSE_DB_CONNECTION_MESSAGE = "Ошибка закрытия соединения с базой данных";
    static final String CLOSE_SYSTEM_IN_MESSAGE = "Ошибка закрытия соединения с потоком ввода данных";
    static final String AUTHORISATION_ERROR_MESSAGE = "Ошибка авторизации. Принудительный выход в главное меню";
    static final String LOG_OUT_MESSAGE = "Вы вышли из своего аккаунта";
    static final String USER_LOGIN_NOT_EXIST = "Пользователя с таким логином не сушествует";
    static final String ACCESS = "Уровень доступа ";
    static final String USER_LOGIN = "Логин нользователя: ";
    static final String WAS_CHANGED = " был изменён на ";
    static final String WAS_NOT_CHANGED = " остался прежним как ";

    static final String SUCCESSFULLY_CHANGED = " был успешно заменен на новый логин:";
    static final String USER_LOGIN_WAS_NOT_CHANGED = "Логин не был изменён. Проверьте исходные данные";
    static final String TICKET_PURCHASED = "Билет приобретён";
    static final String FILM_ID_NOT_EXIST = "Фильма с таким шифром не существует. Пожалуйста укажите существующий шифр";
    static final String ALL_TICKETS_ARE_SOLD_OUT = "Сейчас в кинотеатре все билеты на все сеансы распроданы";
    static final String THE_TICKET_NOT_IN_SALE = "Билета с таким местом нет в продаже.";
    static final String TICKET_RETURNED = "Билет успешно возвращен. Деньги перечисленны обратно на ваш счет";
    static final String FILM_TITLE_CHANGED = "Название фильма в сеансе изменено";
    static final String FILM_DATE_CHANGED = "Дата показа фильма в сеансе изменена";
    static final String UNKNOWN_ACCESS = "Неизвестный уровень доступа. Операция не произведена.";
    static final String UNABLE_TO_CHANGE_ACCESS_FOR_ADMIN = "Невозможно изменить уровень доступа, данный пользователь ADMIN.";

    static final String TIME_ITEM_INPUT = "Введите %s в формате %s.\n";
    static final String INCORRECT_INPUT = "Введенные данные не корректны.Попробуйте ввести снова.";
    static final String ERROR_MESSAGE = "Не удалось ввести элемент LocalDateTime";

    static final String YEAR_ITEM = "год";
    static final String MONTH_ITEM = "месяц";
    static final String DAY_ITEM = "день";
    static final String HOUR_ITEM = "часы";
    static final String MINUTE_ITEM = "минуты";

    static final String YEAR_FORMAT = "yyyy";
    static final String MONTH_FORMAT = "MM";
    static final String DAY_FORMAT = "dd";
    static final String HOUR_FORMAT = "HH";
    static final String MINUTE_FORMAT = "mm";

    static final String YEAR_REG_EXP = "\\d{4}";
    static final String MONTH_REG_EXP = "(0?[1-9]|1[012])";
    static final String DAY_REG_EXP = "(0?[1-9]|[12][\\d]|3[01])";
    static final String HOUR_REG_EXP = "(0?[\\d]|1[\\d]|2[0123])";
    static final String MINUTE_REG_EXP = "[0-5][\\d]";

    static final String ONE = "1";
    static final String TWO = "2";
    static final String THREE = "3";
    static final String FOUR = "4";
    static final String FIVE = "5";
    static final String SIX = "6";
    static final String SEVEN = "7";
}