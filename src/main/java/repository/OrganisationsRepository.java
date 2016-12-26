/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package repository;

import model.entity.OrganisationsEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Created by byaxe on 18.12.16.
 */
@Repository
public interface OrganisationsRepository extends PagingAndSortingRepository<OrganisationsEntity, Long> {
    OrganisationsEntity findByUuid(UUID uuid);
}
