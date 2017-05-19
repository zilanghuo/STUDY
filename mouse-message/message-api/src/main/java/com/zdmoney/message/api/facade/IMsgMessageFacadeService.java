package com.zdmoney.message.api.facade;

import com.zdmoney.message.api.common.dto.MessagePageResultDto;
import com.zdmoney.message.api.common.dto.MessageResultDto;
import com.zdmoney.message.api.dto.message.MsgMessageDto;
import com.zdmoney.message.api.dto.message.MsgMessageReadDto;
import com.zdmoney.message.api.dto.message.MsgMessageSearchDto;
import com.zdmoney.message.api.dto.message.MsgMessageSendDto;

import java.util.List;

/**
 * Created by lwf on 2016/8/15.
 */
public interface IMsgMessageFacadeService {

    /**
     * 查询消息列表-后台和接口
     *
     * @param searchDto
     * @return
     */
    MessagePageResultDto<MsgMessageDto> search(MsgMessageSearchDto searchDto);

    /**
     * 发送消息
     *
     * @param sendDto
     * @return
     */
    MessageResultDto<Boolean> send(MsgMessageSendDto sendDto);

    /**
     * 查询未读消息个数
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
    MessageResultDto<Boolean> read(MsgMessageReadDto readDto);

    /**
     * 根据ID列表，获取消息详情
     *
     * @param ids
     * @return
     */
    MessageResultDto<List<MsgMessageDto>> getByIds(List<Integer> ids);

}
