/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package service.api;

import core.api.IEssence;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;

/**
 * Created by byaxe on 26.11.16.
 */
public interface IEssenceService<T extends IEssence, ID extends Serializable> {

    T save(T dto);

    Iterable<T> save(Iterable<T> dtoList);

    Iterable<T> findAll(Sort sort);

    Page<T> findAll(Pageable pageable);

    Iterable<T> findAll();

    Iterable<T> findAll(Iterable<ID> idList);

    T findOne(ID id);

    long count();

    boolean exists(ID id);

    void delete(ID id);

    void delete(T dto);

    void delete(Iterable<? extends T> dtoList);

    void deleteAll();
}
