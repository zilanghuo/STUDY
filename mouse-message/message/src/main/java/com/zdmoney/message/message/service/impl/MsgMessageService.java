package com.zdmoney.message.message.service.impl;

import com.zdmoney.message.api.common.dto.MessagePageResultDto;
import com.zdmoney.message.api.common.dto.MessageResultDto;
import com.zdmoney.message.api.dto.message.*;
import com.zdmoney.message.api.utils.DateUtils;
import com.zdmoney.message.api.utils.JsonUtils;
import com.zdmoney.message.api.utils.PropertiesUtils;
import com.zdmoney.message.common.base.BaseServiceImpl;
import com.zdmoney.message.message.cache.IMsgCacheCountService;
import com.zdmoney.message.message.dao.MsgMessageDAO;
import com.zdmoney.message.message.model.MsgMessage;
import com.zdmoney.message.message.service.IMsgMessageService;
import com.zdmoney.message.message.service.IMsgMqService;
import com.zdmoney.message.message.service.IMsqMqSendFailureService;
import com.zdmoney.message.utils.CommonUtils;
import com.zdmoney.mq.client.MqMessage;
import com.zdmoney.mq.client.group.MqGroup;
import com.zdmoney.mq.client.producer.MqProducer;
import com.zdmoney.mq.client.producer.handler.MqSendFailHandler;
import com.zdmoney.zdqd.dao.complexQuery.QueryParamBuilder;
import com.zdmoney.zdqd.dao.complexQuery.Sort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by lwf on 2016/8/15.
 */
@Service
@Slf4j
public class MsgMessageService extends BaseServiceImpl<MsgMessage> implements IMsgMessageService, InitializingBean {

    @Autowired
    private MsgMessageDAO msgMessageDAO;

    @Autowired
    private IMsqMqSendFailureService msqMqSendFailureService;

    @Autowired
    @Qualifier("msgMessageMqProducer")
    private MqProducer mqProducer;

    @Autowired
    private IMsgCacheCountService msgCacheCountService;

    @Autowired
    private IMsgMqService msgMqService;

    @Override
    public MessagePageResultDto<MsgMessageDto> search(MsgMessageSearchDto searchDto) {
        log.info("MsgMessageService searchFailedMqMsgs start,accept parameters:" + searchDto.toString());
        QueryParamBuilder builder = QueryParamBuilder.newBuilder();
        builder.addWithValueQueryParam("merchantSerialNo", "=", searchDto.getMerchantSerialNo())
                .addWithValueQueryParam("title", "=", searchDto.getTitle())
                .addWithValueQueryParam("summary", "=", searchDto.getSummary())
                .addWithValueQueryParam("content", "=", searchDto.getContent())
                .addWithValueQueryParam("status", "=", searchDto.getStatus())
                .addWithValueQueryParam("userNo", "=", searchDto.getUserNo())
                .addWithValueQueryParam("userId", "=", searchDto.getUserId())
                .addWithValueQueryParam("type", "=", searchDto.getType())
                .addWithValueQueryParam("createTime", ">=", searchDto.getCreateStartDate())
                .addWithValueQueryParam("createTime", "<=", searchDto.getCreateEndDate());
        List<Sort> sortList =
                (CollectionUtils.isEmpty(searchDto.getSortDtoList()))
                        ? new ArrayList<Sort>() : PropertiesUtils.copyList(Sort.class, searchDto.getSortDtoList());
        sortList.add(new Sort("createTime", Sort.Direction.DESC));
        int totalSize = msgMessageDAO.countQuery(builder.build());
        List<MsgMessage> list = msgMessageDAO.query(builder.build(), sortList, searchDto.getStart(), searchDto.getPageSize());
        log.info("MsgMessageService searchFailedMqMsgs end，data size：" + list.size());
        return new MessagePageResultDto<>(PropertiesUtils.copyListNotExcludeRepeat(MsgMessageDto.class, list),
                totalSize, searchDto.getPageSize(), searchDto.getPageNo());
    }

    @Override
    public MessageResultDto<Boolean> send(final MsgMessageSendDto sendDto) {
        log.info("MsgMessageService send start");
        log.info("send users size：" + sendDto.getUsers().size());
        new Thread(new Runnable() {
            @Override
            public void run() {
                Long startTime = System.currentTimeMillis();
                log.info("MsgMessageService send start:" + DateUtils.format(startTime, "yyyy-MM-dd hh:mm:ss:SSS"));
                int groupSize = 1000;
                List<MsgMessageSendDto.UserDto> users = sendDto.getUsers();
                int retNum = users.size() / groupSize;
                int modNum = users.size() % groupSize;
                int threadNum = retNum + (modNum > 0 ? 1 : 0);    //线程数量
                for (int groupNo = 0; groupNo < threadNum; groupNo++) {
                    log.info("execute count ：" + (groupNo + 1));
                    List<MsgMessageSendDto.UserDto> subUsers = CommonUtils.devide(groupSize, users, groupNo, modNum, threadNum);
                    MsgMessageSendDto currentSendDto = new MsgMessageSendDto(sendDto, subUsers);
                    final MqMessage message = new MqMessage("messageUser-" + sendDto.getMerchantSerialNo() + "-" + groupNo,
                            JsonUtils.toJson(currentSendDto));
                    mqProducer.send(message, new MqSendFailHandler() {
                        @Override
                        public void execute() {
                            msqMqSendFailureService.add(mqProducer, message);
                        }
                    });
                }
                Long endTime = System.currentTimeMillis();
                log.info("MsgMessageService send end:" + DateUtils.format(endTime, "yyyy-MM-dd hh:mm:ss:SSS"));
            }
        }).start();
        log.info("MsgMessageService send end");
        return MessageResultDto.SUCCESS();
    }

    public MessageResultDto<Integer> unReadCount(String userId) {
        Integer integer = msgCacheCountService.getOneCacheValue(userId);
        return new MessageResultDto<>(integer);
    }

    @Override
    public MessageResultDto<Boolean> read(MsgMessageReadDto readDto) {
        List<MsgMessage> messages = new ArrayList<>();
        if (readDto.getIsAllRead()) {
            log.info("MsgMessageSevice read set all read" + ",userId: " + readDto.getUserId());
            MsgMessage message = new MsgMessage();
            if (readDto.getMessageType() != null) {
                message.setType(readDto.getMessageType());
            }
            message.setUserNo(readDto.getUserNo());
            message.setUserId(readDto.getUserId());
            message.setStatus(MsgMessageStatus.UN_READ);
            messages = this.get(message);
        } else {
            log.info("MsgMessageSevice read set part read，ids:" + readDto.getIds() + ",userId: " + readDto.getUserId());
            messages = msgMessageDAO.getByIds(readDto.getIds());
        }
        List<Integer> unReadIds = getUnReadIds(messages);
        if (unReadIds.size() > 0)
            setMessageRead(unReadIds, readDto.getUserId());
        log.info("MsgMessageSevice read success,ids：" + unReadIds.toString() + ",userId: " + readDto.getUserId());
        // 此处插入已读消息的队列
        MsgMessageReadDto notifyReadDto = new MsgMessageReadDto();
        notifyReadDto.setUserId(readDto.getUserId());
        notifyReadDto.setUserNo(readDto.getUserNo());
        notifyReadDto.setCallbackUrl(readDto.getCallbackUrl());
        List<Integer> messageIds = new ArrayList();
        for (Integer id : unReadIds){
            messageIds.add(id);
        }
        notifyReadDto.setIds(messageIds);
        //插入已读消息回调信息
        if (messageIds.size() >0){
            msgMqService.putReadMessageNotify(notifyReadDto,MqGroup.MESSAGE_MESSAGET_READ_NOTIFY);
        }
        return MessageResultDto.SUCCESS();
    }

    @Override
    public MessageResultDto<List<MsgMessageDto>> getByIds(List<Integer> ids) {
        log.info("MsgMessageSevice getByIds start，ids：" + ids.toString());
        List<MsgMessage> msgMessages = msgMessageDAO.getByIds(ids);
        List<MsgMessageDto> msgMessageDtos = PropertiesUtils.copyListNotExcludeRepeat(MsgMessageDto.class, msgMessages);
        List<Integer> unReadIds = getUnReadIds(msgMessages);
        if (unReadIds.size() > 0)
            setMessageRead(unReadIds, msgMessageDtos.get(0).getUserId());
        return new MessageResultDto<>(msgMessageDtos);
    }

    @Override
    public MessageResultDto<Boolean> addMessages(List<MsgMessage> messages) {
        msgMessageDAO.inserts(messages);
        List<String> userIds = new ArrayList<>();
        for (MsgMessage message : messages) {
            userIds.add(message.getUserId());
        }
        msgCacheCountService.batchAddCount(userIds);
        return MessageResultDto.SUCCESS();
    }

    @Override
    public MessageResultDto<String> sendNotifyToMq(MsgMessageSendDto sendDto) {
        return msgMqService.putAddMessageNotify(sendDto, MqGroup.MESSAGE_MESSAGET_INSERT_NOTIFY);
    }


    private void setMessageRead(List<Integer> ids, String userId) {
        log.info("MsgMessageSevice.setMessageRead start,ids：" + ids.toString() + ",userId: " + userId);
        Map<String, Object> map = new HashMap();
        map.put("status", MsgMessageStatus.ALREADY_READ);
        map.put("modifyTime", new Date());
        map.put("ids", ids);

        msgMessageDAO.batchUpdate(map);
        List<String> userIds = new ArrayList<>();
        userIds.add(userId);
        msgCacheCountService.batchReduceCount(userIds, ids.size());
        log.info("MsgMessageSevice.setMessageRead end,ids：" + ids.toString() + ",userId: " + userId);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
    }

    /**
     * 保存信息
     */
    static class PersitMessageThreadFactory implements ThreadFactory {
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        PersitMessageThreadFactory() {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() :
                    Thread.currentThread().getThreadGroup();
            namePrefix = "PersitMessage-pool-" +
                    poolNumber.getAndIncrement() +
                    "-thread-";
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r,
                    namePrefix + threadNumber.getAndIncrement(),
                    0);
            if (t.isDaemon())
                t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }

    private List<Integer> getUnReadIds(List<MsgMessage> msgMessages) {
        log.info("MsgMessageService getUnReadIds start");
        List<Integer> unReadIds = new ArrayList<Integer>();
        for (MsgMessage message : msgMessages) {
            if (message.getStatus().equals(MsgMessageStatus.UN_READ)) {
                unReadIds.add(message.getId());
            }
        }
        log.info("MsgMessageService getUnReadIds end，unRead ids ：" + unReadIds.toString());
        return unReadIds;
    }


}
