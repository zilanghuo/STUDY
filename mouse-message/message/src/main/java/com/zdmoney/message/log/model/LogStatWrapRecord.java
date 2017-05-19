package com.zdmoney.message.log.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by user on 2017/2/7.
 */
@Data
@NoArgsConstructor
public class LogStatWrapRecord implements Serializable {
    private String systemName;
    private String methodName;  //方法
    private Long avgSpendTime;  //平均耗时(毫秒)
    private int amount;         //记录数
    private Date statDate;          //日期

}
