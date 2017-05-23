package com.mouse.core.service;

import com.mouse.common.Entity;
import com.mouse.core.dao.EntityDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by lwf on 2017/5/23.
 */
public class EntityServiceImpl<T extends Entity> implements EntityService<T> {

    @Autowired
    private EntityDao entityDao;

    @Override
    public List<T> getAll() {
        return entityDao.getAll();
    }
}
