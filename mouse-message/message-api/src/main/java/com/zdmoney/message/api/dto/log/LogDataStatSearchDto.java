package com.zdmoney.message.api.dto.log;

import com.zdmoney.message.api.common.dto.MessagePageSearchDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by Administrator on 2017/2/4.
 */
@NoArgsConstructor
@Data
public class LogDataStatSearchDto extends MessagePageSearchDto {
    private static final long serialVersionUID = -3109554101604546867L;

    /**
     * 创建开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDate;
    /**
     * 创建开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;
    private long fromSpendTime;
    private long toSpendTime;
    private String sysName;
    private String methodName;  //方法
    private LogStatRate statRate;    //统计频率

}
