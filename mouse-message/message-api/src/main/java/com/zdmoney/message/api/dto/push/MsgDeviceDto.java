package com.zdmoney.message.api.dto.push;


import com.zdmoney.message.api.base.BaseDto;
import lombok.Data;
import lombok.NoArgsConstructor;

/**设备dto
 * Created by admin on 2016/7/12.
 */
@Data
@NoArgsConstructor
public class MsgDeviceDto extends BaseDto {
    //手机号
    private String cellphoneNum;
    //设备号
    private String deviceId;
    //设备类型
    private String deviceType;

}
