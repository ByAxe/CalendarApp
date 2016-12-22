/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe). All rights reserved.
 */

package repository;

import model.entity.PreferencesEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by byaxe on 20.12.16.
 */
public interface PreferencesRepository extends PagingAndSortingRepository<PreferencesEntity, Long> {
}
