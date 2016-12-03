package repository;

import model.entity.GroupsEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by byaxe on 26.11.16.
 */
@Repository
public interface GroupsRepository extends PagingAndSortingRepository<GroupsEntity, Long> {
}
