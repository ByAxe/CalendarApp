/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package repository;

import core.enums.Stage;
import model.entity.AllocationEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by byaxe on 18.12.16.
 */
@Repository
public interface AllocationRepository extends PagingAndSortingRepository<AllocationEntity, Long> {

    List<AllocationEntity> findByArchiveTrue();

    List<AllocationEntity> findByArchiveFalse();

    @Query(value = "SELECT a FROM AllocationEntity a WHERE a.stage = :stage AND a.archive = :archive AND a.issueYear = :issueYear ORDER BY a.id")
    List<AllocationEntity> find(@Param("stage") Stage stage, @Param("archive") boolean archive, @Param("issueYear") Integer issueYear);
}
