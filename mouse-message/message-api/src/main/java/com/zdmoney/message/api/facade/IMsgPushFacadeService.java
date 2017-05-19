package com.zdmoney.message.api.facade;

import com.zdmoney.message.api.common.dto.MessageResultDto;
import com.zdmoney.message.api.dto.push.MsgDeviceCollectDto;
import com.zdmoney.message.api.dto.push.MsgDeviceDto;
import com.zdmoney.message.api.dto.push.MsgPushDto;
import com.zdmoney.message.api.dto.push.MsgWechatPushDto;

/**
 * Created by gaojc on 2016/7/15.
 */
public interface IMsgPushFacadeService {
    /**
     * app推送消息
     *
     * @param msgPushDto
     * @return
     */
    MessageResultDto push(MsgPushDto msgPushDto);

    /**
     * 增加设备端口
     *
     * @param collectDto
     * @return
     */
    MessageResultDto collect(MsgDeviceCollectDto collectDto);

    /**
     * 根据手机号码查询设备信息
     *
     * @param cellphoneNum
     * @return
     */
    MessageResultDto<MsgDeviceDto> getByCellphoneNum(String cellphoneNum);

    /**
     * 微信推送
     *
     * @param wechatPushDto
     * @return
     */
    MessageResultDto sendWechatMsg(MsgWechatPushDto wechatPushDto);
}
