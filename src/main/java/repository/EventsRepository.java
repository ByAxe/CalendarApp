/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package repository;

import model.entity.EventsEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by byaxe on 26.11.16.
 */
@Repository
public interface EventsRepository extends PagingAndSortingRepository<EventsEntity, Long> {

    @Query(value = "SELECT * FROM cld.events WHERE ends >= CURRENT_TIMESTAMP(2) ORDER BY starts LIMIT 20", nativeQuery = true)
    List<EventsEntity> findUpcomingEvents();

    @Query(value = "SELECT * FROM cld.events WHERE ends >= CURRENT_TIMESTAMP(2) AND starts BETWEEN ?1 AND ?2 ORDER BY starts LIMIT 20", nativeQuery = true)
    List<EventsEntity> findUpcomingEventsForPeriod(Date starts, Date ends);

    Long deleteByUuid(UUID uuid);

    EventsEntity findByUuid(UUID uuid);
}
