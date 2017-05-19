package com.zdmoney.message.facade;

import com.zdmoney.message.api.common.dto.MessagePageResultDto;
import com.zdmoney.message.api.common.dto.MessageResultDto;
import com.zdmoney.message.api.common.validation.MessageValid;
import com.zdmoney.message.api.dto.message.MsgMessageDto;
import com.zdmoney.message.api.dto.message.MsgMessageReadDto;
import com.zdmoney.message.api.dto.message.MsgMessageSearchDto;
import com.zdmoney.message.api.dto.message.MsgMessageSendDto;
import com.zdmoney.message.api.facade.IMsgMessageFacadeService;
import com.zdmoney.message.message.service.IMsgMessageService;
import com.zdmoney.message.utils.CommonUtils;
import com.zdmoney.zdqd.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by lwf on 2016/8/15.
 */
@Component
@Slf4j
public class MsgMessageFacadeService implements IMsgMessageFacadeService {
    @Autowired
    private IMsgMessageService msgMessageService;

    @Value("${msg.message.max_users_size}")
    private int maxUsersSize;

    @Value("${msg.message.max_ids_size}")
    private int maxIdsSize;

    @Override
    public MessagePageResultDto<MsgMessageDto> search(MsgMessageSearchDto searchDto) {
        return msgMessageService.search(searchDto);
    }

    @Override
    public MessageResultDto<Boolean> send(@MessageValid MsgMessageSendDto sendDto) {
        if (sendDto.getUsers().size() > maxUsersSize) {
            return MessageResultDto.FAIL("用户个数不能超过最大限制：" + maxUsersSize);
        }
        return msgMessageService.send(sendDto);
    }

    @Override
    public MessageResultDto<Integer> unReadCount(String userId) {
        if (StringUtils.isEmpty(userId)) {
            return MessageResultDto.FAIL("用户编号必填");
        }
        return msgMessageService.unReadCount(userId);
    }

    @Override
    public MessageResultDto<Boolean> read(MsgMessageReadDto readDto) {
        return msgMessageService.read(readDto);
    }

    @Override
    public MessageResultDto<List<MsgMessageDto>> getByIds(List<Integer> ids) {
        if (!CommonUtils.isNotEmpty(ids)) {
            return MessageResultDto.FAIL("消息编号列表不能为空");
        }
        if (ids.size() > maxIdsSize) {
            return MessageResultDto.FAIL("消息编号个数不能超过最大限制:" + maxIdsSize);
        }
        return msgMessageService.getByIds(ids);
    }

}
