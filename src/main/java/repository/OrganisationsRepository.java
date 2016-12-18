package repository;

import model.entity.OrganisationsEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by byaxe on 18.12.16.
 */
@Repository
public interface OrganisationsRepository extends PagingAndSortingRepository<OrganisationsEntity, Long> {
}
