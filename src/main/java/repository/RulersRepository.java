package repository;

import model.entity.RulersEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by byaxe on 17.12.16.
 */
public interface RulersRepository extends PagingAndSortingRepository<RulersEntity, Long> {
}
