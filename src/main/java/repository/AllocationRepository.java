/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe). All rights reserved.
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
