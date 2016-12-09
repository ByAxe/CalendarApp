package repository;

import model.entity.EventsEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by byaxe on 26.11.16.
 */
@Repository
public interface EventsRepository extends PagingAndSortingRepository<EventsEntity, Long> {

    @Query(value = "SELECT * FROM cld.events WHERE starts >= CURRENT_TIMESTAMP(2) ORDER BY starts", nativeQuery = true)
    List<EventsEntity> findUpcomingEvents();

}
