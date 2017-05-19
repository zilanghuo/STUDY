package com.zdmoney.message.facade;

import com.zdmoney.message.api.common.dto.MessagePageResultDto;
import com.zdmoney.message.api.dto.push.MsgBatchDto;
import com.zdmoney.message.api.dto.push.MsgBatchSearchDto;
import com.zdmoney.message.api.facade.IMsgBatchFacadeService;
import com.zdmoney.message.push.service.IMsgBatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by gaojc on 2016/7/19.
 */
@Component
public class MsgBatchFacadeService implements IMsgBatchFacadeService {

    @Autowired
    private IMsgBatchService msgBatchService;

    @Override
    public MessagePageResultDto<MsgBatchDto> search(MsgBatchSearchDto searchDto) {
        return msgBatchService.search(searchDto);
    }
}
