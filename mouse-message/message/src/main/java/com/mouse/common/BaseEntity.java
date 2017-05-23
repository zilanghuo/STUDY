package com.mouse.common;

import lombok.Data;

import java.util.Date;

/**
 * Created by lwf on 2017/5/23.
 */
@Data
public class BaseEntity extends Entity {
    protected String id;

    protected Date createTime;

    protected Date modifyTime;

    protected String operator;

    @Override
    public Object getId() {
        return id;
    }

    @Override
    public void setId(Object id) {
        if (id instanceof String) {
            this.id = (String) id;
        } else if (id == null) {
            this.id = null;
        }
    }
}
