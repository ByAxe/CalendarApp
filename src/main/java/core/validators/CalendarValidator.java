package core.validators;

import core.commons.Result;
import core.dto.api.IEventsDTO;

import java.util.ArrayList;
import java.util.List;

import static core.enums.ResultEnum.ERROR;
import static core.enums.ResultEnum.SUCCESS;
import static java.time.temporal.ChronoUnit.MINUTES;
import static java.util.stream.Collectors.toList;

/**
 * Created by A.Litvinau on 12/10/2016.
 */
public class CalendarValidator {

    private static final int MINIMAL_EVENT_LONGING = 3;

    /**
     * В случае Успеха возвращает Result где лежит SUCCESS, а payload пустой
     * В случае Ошибок возвращает Result где лежит ERROR и payload с ошибками
     *
     * @param event Новое событие
     * @return Экземпляр результата
     */
    public Result validateNewEvent(IEventsDTO event) {
        List<String> errors = new ArrayList<>();

        if (event.getStarts().isAfter(event.getEnds())) {
            errors.add("Начальная дата события не может быть больше конечной.");
        }

        if (MINUTES.between(event.getStarts(), event.getEnds()) < MINIMAL_EVENT_LONGING) {
            errors.add("Событие не может длиться менее " + MINIMAL_EVENT_LONGING + "  минут.");
        }

        if (event.getTitle().isEmpty()) {
            errors.add("Описание события не может быть пустым.");
        }

        if (errors.isEmpty()) {
            return new Result(SUCCESS);
        } else {
            return new Result(ERROR, errors.stream().map(e -> e += "\n").collect(toList()));
        }
    }
}
