package com.zdmoney.message.sm.model;

import com.zdmoney.message.api.common.dto.MessageOperator;
import com.zdmoney.message.api.dto.sm.SmChannelType;
import com.zdmoney.message.common.base.BaseEntity;
import lombok.Data;

import javax.persistence.Table;
import java.util.Date;

/**
 * 发送短信统计表
 * Created by gaojc on 2016/11/9.
 */
@Data
@Table(name = "T_SM_STATIS")
public class SmStat extends BaseEntity {
    //通道
    private SmChannelType smChannelType;
    //月份 例如:201611
    private String month;
    //天 例如01
    private String day;
    //当日发送数量
    private Integer sendNum;
    //当日发送成功数量
    private Integer sendSucceedNum;
    //当日发送失败数量
    private Integer sendFailedNum;
    //备注
    private String remark;

    public SmStat() {
        this.createTime = new Date();
        this.modifyTime = createTime;
        this.operator = MessageOperator.SYS.name();
    }

    public SmStat(String month, String day) {
        this.month = month;
        this.day = day;
    }

}
