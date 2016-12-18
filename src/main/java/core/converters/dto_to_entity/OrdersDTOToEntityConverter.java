package core.converters.dto_to_entity;

import core.dto.api.IOrdersDTO;
import model.entity.OrdersEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import static core.commons.Utils.convertLocalDateTimeToDate;

/**
 * Created by byaxe on 18.12.16.
 */
@Component
public class OrdersDTOToEntityConverter implements Converter<IOrdersDTO, OrdersEntity> {

    @Override
    public OrdersEntity convert(IOrdersDTO source) {
        if (source == null) return null;

        OrdersEntity target = new OrdersEntity();

        target.setId(source.getId());
        target.setUuid(source.getUuid());
        target.setDtUpdate(source.getDtUpdate());

        target.setProfession(source.getProfession());
        target.setPayload(source.getPayload());
        target.setNumber(source.getNumber());
        target.setRank(source.getRank());
        target.setStarts(convertLocalDateTimeToDate(source.getStarts()));

        return target;
    }
}
