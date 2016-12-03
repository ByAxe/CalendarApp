package service.impl;

import core.dto.api.IGroupsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import service.api.IGroupsService;

/**
 * Created by byaxe on 26.11.16.
 */
@Service
public class GroupsServiceImpl implements IGroupsService {

    @Override
    public IGroupsDTO save(IGroupsDTO entity) {
        return null;
    }

    @Override
    public Iterable<IGroupsDTO> save(Iterable<IGroupsDTO> entities) {
        return null;
    }

    @Override
    public Iterable<IGroupsDTO> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<IGroupsDTO> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Iterable<IGroupsDTO> findAll() {
        return null;
    }

    @Override
    public Iterable<IGroupsDTO> findAll(Iterable<String> ids) {
        return null;
    }

    @Override
    public IGroupsDTO findOne(String uuid) {
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
    public void delete(IGroupsDTO entity) {

    }

    @Override
    public void delete(Iterable<? extends IGroupsDTO> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
