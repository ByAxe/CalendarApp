/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package repository;

import model.entity.AllocationEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by byaxe on 18.12.16.
 */
@Repository
public interface AllocationRepository extends PagingAndSortingRepository<AllocationEntity, Long> {

    List<AllocationEntity> findByArchiveTrue();

    List<AllocationEntity> findByArchiveFalse();
}
