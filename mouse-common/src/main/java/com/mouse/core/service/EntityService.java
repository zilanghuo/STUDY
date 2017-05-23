package com.mouse.core.service;

import com.mouse.common.Entity;

import java.util.List;

/**
 * Created by lwf on 2017/5/23.
 */
public interface EntityService<T extends Entity> {

    List<T> getAll();
}
