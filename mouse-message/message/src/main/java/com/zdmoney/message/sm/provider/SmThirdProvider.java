package com.zdmoney.message.sm.provider;

import com.zdmoney.message.api.common.dto.MessageResultDto;
import com.zdmoney.message.api.dto.sm.*;
import com.zdmoney.message.sm.service.ISmChannelService;
import com.zdmoney.message.sm.service.impl.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2016/11/7.
 */


@Component
@Slf4j
public class SmThirdProvider implements InitializingBean {

    @Autowired
    private ISmChannelService smChannelService;
    @Autowired
    private BSTSendSmService bstSendSmService;
    @Autowired
    private BlueSendSmService blueSendSmService;
    @Autowired
    private Dh3tSendSmService dh3tSendSmService;
    @Autowired
    private BlueSendNotifyService blueSendNotifyService;
    @Autowired
    private BlueSendVerifyCodeService blueSendVerifyCodeService;
    @Autowired
    private BSTSendNotifyService bstSendNotifyService;
    @Autowired
    private Dh3tSendNotifyService dh3tSendNotifyService;
    @Autowired
    private BSTSendVerifyCodeService bstSendVerifyCodeService;
    @Autowired
    private Dh3tSendVerifyCodeService dh3tSendVerifyCodeService;

    public MessageResultDto<SendSmCommonRspDto> sendMarketMsg(SendSmReqDto sendMsgDto) {
        MessageResultDto<ChannelDto> channelDto = smChannelService.searchStartChannel();
        SmChannelType channelType = channelDto.getData().getSmChannelType();
        SendSmCommonReqDto sendSmCommonReqDto = new SendSmCommonReqDto(sendMsgDto.getMobiles(), sendMsgDto.getSendMsg(), SendSmType.MARKET, sendMsgDto.getBatchNo());
        sendSmCommonReqDto.setInstant(sendMsgDto.isInstant());
        if (SmChannelType.BST == channelType) {
            return bstSendSmService.sendSm(sendSmCommonReqDto);
        } else if (SmChannelType.BLUE == channelType) {
            return blueSendSmService.sendSm(sendSmCommonReqDto);
        } else if (SmChannelType.DH3T == channelType) {
            return dh3tSendSmService.sendSm(sendSmCommonReqDto);
        }
        return MessageResultDto.FAIL("营销短信发送失败");
    }

    public MessageResultDto<SendSmCommonRspDto> sendVerifyCodeMsg(SendVerifyCodeMsgReqDto sendMsgDto) {
        MessageResultDto<ChannelDto> channelDto = smChannelService.searchStartChannel();
        SmChannelType channelType = channelDto.getData().getSmChannelType();
        SendSmCommonReqDto sendSmCommonReqDto = new SendSmCommonReqDto(sendMsgDto.getMobile(), sendMsgDto.getSendMsg(), sendMsgDto.getVerifyCode(), SendSmType.VERIFY);
        if (SmChannelType.BST == channelType) {
            return bstSendVerifyCodeService.sendSm(sendSmCommonReqDto);
        } else if (SmChannelType.BLUE == channelType) {
            return blueSendVerifyCodeService.sendSm(sendSmCommonReqDto);
        } else if (SmChannelType.DH3T == channelType) {
            return dh3tSendVerifyCodeService.sendSm(sendSmCommonReqDto);
        }
        return MessageResultDto.FAIL("验证码短信发送失败");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
    }

    public MessageResultDto<SendSmCommonRspDto> sendNotifySm(SendSmNotifyReqDto sendMsgDto) {
        MessageResultDto<ChannelDto> channelDto = smChannelService.searchStartChannel();
        SmChannelType channelType = channelDto.getData().getSmChannelType();
        SendSmCommonReqDto sendSmCommonReqDto = new SendSmCommonReqDto(sendMsgDto.getMobile(), sendMsgDto.getSendMsg(), SendSmType.NOTIFY);
        if (SmChannelType.BST == channelType) {
            return bstSendNotifyService.sendSm(sendSmCommonReqDto);
        } else if (SmChannelType.BLUE == channelType) {
            return blueSendNotifyService.sendSm(sendSmCommonReqDto);
        } else if (SmChannelType.DH3T == channelType) {
            return dh3tSendNotifyService.sendSm(sendSmCommonReqDto);
        }
        return MessageResultDto.FAIL("通知短信发送失败");
    }

}
