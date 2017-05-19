package com.zdmoney.message.api.dto.log;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/2/4.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LogDataReqDto implements Serializable {
    private static final long serialVersionUID = 6990663198116964691L;
    @NotNull(message = "系统不能为空")
    private String systemName;
    @NotNull(message = "来源不能为空")
    private String methodName;
    @NotNull(message = "开始时间不能为空")
    private Date startTime;
    @NotNull(message = "结束时间不能为空")
    private Date endTime;
    private String accessMsg;    //访问日志信息
}
