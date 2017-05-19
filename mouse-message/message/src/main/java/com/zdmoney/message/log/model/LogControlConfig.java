package com.zdmoney.message.log.model;

import com.zdmoney.message.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * 日志开关配置
 * Created by user on 2017/2/4.
 */
@Data
@NoArgsConstructor
@Table(name = "T_LOG_CONFIG")
@EqualsAndHashCode(callSuper = false)
public class LogControlConfig extends BaseEntity {
    @NotNull(message = "设置键不能为空")
    private String settingKey;     //日志统计
    private Boolean isLog;         //是否开启日志记录

    public LogControlConfig(String setSettingKey) {
        this.settingKey = setSettingKey;
    }
}
