package com.zdmoney.message.api.dto.push;

import com.zdmoney.message.api.base.BaseDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 手机号DTO对象
 * Created by gaojc on 2016/7/18.
 */
@Data
@NoArgsConstructor
public class MsgPhoneDto extends BaseDto {
    @NotNull(message = "批次号不能为空")
    private String batchNo;         //批次号

    private String taskNo;          //任务号

    private String cellphoneNum;    //手机号

    private String status;          //状态

    private String retMsg;          //返回信息

    private Date pushTime;          //推送时间

    private String deviceId;        //设备令牌

    private String deviceType;      //设备类型

    public MsgPhoneDto(String cellphoneNum, String taskNo) {
        this.cellphoneNum = cellphoneNum;
        this.taskNo = taskNo;
    }

}

