package com.zdmoney.message.message.service.impl;

import com.zdmoney.message.api.utils.DateUtils;
import com.zdmoney.message.common.base.BaseServiceImpl;
import com.zdmoney.message.message.dao.MsqMqSendFailureDAO;
import com.zdmoney.message.message.model.MsqMqSendFailure;
import com.zdmoney.message.message.service.IMsqMqSendFailureService;
import com.zdmoney.mq.client.MqByteMessage;
import com.zdmoney.mq.client.MqMessage;
import com.zdmoney.mq.client.producer.MqProducer;
import com.zdmoney.zdqd.dao.complexQuery.QueryParamBuilder;
import com.zdmoney.zdqd.dao.complexQuery.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by rui on 16/9/21.
 */
@Service
public class MsqMqSendFailureService extends BaseServiceImpl<MsqMqSendFailure> implements IMsqMqSendFailureService {


    @Autowired
    private MsqMqSendFailureDAO msqMqSendFailureDAO;


    @Override
    @Transactional
    public void add(MqProducer mqProducer, MqMessage sendMessage) {
        MsqMqSendFailure sendFailure = new MsqMqSendFailure(mqProducer, sendMessage);
        msqMqSendFailureDAO.insert(sendFailure);
    }

    @Override
    @Transactional
    public void add(String mqAddress, String mqGroup, String mqTopic, String mqTag, MqMessage sendMessage) {
        MsqMqSendFailure sendFailure = new MsqMqSendFailure(mqAddress, mqGroup, mqTopic, mqTag, sendMessage);
        msqMqSendFailureDAO.insert(sendFailure);
    }


    @Override
    @Transactional
    public void add(MqProducer mqProducer, MqByteMessage sendMessage) {
        MsqMqSendFailure sendFailure = new MsqMqSendFailure(mqProducer, sendMessage);
        msqMqSendFailureDAO.insert(sendFailure);
    }


    public List<MsqMqSendFailure> searchFailedMqMsgs(int failTime, int interval) {
        QueryParamBuilder builder = QueryParamBuilder.newBuilder();
        builder.addWithValueQueryParam("sendFailTimes", "=", failTime).addWithValueQueryParam("sendStatus", "=", 0)
                .addWithValueQueryParam("modifyTime", ">=", DateUtils.plusMinutes(new Date(), -interval));
        List<Sort> sortList = new ArrayList<Sort>();
        sortList.add(new Sort("id", Sort.Direction.DESC));
        return query(builder.build(), sortList, 0, 1000);
    }
}
