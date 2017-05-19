package com.zdmoney.message.log.model;

import com.zdmoney.message.api.common.dto.MessageOperator;
import com.zdmoney.message.common.base.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 日志统计 /每日
 * Created by user on 2017/2/4.
 */
@Data
@NoArgsConstructor
@Table(name = "T_LOG_STAT_DAY")
public class LogDataStatDay extends BaseEntity {
    @NotNull(message = "系统不能为空")
    private String systemName;
    @NotNull(message = "方法不能为空")
    private String methodName;  //方法
    private Long avgSpendTime;  //平均耗时(毫秒)
    private int amount;         //记录数
    private Date statDate;          //统计日期

    public LogDataStatDay(String systemName, String methodName, Date statDate) {
        this.systemName = systemName;
        this.methodName = methodName;
        this.statDate = statDate;
        this.createTime=new Date();
        this.modifyTime=getCreateTime();
        this.operator= MessageOperator.SYS.name();
    }

    public LogDataStatDay(Date date) {
        this.statDate = date;
    }
}
