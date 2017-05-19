package com.zdmoney.message.api.dto.message;

import com.zdmoney.message.api.base.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 消息对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MsgMessageDto extends BaseDto {
    //商户流水号
    private String merchantSerialNo;
    //用户id
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

    public String getMessageTypeShow(){
        return type.getMsg();
    }
    public String getMessageStatusShow(){
        return status.getMsg();
    }
}

