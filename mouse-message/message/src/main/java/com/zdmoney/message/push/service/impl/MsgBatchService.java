package com.zdmoney.message.push.service.impl;

import com.zdmoney.message.api.common.dto.MessagePageResultDto;
import com.zdmoney.message.api.common.dto.MessageResultDto;
import com.zdmoney.message.api.dto.push.MsgBatchDto;
import com.zdmoney.message.api.dto.push.MsgBatchSearchDto;
import com.zdmoney.message.api.dto.push.MsgPushDto;
import com.zdmoney.message.api.utils.JsonUtils;
import com.zdmoney.message.api.utils.PropertiesUtils;
import com.zdmoney.message.common.base.BaseServiceImpl;
import com.zdmoney.message.message.service.IMsqMqSendFailureService;
import com.zdmoney.message.push.dao.MsgBatchDAO;
import com.zdmoney.message.push.model.MsgBatch;
import com.zdmoney.message.push.service.IMsgBatchService;
import com.zdmoney.mq.client.MqMessage;
import com.zdmoney.mq.client.producer.MqProducer;
import com.zdmoney.mq.client.producer.handler.MqSendFailHandler;
import com.zdmoney.zdqd.dao.complexQuery.QueryParamBuilder;
import com.zdmoney.zdqd.dao.complexQuery.Sort;
import com.zdmoney.zdqd.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaojc on 2016/7/19.
 */
@Service
@Slf4j
public class MsgBatchService extends BaseServiceImpl<MsgBatch> implements IMsgBatchService {

    @Autowired
    private MsgBatchDAO msgBatchDAO;

    @Autowired
    @Qualifier("msgPushMqProducer")
    private MqProducer mqProducer;

    @Autowired
    private IMsqMqSendFailureService msqMqSendFailureService;

    @Override
    public MessageResultDto generate(final MsgPushDto msgPushDto) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                long startTime = System.currentTimeMillis();
                logger.info("MsgBatchService.generate run start time:" + startTime);

                final MqMessage message = new MqMessage("persistMsgPushGroup"+msgPushDto.getSerialNo(),
                        JsonUtils.toJson(msgPushDto));
                log.info("msgPushMqProducer put persistDto.serialNo=" + msgPushDto.getSerialNo());

                mqProducer.send(message, new MqSendFailHandler() {
                    @Override
                    public void execute() {
                        msqMqSendFailureService.add(mqProducer, message);
                    }
                });
                long endTime = System.currentTimeMillis();
                logger.info("MsgBatchService.generate run end time:" + endTime);
                logger.info("MsgBatchService.generate run cost time:" + (endTime - startTime) + "ms");
            }
        }).start();

        return MessageResultDto.SUCCESS();
    }

    @Override
    public MsgBatch getMsgBatchByNo(String batchNo) {
        return msgBatchDAO.getOne(new MsgBatch(batchNo));
    }

    @Override
    public MessagePageResultDto<MsgBatchDto> search(MsgBatchSearchDto msgBatchSearchDto) {
        QueryParamBuilder builder = QueryParamBuilder.newBuilder();
        builder.addWithValueQueryParam("serialNo", StringUtils.isNotEmpty(msgBatchSearchDto.getSerialNo()) ? "like" : null,
                "%" + msgBatchSearchDto.getSerialNo() + "%")
                .addWithValueQueryParam("createTime", ">=", msgBatchSearchDto.getCreateStartDate())
                .addWithValueQueryParam("createTime", "<=", msgBatchSearchDto.getCreateEndDate())
                .addWithValueQueryParam("pushTime", ">=", msgBatchSearchDto.getPushStartDate())
                .addWithValueQueryParam("pushTime", "<=", msgBatchSearchDto.getPushEndDate());
        List<Sort> sortList =
                (CollectionUtils.isEmpty(msgBatchSearchDto.getSortDtoList()))
                        ? new ArrayList<Sort>() : PropertiesUtils.copyList(Sort.class, msgBatchSearchDto.getSortDtoList());
        sortList.add(new Sort("createTime", Sort.Direction.DESC));
        int totalSize = msgBatchDAO.countQuery(builder.build());
        List<MsgBatch> list = msgBatchDAO.query(builder.build(), sortList, msgBatchSearchDto.getStart(), msgBatchSearchDto.getPageSize());
        List<MsgBatchDto> msgBatchDtos = PropertiesUtils.copyListNotExcludeRepeat(MsgBatchDto.class, list);
        return new MessagePageResultDto<>(msgBatchDtos, totalSize, msgBatchSearchDto.getPageSize(), msgBatchSearchDto.getPageNo());
    }

}
