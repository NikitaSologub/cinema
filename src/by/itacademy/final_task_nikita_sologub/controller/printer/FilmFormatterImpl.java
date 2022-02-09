package by.itacademy.final_task_nikita_sologub.controller.printer;

import by.itacademy.final_task_nikita_sologub.date.DateFormatter;
import by.itacademy.final_task_nikita_sologub.model.film.Film;

import java.util.List;

import static by.itacademy.final_task_nikita_sologub.controller.printer.Constant.CYAN;
import static by.itacademy.final_task_nikita_sologub.controller.printer.Constant.DATE;
import static by.itacademy.final_task_nikita_sologub.controller.printer.Constant.EMPTY_FILMS_BODY;
import static by.itacademy.final_task_nikita_sologub.controller.printer.Constant.EVENT_CODE;
import static by.itacademy.final_task_nikita_sologub.controller.printer.Constant.FILM;
import static by.itacademy.final_task_nikita_sologub.controller.printer.Constant.FILM_HEADER;
import static by.itacademy.final_task_nikita_sologub.controller.printer.Constant.NEXT_LINE;
import static by.itacademy.final_task_nikita_sologub.controller.printer.Constant.RED;
import static by.itacademy.final_task_nikita_sologub.controller.printer.Constant.SEMICOLON;
import static by.itacademy.final_task_nikita_sologub.controller.printer.Constant.TICKETS_REMAIN;

public class FilmFormatterImpl extends AbstractFormatter implements FilmFormatter {
    public FilmFormatterImpl(StringBuilder builder) {
        super(builder);
    }

    @Override
    public String getFormattedView(List<Film> films) {
        setMainAndContrastColours(CYAN, RED);
        appendHeader(FILM_HEADER);
        if (films.isEmpty()) {
            appendEmptyBody(EMPTY_FILMS_BODY);
        } else {
            appendFilmsBody(films);
        }
        appendFooter();
        String view = builder.toString();
        flushResources();
        return view;
    }

    private void appendFilmsBody(final List<Film> films) {
        //"ФИЛЬМ:%s; ШИФР_МЕРОПРИЯТИЯ:%d; ДАТА:%s; БИЛЕТОВ_ОСТАЛОСЬ:%d\n";
        for (Film film : films) {
            appendElementAndBorder(FILM, film.getTitle(), SEMICOLON);
            appendElementAndBorder(EVENT_CODE, film.getId(), SEMICOLON);
            appendElementAndBorder(DATE, DateFormatter.getFormattedDate(film.getTime()), SEMICOLON);
            appendElementAndBorder(TICKETS_REMAIN, film.getTicketsAmount(), NEXT_LINE);
        }
    }
}