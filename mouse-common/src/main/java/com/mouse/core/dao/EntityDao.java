package com.mouse.core.dao;

import com.mouse.common.Entity;
import com.mouse.core.dao.impl.mybatis.BaseSQLProvider;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * Created by lwf on 2017/5/23.
 */
public interface EntityDao<T extends Entity> {

    @SelectProvider(type = BaseSQLProvider.class, method = "getAll")
    @ResultMap("getMap")
    List<T> getAll();
}
