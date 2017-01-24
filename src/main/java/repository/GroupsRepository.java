/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package repository;

import model.entity.GroupsEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Created by byaxe on 26.11.16.
 */
@Repository
public interface GroupsRepository extends PagingAndSortingRepository<GroupsEntity, Long> {

    GroupsEntity findByUuid(UUID uuid);

}
