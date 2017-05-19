package com.zdmoney.message.api.dto.push;

import com.zdmoney.message.api.common.dto.MessagePageSearchDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 手机号任务DTO对象
 * Created by gaojc on 2016/7/18.
 */
@Data
@NoArgsConstructor
public class MsgPhoneSearchDto extends MessagePageSearchDto {

    private String batchNo;//批次号

    private String cellphoneNum;//手机号

    /**
     * 创建开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createStartDate;

    /**
     * 创建结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createEndDate;

    /**
     * 推送开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date pushStartDate;

    /**
     * 推送开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date pushEndDate;

}

