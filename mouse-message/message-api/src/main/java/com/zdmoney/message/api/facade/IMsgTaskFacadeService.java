package com.zdmoney.message.api.facade;

import com.zdmoney.message.api.common.dto.MessagePageResultDto;
import com.zdmoney.message.api.dto.push.MsgTaskDto;
import com.zdmoney.message.api.dto.push.MsgTaskSearchDto;

/**
 * Created by lwf on 2016/7/21.
 */
public interface IMsgTaskFacadeService {
    /**
     * 查询
     *
     * @param searchDto
     * @return
     */
    MessagePageResultDto<MsgTaskDto> search(MsgTaskSearchDto searchDto);
}
