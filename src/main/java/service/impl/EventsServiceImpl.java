package service.impl;

import core.dto.api.IEventsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import service.api.IEventsService;

/**
 * Created by byaxe on 26.11.16.
 */
@Service
public class EventsServiceImpl implements IEventsService {

    @Override
    public IEventsDTO save(IEventsDTO entity) {
        return null;
    }

    @Override
    public Iterable<IEventsDTO> save(Iterable<IEventsDTO> entities) {
        return null;
    }

    @Override
    public Iterable<IEventsDTO> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<IEventsDTO> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Iterable<IEventsDTO> findAll() {
        return null;
    }

    @Override
    public Iterable<IEventsDTO> findAll(Iterable<String> ids) {
        return null;
    }

    @Override
    public IEventsDTO findOne(String s) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public boolean exists(String s) {
        return false;
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public void delete(IEventsDTO entity) {

    }

    @Override
    public void delete(Iterable<? extends IEventsDTO> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
