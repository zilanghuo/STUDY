package com.zdmoney.message.push.model;

import com.zdmoney.message.api.common.dto.MessageOperator;
import com.zdmoney.message.api.dto.push.enums.DeviceType;
import com.zdmoney.message.api.dto.push.enums.PushStatus;
import com.zdmoney.message.common.base.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;
import java.util.Date;

/**
 * 推送消息任务表
 * Created by gaojc on 2016/7/18.
 */
@Data
@NoArgsConstructor
@Table(name = "T_MSG_TASK")
public class MsgTask extends BaseEntity {
    //任务号
    private String taskNo;
    //批次号
    private String batchNo;
    //第三方任务号
    private String thirdTaskNo;
    //推送时间
    private Date pushTime;
    //是否全员推送
    private Boolean isAllpush;
    //是否立即推送
    private Boolean isInstant;
    //标题
    private String title;
    //内容
    private String content;
    //消息内容
    private String data;
    //推送数量
    private Integer pushNum;
    //推送开始时间
    private Date pushBeginTime;
    //推送结束时间
    private Date pushFinishTime;
    //成功数
    private Integer succeedNum;
    //到达数
    private Integer arriverNum;
    //点击数
    private Integer clickNum;
    //返回信息
    private String retMsg;
    //设备类型
    private DeviceType deviceType;
    //任务状态
    private PushStatus status;
    //返回报文
    private String retData;

    public MsgTask(String taskNo) {
        this.taskNo = taskNo;
    }

    public MsgTask(String taskNo, DeviceType deviceType, MsgBatch msgBatch) {
        this.taskNo = taskNo;
        this.batchNo = msgBatch.getBatchNo();
        this.deviceType = deviceType;
        this.title = msgBatch.getTitle();
        this.content = msgBatch.getContent();
        this.data = msgBatch.getData();
        this.pushTime = msgBatch.getPushTime();
        this.isAllpush = msgBatch.getIsAllpush();
        this.isInstant = msgBatch.getIsInstant();
        this.createTime = new Date();
        this.modifyTime = createTime;
        this.status = msgBatch.getIsInstant() ? PushStatus.PUSHING : PushStatus.WAITING;
        this.operator = MessageOperator.SYS.name();
    }

}
