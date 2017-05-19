package com.zdmoney.message.api.dto.sm;

import com.zdmoney.zdqd.util.StringUtils;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/12.
 */
@Data
@NoArgsConstructor
public class SendSmCommonReqDto implements Serializable {

    private static final long serialVersionUID = -36897799516376869L;

    private List<String> mobiles = new ArrayList<>();

    private String mobile;

    private String sendMsg;

    private String verifyCode;

    private SendSmType sendSmType;//类型

    private String batchNo;

    private boolean isInstant;//是否立即发送

    public SendSmCommonReqDto(List<String> mobiles, String sendMsg, SendSmType sendSmType, String batchNo) {
        this.mobiles = mobiles;
        this.verifyCode = "";
        this.sendMsg = sendMsg;
        this.sendSmType = sendSmType;
        this.batchNo = batchNo;
    }

    public SendSmCommonReqDto(String mobile, String sendMsg, SendSmType sendSmType) {
        this.mobile = mobile;
        this.verifyCode = "";
        this.sendMsg = sendMsg;
        this.sendSmType = sendSmType;
        this.mobiles.clear();
        this.mobiles.add(mobile);
    }

    public SendSmCommonReqDto(String mobile, String sendMsg, String verifyCode, SendSmType sendSmType) {
        this.mobile = mobile;
        this.verifyCode = verifyCode;
        this.sendMsg = sendMsg;
        this.sendSmType = sendSmType;
        this.mobiles.clear();
        this.mobiles.add(mobile);
    }

    public String getMobileStr() {
        return StringUtils.join(mobiles, ",");
    }

}
