package com.zdmoney.message.api.facade;

import com.zdmoney.message.api.common.dto.MessagePageResultDto;
import com.zdmoney.message.api.common.dto.MessageResultDto;
import com.zdmoney.message.api.dto.log.*;

/**
 * Created by Administrator on 2017/2/4.
 * 统计调用方法时间
 */
public interface ILogFacadeService {

    /**
     * 入库
     *
     * @param logDataReqDto
     * @return
     */
    MessageResultDto<LogDataRspDto> accessLog(LogDataReqDto logDataReqDto);

    /**
     * 查询入库日志
     * @param searchDto
     * @return
     */
    MessagePageResultDto<LogDataDto> search(LogDataSearchDto searchDto);

    /**
     * 查询统计日志
     * @param searchDto
     * @return
     */
    MessagePageResultDto<LogDataStatDto> searchStat(LogDataStatSearchDto searchDto);

    /**
     * 启动入库
     * @return
     */
    MessageResultDto on();

    /**
     * 关闭入库
     * @return
     */
    MessageResultDto off();

}
