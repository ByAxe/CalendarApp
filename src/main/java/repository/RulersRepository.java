/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe). All rights reserved.
 */

package repository;

import model.entity.RulersEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by byaxe on 17.12.16.
 */
public interface RulersRepository extends PagingAndSortingRepository<RulersEntity, Long> {
}
