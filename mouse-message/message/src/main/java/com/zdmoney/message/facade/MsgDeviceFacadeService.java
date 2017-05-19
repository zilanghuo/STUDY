package com.zdmoney.message.facade;

import com.zdmoney.message.api.common.dto.MessagePageResultDto;
import com.zdmoney.message.api.common.dto.MessageResultDto;
import com.zdmoney.message.api.dto.push.MsgDeviceDto;
import com.zdmoney.message.api.dto.push.MsgDeviceSearchDto;
import com.zdmoney.message.api.facade.IMsgDeviceFacadeService;
import com.zdmoney.message.api.utils.PropertiesUtils;
import com.zdmoney.message.push.model.MsgDevice;
import com.zdmoney.message.push.service.IMsgDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by lwf on 2016/7/15.
 */
@Component
public class MsgDeviceFacadeService implements IMsgDeviceFacadeService {

    @Autowired
    private IMsgDeviceService msgDeviceService;

    @Override
    public MessageResultDto<MsgDeviceDto> get(Long id) {
        MsgDevice msgDevice = msgDeviceService.get(id);
        if (msgDevice == null) {
            MessageResultDto.FAIL("没有找到信息!");
        }
        return new MessageResultDto<>(PropertiesUtils.copy(MsgDeviceDto.class, msgDevice));
    }

    @Override
    public MessagePageResultDto<MsgDeviceDto> search(MsgDeviceSearchDto msgDeviceSearchDto) {
        return msgDeviceService.search(msgDeviceSearchDto);
    }
}
