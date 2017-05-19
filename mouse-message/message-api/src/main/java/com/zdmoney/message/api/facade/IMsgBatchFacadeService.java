package com.zdmoney.message.api.facade;

import com.zdmoney.message.api.common.dto.MessagePageResultDto;
import com.zdmoney.message.api.dto.push.MsgBatchDto;
import com.zdmoney.message.api.dto.push.MsgBatchSearchDto;

/**
 * Created by gaojc on 2016/7/19.
 */
public interface IMsgBatchFacadeService {
    /**
     * 查询
     *
     * @param searchDto
     * @return
     */
    MessagePageResultDto<MsgBatchDto> search(MsgBatchSearchDto searchDto);
}
