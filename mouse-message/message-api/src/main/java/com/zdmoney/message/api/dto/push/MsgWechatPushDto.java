package com.zdmoney.message.api.dto.push;

import com.zdmoney.message.api.base.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * 微信消息推送请求对象
 * Created by gaojc on 2016/8/22.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MsgWechatPushDto extends BaseDto {
    @NotNull(message = "openId不能为空")
    String openId;

    @NotNull(message = "模板不能为空")
    String tmlShortId;

    String url;

    @NotNull(message = "请求参数不能为空")
    Map<String, String> params;

}

