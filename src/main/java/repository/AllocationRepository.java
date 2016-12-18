package repository;

import model.entity.AllocationEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by byaxe on 18.12.16.
 */
@Repository
public interface AllocationRepository extends PagingAndSortingRepository<AllocationEntity, Long> {
}
