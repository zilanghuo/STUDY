package com.zdmoney.message.api.dto.sm;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/3.
 */
@Data
@NoArgsConstructor
public class ChannelReqDto implements Serializable {
    private static final long serialVersionUID = -2205348395832384144L;
    @NotNull(message = "渠道编号不能为空")
    private String channelNo;//传值为渠道编号

    private boolean resetMonthNum;//是否充值月发送数量

    public ChannelReqDto(String channelNo) {
        this.channelNo = channelNo;
        resetMonthNum = false;
    }

    public ChannelReqDto(String channelNo, boolean resetMonthNum) {
        this.channelNo = channelNo;
        this.resetMonthNum = resetMonthNum;
    }
}
