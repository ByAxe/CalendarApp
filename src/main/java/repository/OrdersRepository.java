/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe). All rights reserved.
 */

package repository;

import model.entity.OrdersEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by byaxe on 18.12.16.
 */
@Repository
public interface OrdersRepository extends PagingAndSortingRepository<OrdersEntity, Long> {
}
