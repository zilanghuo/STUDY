package com.zdmoney.message.api.dto.sm;

import com.zdmoney.message.api.common.dto.MessagePageSearchDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 短信查询dto
 * Created by gaojc on 2016/11/3.
 */
@Data
@NoArgsConstructor
public class SmSearchDto extends MessagePageSearchDto {
    private static final long serialVersionUID = 343916965406217666L;
    private String batchNo;         //批次号
    private String mobile;    //手机号
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
    private Integer status;          //状态
}
