package com.zdmoney.message.push.service;

import com.zdmoney.message.api.common.dto.MessagePageResultDto;
import com.zdmoney.message.api.common.dto.MessageResultDto;
import com.zdmoney.message.api.dto.push.MsgPhoneDto;
import com.zdmoney.message.api.dto.push.MsgPhoneSearchDto;
import com.zdmoney.message.push.model.MsgPhone;
import com.zdmoney.zdqd.service.EntityService;

import java.util.List;

/**
 * Created by admin on 2016/7/15.
 */
public interface IMsgPhoneService extends EntityService<MsgPhone> {
    public void batchInsert(List<MsgPhone> msgPhones);

    /**
     * 查询
     *
     * @param searchDto
     * @return
     */
    MessagePageResultDto<MsgPhoneDto> search(MsgPhoneSearchDto searchDto);

    /**
     * 增加手机记录
     *
     * @param msgPhoneDto
     * @return
     */
    MessageResultDto addPhone(MsgPhoneDto msgPhoneDto);

    /**
     * 更新
     *
     * @param msgPhone
     * @return
     */
    MessageResultDto updatePhone(MsgPhone msgPhone);

    /**
     * 插入
     *
     * @param msgPhone
     * @return
     */
    MessageResultDto insertPhone(MsgPhone msgPhone);

    /**
     * 根据任务号查找手机
     *
     * @param taskNo
     * @return
     */
    List<MsgPhone> getByTaskNo(String taskNo);

    MsgPhone getOne(MsgPhone msgPhone);
}
