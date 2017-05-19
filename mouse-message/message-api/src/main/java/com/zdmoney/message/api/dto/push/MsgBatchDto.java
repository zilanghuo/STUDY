package com.zdmoney.message.api.dto.push;

import com.zdmoney.message.api.base.BaseDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 推送批次DTO对象
 * Created by gaojc on 2016/7/18.
 */
@Data
@NoArgsConstructor
public class MsgBatchDto extends BaseDto {
    //批次号
    private String batchNo;
    //商户流水号
    private String serialNo;
    //推送时间
    private Date pushTime;
    //是否立即推送
    private Boolean isInstant;
    //是否全员推送
    private Boolean isAllpush;
    //推送数量
    private Integer pushNum;
    //成功数
    private Integer succeedNum;
    //到达数
    private Integer arriverNum;
    //点击数
    private Integer clickNum;
    //通知栏目标题
    private String title;
    //通知栏内容
    private String content;
    //透传消息内容
    private String data;

}

