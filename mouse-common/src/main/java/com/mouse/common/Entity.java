package com.mouse.common;


import lombok.Data;

import java.io.Serializable;

/**
 * Created by lwf on 2017/5/23.
 */
@Data
public abstract class Entity implements Serializable {

    private static final long serialVersionUID = 4212679023438415647L;

    public abstract Object getId();

    public abstract void setId(Object id);

}
