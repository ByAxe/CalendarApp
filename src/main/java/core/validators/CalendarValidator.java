package core.validators;

import core.commons.Result;
import core.dto.api.IEventsDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static core.commons.Utils.wrapErrorsList;
import static core.enums.ResultEnum.SUCCESS;
import static java.time.temporal.ChronoUnit.MINUTES;

/**
 * Created by A.Litvinau on 12/10/2016.
 */
@Component
public class CalendarValidator {

    @Value("${minimal.event.longing.minutes}")
    private int MINIMAL_EVENT_LONGING_MINUTES;

    /**
     * В случае Успеха возвращает {@link Result} где лежит {@link core.enums.ResultEnum.SUCCESS}, а payload пустой
     * В случае Ошибок возвращает {@link Result} где лежит {@link core.enums.ResultEnum.ERROR} и payload с ошибками
     *
     * @param event Новое событие {@link IEventsDTO}
     * @return Экземпляр результата {@link Result}
     */
    public Result validateNewEvent(IEventsDTO event) {
        List<String> errors = new ArrayList<>();

        if (event.getStarts().isAfter(event.getEnds())) {
            errors.add("Начальная дата события не может быть больше конечной.");
        }

        if (MINUTES.between(event.getStarts(), event.getEnds()) < MINIMAL_EVENT_LONGING_MINUTES) {
            errors.add("Событие не может длиться менее " + MINIMAL_EVENT_LONGING_MINUTES + "  минут.");
        }

        if (event.getTitle().isEmpty()) {
            errors.add("Описание события не может быть пустым.");
        }

        if (errors.isEmpty()) {
            return new Result(SUCCESS);
        } else {
            return wrapErrorsList(errors);
        }
    }
}
