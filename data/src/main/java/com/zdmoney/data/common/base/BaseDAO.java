package com.zdmoney.data.common.base;

import com.zdmoney.zdqd.dao.EntityDAO;
import com.zdmoney.zdqd.dao.impl.mybatis.BaseSQLProvider;
import com.zdmoney.zdqd.model.Entity;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;

/**
 * Created by admin on 2016/7/15.
 */
public interface BaseDAO<T extends Entity> extends EntityDAO<T> {

    @InsertProvider(type = BaseSQLProvider.class, method = "insert")
    @Options(useGeneratedKeys = true)
    public int insert(T t);

}
