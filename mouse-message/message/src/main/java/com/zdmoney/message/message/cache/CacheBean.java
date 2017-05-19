package com.zdmoney.message.message.cache;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/2/21.
 */
@Data
@NoArgsConstructor
public class CacheBean implements Serializable{
    private String key;//对应的用户id
    private Integer value;//对应的当前redis的值

    public CacheBean(String key, Integer value) {
        this.key = key;
        this.value = value;
    }
}
