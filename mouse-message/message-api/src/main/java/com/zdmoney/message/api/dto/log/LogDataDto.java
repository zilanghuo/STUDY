package com.zdmoney.message.api.dto.log;

import com.zdmoney.message.api.base.BaseDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created by Administrator on 2017/2/4.
 */
@Data
@NoArgsConstructor
public class LogDataDto extends BaseDto {
    private static final long serialVersionUID = -2140260541166299851L;
    private String systemName;
    private String methodName;
    private Date startTime;
    private Date endTime;
    private long spendTime;//毫秒
    private String accessMsg; //备注信息

}
