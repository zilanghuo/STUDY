package com.zdmoney.message.facade;

import com.zdmoney.message.api.common.dto.MessageResultDto;
import com.zdmoney.message.api.common.validation.MessageValid;
import com.zdmoney.message.api.dto.push.MsgDeviceCollectDto;
import com.zdmoney.message.api.dto.push.MsgDeviceDto;
import com.zdmoney.message.api.dto.push.MsgPushDto;
import com.zdmoney.message.api.dto.push.MsgWechatPushDto;
import com.zdmoney.message.api.facade.IMsgPushFacadeService;
import com.zdmoney.message.api.utils.PropertiesUtils;
import com.zdmoney.message.push.model.MsgDevice;
import com.zdmoney.message.push.provider.WechatProvider;
import com.zdmoney.message.push.service.IMsgBatchService;
import com.zdmoney.message.push.service.IMsgDeviceService;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by gaojc on 2016/7/15.
 */
@Component
public class MsgPushFacadeService implements IMsgPushFacadeService {

    @Autowired
    private IMsgBatchService msgBatchService;
    @Autowired
    private IMsgDeviceService msgDeviceService;
    @Autowired
    private WechatProvider wechatProvider;

    @Value("${msg.push.max_phones_size}")
    private int maxPhonesSize;

    /**
     * app发送推送请求，生成批次入库
     *
     * @param msgPushDto
     * @return
     */
    public MessageResultDto push(@MessageValid MsgPushDto msgPushDto) {
        if (msgPushDto.size() > maxPhonesSize) {
            return MessageResultDto.FAIL("手机号记录超过最大长度");
        }
        if (msgPushDto.size() > 0 && msgPushDto.getIsAllpush()) {
            return MessageResultDto.FAIL("全员推送不需要手机号码");
        }
        if (msgPushDto.size() == 0 && !msgPushDto.getIsAllpush()) {
            return MessageResultDto.FAIL("非全员推送需要手机号码");
        }
        return msgBatchService.generate(msgPushDto);
    }

    @Override
    public MessageResultDto collect(@MessageValid MsgDeviceCollectDto msgDeviceCollectDto) {
        return msgDeviceService.collect(msgDeviceCollectDto);
    }


    @Override
    public MessageResultDto<MsgDeviceDto> getByCellphoneNum(String cellphoneNum) {
        Validate.notEmpty(cellphoneNum, "cellphoneNum can not be empty");
        MsgDevice msgDevice = msgDeviceService.getByCellphoneNum(cellphoneNum);
        return new MessageResultDto(PropertiesUtils.copy(MsgDeviceDto.class, msgDevice));
    }

    @Override
    public MessageResultDto sendWechatMsg(@MessageValid MsgWechatPushDto wechatPushDto) {
        return wechatProvider.sendWechatMsg(wechatPushDto);
    }

}
