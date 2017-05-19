package com.zdmoney.message.sm.model;

import com.zdmoney.message.api.dto.sm.SmChannelStatus;
import com.zdmoney.message.common.base.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Created by Administrator on 2016/11/7.
 * 短信渠道表
 */
@Data
@NoArgsConstructor
@Table(name = "T_SM_CHANNEL")
public class SmChannel extends BaseEntity {
    @NotNull(message = "编号不能为空")
    private String no;
    @NotNull(message = "名称不能为空")
    private String name;        //通道名称
    private int monthNumber;       //当月发送数量
    private int monthMaxnumber;    //每月最大可发送数量
    private SmChannelStatus status;        //通道状态

    public SmChannel(String no) {
        this.no = no;
    }

}
