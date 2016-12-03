package service.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;

/**
 * Created by byaxe on 26.11.16.
 */
public interface IEssenceService<T, UUID extends Serializable> {

    T save(T entity);

    Iterable<T> save(Iterable<T> entities);

    Iterable<T> findAll(Sort sort);

    Page<T> findAll(Pageable pageable);

    Iterable<T> findAll();

    Iterable<T> findAll(Iterable<UUID> uuids);

    T findOne(UUID uuid);

    long count();

    boolean exists(UUID uuid);

    void delete(UUID uuid);

    void delete(T entity);

    void delete(Iterable<? extends T> entities);

    void deleteAll();
}
