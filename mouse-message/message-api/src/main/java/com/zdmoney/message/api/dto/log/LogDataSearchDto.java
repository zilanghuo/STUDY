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
public class LogDataSearchDto extends MessagePageSearchDto {
    private static final long serialVersionUID = -2733032825205501528L;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    private String systemName;
    private String methodName;

}
