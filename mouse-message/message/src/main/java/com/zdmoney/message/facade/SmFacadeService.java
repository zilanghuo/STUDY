package com.zdmoney.message.facade;

import com.zdmoney.message.api.common.dto.MessagePageResultDto;
import com.zdmoney.message.api.common.dto.MessageResultDto;
import com.zdmoney.message.api.dto.sm.*;
import com.zdmoney.message.api.facade.ISmFacadeService;
import com.zdmoney.message.sm.mq.SmMqProducer;
import com.zdmoney.message.sm.service.ISmChannelService;
import com.zdmoney.message.sm.service.ISmMarketService;
import com.zdmoney.message.sm.service.ISmNotifyService;
import com.zdmoney.message.sm.service.ISmVerifyCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2016/11/7.
 */
@Component
@Slf4j
public class SmFacadeService implements ISmFacadeService {

    @Autowired
    private ISmMarketService smMarketService;
    @Autowired
    private ISmNotifyService smNotifyService;
    @Autowired
    private ISmVerifyCodeService smVerifyCodeService;
    @Autowired
    private SmMqProducer smProducer;
    @Autowired
    private ISmChannelService smChannelService;

    @Override
    public MessageResultDto<SendVerifyCodeMsgRspDto> sendVerifyCodeMsg(SendVerifyCodeMsgReqDto reqDto) {
       return smProducer.sendVerifyCodeMsg(reqDto);
    }

    @Override
    public MessageResultDto<SendSmRspDto> sendMarketMsg(SendSmReqDto reqDto) {
        return smProducer.sendMarketMsg(reqDto);
    }

    @Override
    public MessageResultDto<SendSmRspDto> sendNotifyMsg(SendSmNotifyReqDto reqDto) {
        return smProducer.sendNotifyMsg(reqDto);
    }

    @Override
    public MessageResultDto verificationCode(VerifyCodeReqDto reqDto) {
        return smVerifyCodeService.verificationCode(reqDto);
    }

    @Override
    public MessageResultDto<ChannelRspDto> startChannel(ChannelReqDto reqDto) {
        return smChannelService.start(reqDto);
    }

    @Override
    public MessageResultDto<ChannelRspDto> stopChannel(ChannelReqDto reqDto) {
        return smChannelService.stop(reqDto);
    }

    @Override
    public MessagePageResultDto<VerifyCodeRspDto> searchVerifyCode(SmSearchDto reqDto) {
        return smVerifyCodeService.searchVerifyCode(reqDto);
    }

    @Override
    public MessagePageResultDto<ShortMsgDto> searchShortMsg(SmSearchDto reqDto) {
        return smMarketService.searchShortMsg(reqDto);
    }

    @Override
    public MessagePageResultDto<SmNotifyDto> searchSmNotify(SmSearchDto reqDto) {
        return smNotifyService.searchSmNotify(reqDto);
    }

    @Override
    public MessagePageResultDto<ChannelDto> searchChannel(ChannelSearchDto reqDto) {
        return smChannelService.searchChannel(reqDto);
    }

}
