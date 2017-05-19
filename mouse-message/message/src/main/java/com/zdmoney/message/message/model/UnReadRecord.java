package com.zdmoney.message.message.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * message未读数量
 * Created by user on 2017/2/24.
 */
@Data
@NoArgsConstructor
public class UnReadRecord implements Serializable {
    private String cKey;//对应的用户id
    private Integer cValue;//对应的unreadCount

    public UnReadRecord(String key, Integer value) {
        this.cKey = key;
        this.cValue = value;
    }
}
