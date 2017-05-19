package com.zdmoney.message.api.dto.push;

import com.zdmoney.message.api.base.BaseDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 推送任务DTO对象
 * Created by gaojc on 2016/7/18.
 */
@Data
@NoArgsConstructor
public class MsgTaskDto extends BaseDto {
    //任务号
    private String taskNo;
    //批次号
    private String batchNo;
    //第三方任务号
    private String thirdTaskNo;
    //是否全员推送
    private Boolean isAllpush;
    //标题
    private String title;
    //内容
    private String content;
    //推送时间
    private Date pushTime;
    //是否立即推送
    private Boolean isInstant;
    //推送数量
    private Integer pushNum;
    //成功数
    private Integer succeedNum;
    //到达数
    private Integer arriverNum;
    //点击数
    private Integer clickNum;
    //返回码
    private String retCode;
    //设备类型
    private String deviceType;
    //任务状态
    private String status;
    //返回报文
    private String retData;
    //推送开始时间
    private Date pushBeginTime;
    //推送结束时间
    private Date pushFinishTime;

}

