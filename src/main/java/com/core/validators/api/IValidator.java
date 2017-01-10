/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package com.core.validators.api;


import com.core.api.IEssence;
import com.core.commons.Result;

/**
 * Created by byaxe on 25.12.16.
 */
public interface IValidator<R extends Result, N extends IEssence> {

    /**
     * Валидирует новое событие {@link N}
     *
     * @param n Новое событие {@link N}
     * @return Экземпляр результата {@link R}
     */
    R validate(N n);

}
