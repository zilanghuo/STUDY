package com.zdmoney.message.api.facade;

import com.zdmoney.message.api.common.dto.MessagePageResultDto;
import com.zdmoney.message.api.common.dto.MessageResultDto;
import com.zdmoney.message.api.dto.push.MsgDeviceDto;
import com.zdmoney.message.api.dto.push.MsgDeviceSearchDto;

/**
 * Created by admin on 2016/7/15.
 */
public interface IMsgDeviceFacadeService {

    /**
     * 单个对象
     * @param id
     * @return
     */
    MessageResultDto<MsgDeviceDto> get(Long id);

    /**
     * 查询
     * @param msgDeviceSearchDto
     * @return
     */
    MessagePageResultDto<MsgDeviceDto> search(MsgDeviceSearchDto msgDeviceSearchDto);
}
