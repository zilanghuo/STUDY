package com.zdmoney.message.api.dto.message;

import com.zdmoney.message.api.common.dto.MessagePageSearchDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 消息对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MsgMessageSearchDto extends MessagePageSearchDto {
    //商户流水号
    private String merchantSerialNo;
    //标题
    private String title;
    //摘要
    private String summary;
    //内容
    private String content;
    //状态
    private Integer status;
    //用户编号
    private String userNo;
    //用户id
    private String userId;
    //类型
    private MsgMessageType type;
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

}

