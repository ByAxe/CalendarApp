package core.validators;

import core.commons.Result;
import core.dto.api.IEventsDTO;

import java.util.ArrayList;
import java.util.List;

import static core.enums.ResultEnum.ERROR;
import static core.enums.ResultEnum.SUCCESS;

/**
 * Created by A.Litvinau on 12/10/2016.
 */
public class CalendarValidator {

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
            errors.add("Начальная дата события не может быть больше конечной.\n");
        }

        if (event.getTitle().isEmpty()) {
            errors.add("Описание события не может быть пустым.\n");
        }

        if (errors.isEmpty()) return new Result(SUCCESS);
        else return new Result(ERROR, errors);
    }
}
