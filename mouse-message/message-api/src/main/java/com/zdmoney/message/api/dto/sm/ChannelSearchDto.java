package com.zdmoney.message.api.dto.sm;

import com.zdmoney.message.api.common.dto.MessagePageSearchDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * modified by gaojc on 2016/11/7.
 */
@Data
@NoArgsConstructor
public class ChannelSearchDto extends MessagePageSearchDto {
    private static final long serialVersionUID = -7591324003243612263L;
    private String name;//名称
    private Integer status;//状态
    /**
     * 修改开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifyStartDate;

    /**
     * 修改结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifyEndDate;
}
