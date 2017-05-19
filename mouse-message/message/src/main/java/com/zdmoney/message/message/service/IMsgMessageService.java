package com.zdmoney.message.message.service;

import com.zdmoney.message.api.common.dto.MessagePageResultDto;
import com.zdmoney.message.api.common.dto.MessageResultDto;
import com.zdmoney.message.api.dto.message.MsgMessageDto;
import com.zdmoney.message.api.dto.message.MsgMessageReadDto;
import com.zdmoney.message.api.dto.message.MsgMessageSearchDto;
import com.zdmoney.message.api.dto.message.MsgMessageSendDto;
import com.zdmoney.message.message.model.MsgMessage;
import com.zdmoney.zdqd.service.EntityService;

import java.util.List;

/**
 * Created by lwf on 2016/8/15.
 */
public interface IMsgMessageService extends EntityService<MsgMessage> {
    /**
     * 查询
     *
     * @param searchDto
     * @return
     */
    MessagePageResultDto<MsgMessageDto> search(MsgMessageSearchDto searchDto);

    /**
     * 接收消息
     *
     * @param sendDto
     * @return
     */
    MessageResultDto<Boolean> send(MsgMessageSendDto sendDto);

    /**
     * 未读消息
     *
     * @param userId
     * @return
     */
    MessageResultDto<Integer> unReadCount(String userId);

    /**
     * 读消息
     *
     * @param readDto
     * @return
     */
    MessageResultDto read(MsgMessageReadDto readDto);

    /**
     * 根据ID列表，获取消息详情
     *
     * @param ids
     * @return
     */
    MessageResultDto<List<MsgMessageDto>> getByIds(List<Integer> ids);

    /**
     * 批量插入
     *
     * @param messages
     * @return
     */
    MessageResultDto<Boolean> addMessages(List<MsgMessage> messages);


    /**
     * 发送 插入消息 到mq
     * @param sendDto
     * @return
     */
    MessageResultDto<String> sendNotifyToMq(MsgMessageSendDto sendDto);
}
