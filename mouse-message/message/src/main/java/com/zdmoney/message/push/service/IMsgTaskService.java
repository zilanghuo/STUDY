package com.zdmoney.message.push.service;

import com.zdmoney.message.api.common.dto.MessagePageResultDto;
import com.zdmoney.message.api.common.dto.MessageResultDto;
import com.zdmoney.message.api.dto.push.MsgTaskDto;
import com.zdmoney.message.api.dto.push.MsgTaskSearchDto;
import com.zdmoney.message.push.model.MsgTask;
import com.zdmoney.zdqd.service.EntityService;

import java.util.List;

/**
 * Created by gaojc on 2016/7/15.
 */
public interface IMsgTaskService extends EntityService<MsgTask> {
    /**
     * 立即推送
     *
     * @param batchNo
     * @return
     */
    MessageResultDto executeTask(String batchNo);

    /**
     * 定时推送
     *
     * @return
     */
    MessageResultDto executeTimerTask();

    /**
     * 统计推送结果
     */
    void statPushResult();

    /**
     * 获取任务
     *
     * @param taskNo
     * @return
     */
    MsgTask getMsgTaskByTaskNo(String taskNo);

    /**
     * 查询
     *
     * @param searchDto
     * @return
     */
    MessagePageResultDto<MsgTaskDto> search(MsgTaskSearchDto searchDto);


    List<MsgTask> getByBatchNo(String batchNo);
}
