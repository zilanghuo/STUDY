package com.zdmoney.message.api.dto.push;

import com.zdmoney.message.api.base.BaseDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 消息推送请求对象
 * Created by gaojc on 2016/7/18.
 */
@Data
@NoArgsConstructor
public class MsgPushDto extends BaseDto {

    @NotNull(message = "请求流水号不能为空")
    private String serialNo;//商户流水号(批次号)

    private List<String> phones = new ArrayList<>(); //手机号列表

    private Date pushTime = new Date();//推送时间

    @NotNull(message = "是否立即推送不能为空")
    private Boolean isInstant;//是否立即推送

    @NotNull(message = "通知栏标题不能为空")
    private String title; //通知栏标题

    @NotNull(message = "通知栏内容不能为空")
    private String content; //通知栏内容

    private String data; //消息内容

    @NotNull(message = "是否全员推送不能为空")
    private Boolean isAllpush;//是否全员推送

    public int size() {
        return phones.size();
    }
}

