package com.zdmoney.message.api.dto.sm;

import com.zdmoney.zdqd.util.StringUtils;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 发送短信请求
 * Created by Administrator on 2016/11/3.
 */
@Data
public class SendSmReqDto implements Serializable {

    private static final long serialVersionUID = 5594859588058272652L;

    @NotNull(message = "商户批次号不能为空")
    private String batchNo;//商户批次号

    private List<String> mobiles = new ArrayList<>(); //手机号列表

    @NotNull(message = "短信内容不能为空")
    private String sendMsg;         //短信内容

    private boolean isInstant;//是否立即发送

    private Date sendTime;//如果是非立即发送，需要发送时间

    private boolean isTimed;    //是否是定时发送的短信,发送mq的时候用

    private SmSource smSource;//来源，没有默认为LCB

    public SendSmReqDto() {
        this.smSource = SmSource.LCB;
    }

    public SendSmReqDto(SendSmReqDto sendDto, List<String> mobiles) {
        this(sendDto, mobiles, SmSource.LCB);
    }

    public SendSmReqDto(SendSmReqDto sendDto, List<String> mobiles, SmSource smSource) {
        this.mobiles = mobiles;
        this.sendMsg = sendDto.getSendMsg();
        this.isInstant = sendDto.isInstant();
        this.smSource = smSource;
    }

    public String getMobileStr() {
        return StringUtils.join(getMobiles(), ",");
    }

    @Override
    public String toString() {
        return "SendSmReqDto{" +
                "batchNo='" + batchNo + '\'' +
                ", mobiles=" + mobiles +
                ", sendMsg='" + sendMsg + '\'' +
                ", isInstant=" + isInstant +
                ", sendTime=" + sendTime +
                ", isTimed=" + isTimed +
                ", smSource=" + smSource +
                '}';
    }
}
