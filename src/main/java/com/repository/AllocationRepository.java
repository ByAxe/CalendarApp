/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package com.repository;

import com.model.entity.AllocationEntity;
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

    @Query(value = "SELECT cld.allocation.*\n" +
            "FROM cld.allocation\n" +
            "  JOIN cld.students ON cld.allocation.student_id = cld.students.id\n" +
            "  JOIN cld.groups ON cld.students.group_id = cld.groups.id\n" +
            "WHERE cld.groups.stage = :stage\n" +
            "      AND cld.allocation.archive = :archive\n" +
            "      AND cld.groups.issue_year = :issueYear\n" +
            "      AND cld.groups.issue_month = :issueMonth\n" +
            "ORDER BY cld.allocation.id", nativeQuery = true)
    List<AllocationEntity> find(@Param("stage") String stage,
                                @Param("archive") boolean archive,
                                @Param("issueYear") Integer issueYear,
                                @Param("issueMonth") Integer issueMonth);
}
