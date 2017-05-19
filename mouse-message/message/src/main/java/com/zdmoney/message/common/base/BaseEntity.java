package com.zdmoney.message.common.base;

import com.zdmoney.zdqd.model.Entity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by rui on 15/8/24.
 */
@Data
public abstract class BaseEntity extends Entity {

    protected Integer id;

    protected Date createTime;

    protected Date modifyTime;

    protected String operator;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Object id) {
        if (id instanceof BigDecimal) {
            BigDecimal value =  (BigDecimal) id;
            this.id = value.intValue();
        }else if (id instanceof Integer) {
            this.id = (Integer)id;
        } else if (id == null) {
            this.id = null;
        }
    }
}
