package com.zdmoney.message.push.service;

import com.zdmoney.message.api.common.dto.MessagePageResultDto;
import com.zdmoney.message.api.common.dto.MessageResultDto;
import com.zdmoney.message.api.dto.push.MsgDeviceCollectDto;
import com.zdmoney.message.api.dto.push.MsgDeviceDto;
import com.zdmoney.message.api.dto.push.MsgDeviceSearchDto;
import com.zdmoney.message.push.model.MsgDevice;
import com.zdmoney.zdqd.service.EntityService;

/**
 * Created by admin on 2016/7/15.
 */
public interface IMsgDeviceService extends EntityService<MsgDevice> {
    /**
     * 获取单个记录
     * @param id
     * @return
     */
    MsgDevice get(Long id);

    /**
     * 查询
     * @param msgDeviceSearchDto
     * @return
     */
    MessagePageResultDto<MsgDeviceDto> search(MsgDeviceSearchDto msgDeviceSearchDto);

    /**
     * 增加
     * @param collectDto
     * @return
     */
    MessageResultDto collect(MsgDeviceCollectDto collectDto);

    /**
     * 获取单个记录
     * @param cellphone
     * @return
     */
    MsgDevice getByCellphoneNum(String cellphone);

}
