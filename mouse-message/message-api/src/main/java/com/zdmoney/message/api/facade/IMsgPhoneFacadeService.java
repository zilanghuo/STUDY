package com.zdmoney.message.api.facade;

import com.zdmoney.message.api.common.dto.MessagePageResultDto;
import com.zdmoney.message.api.dto.push.MsgPhoneDto;
import com.zdmoney.message.api.dto.push.MsgPhoneSearchDto;

/**
 * Created by gaojc on 2016/7/19.
 */
public interface IMsgPhoneFacadeService {
    /**
     * 查询
     * @param searchDto
     * @return
     */
    MessagePageResultDto<MsgPhoneDto> search(MsgPhoneSearchDto searchDto);
}
