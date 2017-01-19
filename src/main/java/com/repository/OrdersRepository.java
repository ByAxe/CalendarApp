/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package com.repository;

import com.model.entity.OrdersEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Created by byaxe on 18.12.16.
 */
@Repository
public interface OrdersRepository extends PagingAndSortingRepository<OrdersEntity, Long> {
    OrdersEntity findByUuid(UUID uuid);
}
