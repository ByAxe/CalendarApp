/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

package com.core.api;

/**
 * Created by byaxe on 25.12.16.
 * <p>
 * Все кто имплементит этот интерфейс, будут выводиться на показ пользователю в списки/таблицы и так далее
 * Следовательно, они должны обладать методом для красивого и информативного вывода информации о себе
 */
public interface IVisible {
    String toPrettyString();
}
