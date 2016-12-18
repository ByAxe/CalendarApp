package repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by byaxe on 18.12.16.
 */
@Repository
public interface StudentsRepository extends PagingAndSortingRepository<StudentsRepository, Long> {
}
