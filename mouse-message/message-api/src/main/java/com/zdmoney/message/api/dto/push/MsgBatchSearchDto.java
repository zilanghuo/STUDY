package com.zdmoney.message.api.dto.push;


import com.zdmoney.message.api.common.dto.MessagePageSearchDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 批次查询
 */
@Data
@NoArgsConstructor
public class MsgBatchSearchDto extends MessagePageSearchDto {
    /**
     * 批次号
     */
    private String batchNo;

    /**
     * 商户流水号
     */
    private String serialNo;

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
     * 推送结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date pushEndDate;

    public MsgBatchSearchDto(String batchNo, Date createStartDate, Date createEndDate, Date pushStartDate, Date pushEndDate) {
        this.batchNo = batchNo;
        this.createStartDate = createStartDate;
        this.createEndDate = createEndDate;
        this.pushStartDate = pushStartDate;
        this.pushEndDate = pushEndDate;
    }
}
