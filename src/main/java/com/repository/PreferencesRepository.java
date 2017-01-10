/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package com.repository;

import com.model.entity.PreferencesEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by byaxe on 20.12.16.
 */
public interface PreferencesRepository extends PagingAndSortingRepository<PreferencesEntity, Long> {
}
