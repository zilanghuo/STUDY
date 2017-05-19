package com.zdmoney.message.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by lwf on 2017/5/18.
 */
@Getter
@AllArgsConstructor
public enum MessageDirection {

    INSERT("插入","1"),READ("读取","0");

    private String msg;

    private String value;
}
