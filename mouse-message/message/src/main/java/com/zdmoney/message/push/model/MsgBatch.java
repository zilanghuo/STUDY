package com.zdmoney.message.push.model;

import com.zdmoney.message.api.common.dto.MessageOperator;
import com.zdmoney.message.api.dto.push.MsgPushDto;
import com.zdmoney.message.common.base.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;
import java.util.Date;

/**
 * 推送消息批次表
 * Created by gaojc on 2016/7/15.
 */
@Data
@NoArgsConstructor
@Table(name = "T_MSG_BATCH")
public class MsgBatch extends BaseEntity {
    //批次号
    private String batchNo;
    //商户流水号
    private String serialNo;
    //推送时间
    private Date pushTime;
    //是否立即推送
    private Boolean isInstant;
    //推送数量
    private Integer pushNum;
    //通知栏标题
    private String title;
    //通知栏内容
    private String content;
    //消息内容
    private String data;
    //成功数
    private Integer succeedNum;
    //到达数
    private Integer arriverNum;
    //点击数
    private Integer clickNum;
    //是否全员推送
    private Boolean isAllpush;

    public MsgBatch(String batchNo) {
        this.batchNo = batchNo;
    }

    public MsgBatch(Boolean isAllpush) {
        this.isAllpush = isAllpush;
    }

    public MsgBatch(String batchNo, MsgPushDto msgPushDto) {
        this.batchNo = batchNo;
        this.serialNo = msgPushDto.getSerialNo();
        this.pushNum = msgPushDto.size();
        this.isAllpush = msgPushDto.getIsAllpush();
        this.isInstant = msgPushDto.getIsInstant();
        this.pushTime = msgPushDto.getIsInstant() ? new Date() :
                (msgPushDto.getPushTime() == null ? new Date() : msgPushDto.getPushTime());
        this.title = msgPushDto.getTitle();
        this.content = msgPushDto.getContent();
        this.data = msgPushDto.getData();
        this.pushNum = this.isAllpush ? null : msgPushDto.getPhones().size();
        this.createTime = new Date();
        this.modifyTime = createTime;
        this.operator = MessageOperator.SYS.name();
    }

}
