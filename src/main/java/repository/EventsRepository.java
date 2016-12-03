package repository;

import model.entity.EventsEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by byaxe on 26.11.16.
 */
@Repository
public interface EventsRepository extends PagingAndSortingRepository<EventsEntity, Long> {
}
