package service.api;

import core.dto.api.IOrdersDTO;
import model.entity.OrdersEntity;

/**
 * Created by byaxe on 18.12.16.
 */
public interface IOrdersService extends IEssenceService<IOrdersDTO, Long>, IConversionService<IOrdersDTO, OrdersEntity> {
}
