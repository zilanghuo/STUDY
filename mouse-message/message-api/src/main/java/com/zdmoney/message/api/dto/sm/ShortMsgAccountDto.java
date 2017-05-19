package com.zdmoney.message.api.dto.sm;

import com.zdmoney.message.api.base.BaseDto;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Administrator on 2016/11/3.
 * 短信账户
 */
@Data
@NoArgsConstructor
public class ShortMsgAccountDto extends BaseDto {

    private static final long serialVersionUID = -8860862201947481783L;
    private String dockingMode;//对接方式http
    private String userName;//用户名
    private String password;//密码
    private String serverAddress;//服务器地址
    private String serverPort;
    private String rate;//速率
    private String words;//字数
    private String signature;//签名
    private String accessNumber;//接入号

}
