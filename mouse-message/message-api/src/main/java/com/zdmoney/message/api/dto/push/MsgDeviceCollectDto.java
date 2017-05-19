package com.zdmoney.message.api.dto.push;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * /**设备请求DTO
 * Created by laiwufa on 2016/7/18.
 */
@Data
@NoArgsConstructor
public class MsgDeviceCollectDto implements Serializable {
    private static final long serialVersionUID = -9057013913167785540L;
    /**
     * 手机号
     */
    @NotNull(message = "手机号码不能为空")
    protected String cellphoneNum;
    /**
     * 设备号
     */
    @NotNull(message = "设备号不能为空")
    protected String deviceId;

    /**
     * 设备类型
     */
    @NotNull(message = "设备类型不能为空")
    protected String deviceType;

}
