package com.zdmoney.message.push.service;

import com.zdmoney.message.api.common.dto.MessagePageResultDto;
import com.zdmoney.message.api.common.dto.MessageResultDto;
import com.zdmoney.message.api.dto.push.MsgBatchDto;
import com.zdmoney.message.api.dto.push.MsgBatchSearchDto;
import com.zdmoney.message.api.dto.push.MsgPushDto;
import com.zdmoney.message.push.model.MsgBatch;
import com.zdmoney.zdqd.service.EntityService;

/**
 * Created by gaojc on 2016/7/15.
 */
public interface IMsgBatchService extends EntityService<MsgBatch> {

    /**
     * 创建批次
     *
     * @param msgPushDto
     * @return
     */
    MessageResultDto generate(MsgPushDto msgPushDto);

    /**
     * 获取批次
     *
     * @param batchNo
     * @return
     */
    MsgBatch getMsgBatchByNo(String batchNo);

    /**
     * 查询
     *
     * @param msgBatchSearchDto
     * @return
     */
    MessagePageResultDto<MsgBatchDto> search(MsgBatchSearchDto msgBatchSearchDto);

}
