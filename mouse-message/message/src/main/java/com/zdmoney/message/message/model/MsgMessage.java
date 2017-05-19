package com.zdmoney.message.message.model;

import com.zdmoney.message.api.common.dto.MessageOperator;
import com.zdmoney.message.api.dto.message.MsgMessageSendDto;
import com.zdmoney.message.api.dto.message.MsgMessageStatus;
import com.zdmoney.message.api.dto.message.MsgMessageType;
import com.zdmoney.message.common.base.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;
import java.util.Date;

/**
 * 消息表
 */
@Data
@NoArgsConstructor
@Table(name = "T_MSG_MESSAGE")
public class MsgMessage extends BaseEntity {
    //商户流水号
    private String merchantSerialNo;

    //用户编号
    private String userId;

    //用户编号
    private String userNo;

    //用户姓名
    private String userName;

    //用户手机号
    private String userPhone;
    //标题
    private String title;
    //摘要
    private String summary;
    //内容
    private String content;
    //类型
    private MsgMessageType type;
    //状态
    private MsgMessageStatus status;

    public MsgMessage(MsgMessageSendDto sendDto) {
        this.merchantSerialNo = sendDto.getMerchantSerialNo();
        this.title = sendDto.getTitle();
        this.summary = sendDto.getSummary();
        this.content = sendDto.getContent();
        this.type = MsgMessageType.getMessageType(sendDto.getType());
        this.status = MsgMessageStatus.UN_READ;
        this.createTime = new Date();
        this.modifyTime = createTime;
        this.operator = MessageOperator.SYS.name();
    }

    public MsgMessage(String userId, MsgMessageStatus status) {
        this.userId = userId;
        this.status = status;
    }
}
