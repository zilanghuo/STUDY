package com.zdmoney.message.api.dto.push;


import com.zdmoney.message.api.common.dto.MessagePageSearchDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**设备查询dto
 * Created by laiwufa on 2016/7/18.
 */
@Data
@NoArgsConstructor
public class MsgDeviceSearchDto extends MessagePageSearchDto {
    /**
     * 手机号
     */
    private String cellphoneNum;

    /**
     *设备类型
     */
    private String deviceType;

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

    public MsgDeviceSearchDto(String cellphoneNum,String deviceType,Date startDate,Date endDate){
        this.cellphoneNum = cellphoneNum;
        this.deviceType = deviceType;
    }
}
