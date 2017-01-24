/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package core.validators.api;


import core.api.IEssence;
import core.commons.Result;

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
