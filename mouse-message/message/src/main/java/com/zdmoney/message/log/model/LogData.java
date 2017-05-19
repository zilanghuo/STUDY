package com.zdmoney.message.log.model;

import com.zdmoney.message.api.common.dto.MessageOperator;
import com.zdmoney.message.api.dto.log.LogDataReqDto;
import com.zdmoney.message.common.base.BaseEntity;
import com.zdmoney.message.utils.CommonUtils;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by Administrator on 2017/2/4.
 */
@NoArgsConstructor
@Data
@Table(name = "T_LOG_DATA")
public class LogData extends BaseEntity {
    private static final String LOG_REGEX = "@[0-9]+[a-zA-Z]+[0-9]+";

    @NotNull(message = "系统不能为空")
    private String systemName;
    @NotNull(message = "方法不能为空")
    private String methodName;
    private Date startTime;
    private Date endTime;
    private long spendTime;         //耗时毫秒
    private String accessMsg;

    public LogData(LogDataReqDto logDataReqDto) {
        this.systemName = logDataReqDto.getSystemName();
        this.methodName = CommonUtils.processRegexText(logDataReqDto.getMethodName(), LOG_REGEX, ".");
        this.startTime = logDataReqDto.getStartTime();
        this.endTime = logDataReqDto.getEndTime();
        this.spendTime = this.endTime.getTime() - this.startTime.getTime();
        this.accessMsg = logDataReqDto.getAccessMsg();
        this.createTime = new Date();
        this.modifyTime = getCreateTime();
        this.operator = MessageOperator.SYS.name();
    }

}
