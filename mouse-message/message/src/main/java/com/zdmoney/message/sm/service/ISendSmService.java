package com.zdmoney.message.sm.service;

import com.zdmoney.message.api.common.dto.MessageResultDto;
import com.zdmoney.message.api.dto.sm.*;

/**
 * Created by Administrator on 2016/11/3.
 */
public interface ISendSmService {

    MessageResultDto<SendSmCommonRspDto> sendSm(SendSmCommonReqDto sendSmCommonReqDto);

}
