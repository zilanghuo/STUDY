package com.zdmoney.message.facade;

import com.zdmoney.message.api.common.dto.MessagePageResultDto;
import com.zdmoney.message.api.dto.push.MsgTaskDto;
import com.zdmoney.message.api.dto.push.MsgTaskSearchDto;
import com.zdmoney.message.api.facade.IMsgTaskFacadeService;
import com.zdmoney.message.push.service.IMsgTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by gaojc on 2016/7/19.
 */
@Component
public class MsgTaskFacadeService implements IMsgTaskFacadeService {

    @Autowired
    private IMsgTaskService msgTaskService;

    @Override
    public MessagePageResultDto<MsgTaskDto> search(MsgTaskSearchDto searchDto) {
        return msgTaskService.search(searchDto);
    }
}
