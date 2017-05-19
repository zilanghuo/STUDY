package com.zdmoney.message.facade;

import com.zdmoney.message.api.common.dto.MessagePageResultDto;
import com.zdmoney.message.api.dto.push.MsgPhoneDto;
import com.zdmoney.message.api.dto.push.MsgPhoneSearchDto;
import com.zdmoney.message.api.facade.IMsgPhoneFacadeService;
import com.zdmoney.message.push.service.IMsgPhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by lwf on 2016/7/21.
 */
@Component
public class MsgPhoneFacadeService implements IMsgPhoneFacadeService {
    @Autowired
    private IMsgPhoneService msgPhoneService;

    @Override
    public MessagePageResultDto<MsgPhoneDto> search(MsgPhoneSearchDto searchDto) {
        return msgPhoneService.search(searchDto);
    }
}
