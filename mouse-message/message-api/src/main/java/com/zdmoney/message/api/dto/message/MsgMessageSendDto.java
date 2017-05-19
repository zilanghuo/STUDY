package com.zdmoney.message.api.dto.message;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by laiwufa on 2016/7/18.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MsgMessageSendDto implements Serializable {
    private static final long serialVersionUID = -8600430803305245401L;
    //流水号
    @NotNull(message = "商户流水号不能为空")
    private String merchantSerialNo;

    //题目
    @NotNull(message = "消息题目不能为空")
    private String title;

    //摘要
    @NotNull(message = "消息摘要不能为空")
    private String summary;

    //类型
    @NotNull(message = "消息类别不能为空")
    private Integer type;

    //内容
    @NotNull(message = "消息内容不能为空")
    private String content;

    @NotNull(message = "回调地址不能为空")
    private String callbackUrl;

    //用户集合
    private List<UserDto> users = new ArrayList<>();

    public MsgMessageSendDto(MsgMessageSendDto sendDto, List<UserDto> subUsers) {
        this.users = subUsers;
        this.merchantSerialNo = sendDto.getMerchantSerialNo();
        this.title = sendDto.getTitle();
        this.summary = sendDto.getSummary();
        this.type = sendDto.getType();
        this.content = sendDto.getContent();
        this.callbackUrl = sendDto.getCallbackUrl();
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserDto implements Serializable {
        //用户id
        private String userId;

        //用户编号
        private String userNo;

        //姓名
        private String userName;

        //手机号
        private String userPhone;

        public UserDto(String userId, String userNo, String userPhone) {
            this.userId = userId;
            this.userNo = userNo;
            this.userPhone = userPhone;
        }

    }
}
