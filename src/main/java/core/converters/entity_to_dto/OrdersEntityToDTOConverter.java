package core.converters.entity_to_dto;

import core.dto.OrdersDTOImpl;
import core.dto.api.IOrdersDTO;
import model.entity.OrdersEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import static core.commons.Utils.covertDateToLocalDateTime;

/**
 * Created by byaxe on 18.12.16.
 */
@Component
public class OrdersEntityToDTOConverter implements Converter<OrdersEntity, IOrdersDTO> {

    @Override
    public IOrdersDTO convert(OrdersEntity source) {
        if (source == null) return null;

        IOrdersDTO target = new OrdersDTOImpl();

        target.setId(source.getId());
        target.setUuid(source.getUuid());
        target.setDtUpdate(source.getDtUpdate());

        target.setProfession(source.getProfession());
        target.setPayload(source.getPayload());
        target.setNumber(source.getNumber());
        target.setRank(source.getRank());
        target.setStarts(covertDateToLocalDateTime(source.getStarts()));

        return target;
    }
}
