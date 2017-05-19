package com.zdmoney.message.api.dto.sm;

import com.zdmoney.message.api.base.BaseDto;
import com.zdmoney.zdqd.util.StringUtils;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Administrator on 2016/11/7.
 */
@Data
@NoArgsConstructor
public class ChannelDto extends BaseDto {

    private static final long serialVersionUID = 8798522681661189006L;
    private String no;//编号
    private String name;//名称
    private int monthNumber;//当月发送数量
    private int monthMaxnumber;//每月最大可发送数量
    private SmChannelStatus status;

    public SmChannelType getSmChannelType() {
        if(StringUtils.equals(SmChannelType.BLUE.getNo(), no)) {
            return SmChannelType.BLUE;
        } else if(StringUtils.equals(SmChannelType.BST.getNo(), no)) {
            return SmChannelType.BST;
        } else if(StringUtils.equals(SmChannelType.DH3T.getNo(), no)) {
            return SmChannelType.DH3T;
        }
        return null;
    }
}
