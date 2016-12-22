/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe). All rights reserved.
 */

package service.api;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by byaxe on 17.12.16.
 */
public interface IConversionService<T, S> {
    /**
     * Конвертируем DTO в Сущность
     *
     * @param dto Исходный DTO
     * @return Сущность
     */
    S convertDtoToEntity(T dto);

    /**
     * Конвертируем Сущность в DTO
     *
     * @param entity Исходная сущность
     * @return DTO
     */
    T convertEntityToDto(S entity);

    /**
     * Конвертация списка DTO в список Сущностей
     *
     * @param dtoList Список DTO
     * @return Список Сущсностей
     */
    default List<S> convertListDtoToEntity(Iterable<T> dtoList) {
        return ((Collection<T>) dtoList)
                .stream()
                .map(this::convertDtoToEntity)
                .collect(toList());
    }

    /**
     * Конвертация списка Сущностей в список DTO
     *
     * @param entities Список Сущностей
     * @return Список DTO
     */
    default List<T> convertListEntityToDto(Iterable<S> entities) {
        return ((Collection<S>) entities)
                .stream()
                .map(this::convertEntityToDto)
                .collect(toList());
    }
}
