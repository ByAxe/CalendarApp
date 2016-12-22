/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe). All rights reserved.
 */

package repository;

import model.entity.NotificationsLogEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by byaxe on 20.12.16.
 */
@Repository
public interface NotificationsLogRepository extends PagingAndSortingRepository<NotificationsLogEntity, Long> {

    List<NotificationsLogEntity> findByViewedTrue();

    List<NotificationsLogEntity> findByViewedFalse();

    long countFindByViewedFalse();

}
