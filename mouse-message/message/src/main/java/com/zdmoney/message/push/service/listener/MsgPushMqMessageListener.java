package com.zdmoney.message.push.service.listener;

import com.zdmoney.message.api.dto.push.MsgPushDto;
import com.zdmoney.message.api.dto.push.enums.DeviceType;
import com.zdmoney.message.api.dto.push.enums.PushStatus;
import com.zdmoney.message.api.utils.DateUtils;
import com.zdmoney.message.api.utils.JsonUtils;
import com.zdmoney.message.push.model.MsgBatch;
import com.zdmoney.message.push.model.MsgDevice;
import com.zdmoney.message.push.model.MsgPhone;
import com.zdmoney.message.push.model.MsgTask;
import com.zdmoney.message.push.service.IMsgBatchService;
import com.zdmoney.message.push.service.IMsgDeviceService;
import com.zdmoney.message.push.service.IMsgPhoneService;
import com.zdmoney.message.push.service.IMsgTaskService;
import com.zdmoney.message.utils.CommonUtils;
import com.zdmoney.message.utils.SpringContextHelper;
import com.zdmoney.mq.client.MqMessage;
import com.zdmoney.mq.client.consumer.listener.MqMessageListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by gaojc on 2016/9/1.
 */
@Slf4j
public class MsgPushMqMessageListener implements MqMessageListener {
    private IMsgPhoneService msgPhoneService = (IMsgPhoneService) SpringContextHelper.getBean("msgPhoneService");
    private IMsgDeviceService msgDeviceService = (IMsgDeviceService) SpringContextHelper.getBean("msgDeviceService");
    private IMsgTaskService msgTaskService = (IMsgTaskService) SpringContextHelper.getBean("msgTaskService");
    private IMsgBatchService msgBatchService = (IMsgBatchService) SpringContextHelper.getBean("msgBatchService");

    private void savePushMsg(List<MqMessage> mqMessages) {
        int randomNumSize = 10;

        for (MqMessage mqMessage : mqMessages) {
            MsgPushDto persistDto = JsonUtils.fromJson(mqMessage.getData(), MsgPushDto.class);

            log.info("msgPushMqConsumer get persistDto.serialNo="+persistDto.getSerialNo());

            //生成批次号
            String batchNo = RandomStringUtils.randomNumeric(randomNumSize);
            MsgBatch msgBatch = new MsgBatch(batchNo, persistDto);
            msgBatchService.insert(msgBatch);

            //非全员推送，生成手机记录
            if (!msgBatch.getIsAllpush()) {
                //生成任务号
                String androidTaskNo = batchNo + "-" + RandomStringUtils.randomNumeric(randomNumSize);
                MsgTask androidTask = new MsgTask(androidTaskNo, DeviceType.ANDROID, msgBatch);
                String iosTaskNo = batchNo + "-" + RandomStringUtils.randomNumeric(randomNumSize);
                MsgTask iosTask = new MsgTask(iosTaskNo, DeviceType.IOS, msgBatch);

                Integer androidCount = new Integer(0);
                Integer iosCount = new Integer(0);

                int groupSize = 1000;
                int retNum = persistDto.size() / groupSize;
                int modNum = persistDto.size() % groupSize;
                int threadNum = retNum + (modNum > 0 ? 1 : 0);    //分组数量

                List<String> subPhoneList = new ArrayList<String>();

                for (int groupNo = 0; groupNo < threadNum; groupNo++) {
                    subPhoneList = CommonUtils.devide(groupSize, persistDto.getPhones(), groupNo, modNum, threadNum);

                    List<MsgPhone> phoneList = new ArrayList<>();
                    for (String phone : subPhoneList) {
                        MsgDevice msgDevice = msgDeviceService.getByCellphoneNum(phone);
                        if (null == msgDevice) {
                            log.info("不存在手机号：" + phone + " 的用户,忽略");
                            continue;
                        }
                        MsgPhone msgPhone = new MsgPhone();
                        msgPhone.setCreateTime(new Date());
                        msgPhone.setModifyTime(msgPhone.getCreateTime());
                        msgPhone.setPushTime(msgBatch.getPushTime());
                        msgPhone.setCellphoneNum(phone);

                        DeviceType deviceType = msgDevice.getDeviceType();
                        if (deviceType.equals(DeviceType.ANDROID)) {
                            msgPhone.setTaskNo(androidTaskNo);
                            androidCount++;
                        }
                        if (deviceType.equals(DeviceType.IOS)) {
                            msgPhone.setTaskNo(iosTaskNo);
                            iosCount++;
                        }
                        msgPhone.setDeviceType(msgDevice.getDeviceType());
                        msgPhone.setDeviceId(msgDevice.getDeviceId());
                        msgPhone.setBatchNo(batchNo);
                        msgPhone.setStatus(PushStatus.WAITING);

                        phoneList.add(msgPhone);
                    }
                    long starTime = System.currentTimeMillis();
                    log.info("PersistMsgPhone begin time=" + DateUtils.format(starTime, DateUtils.DEFAULT_FORMAT));
                    msgPhoneService.batchInsert(phoneList);

                    long endTime = System.currentTimeMillis();
                    log.info("PersistMsgPhone end time=" + DateUtils.format(endTime, DateUtils.DEFAULT_FORMAT));
                    log.info("PersistMsgPhone cost time=" + (endTime - starTime) + "ms");
                }
                //保存任务
                if (androidCount > 0) {
                    androidTask.setPushNum(androidCount);
                    msgTaskService.insert(androidTask);
                }
                if (iosCount > 0) {
                    iosTask.setPushNum(iosCount);
                    msgTaskService.insert(iosTask);
                }
            } else {
                //全员推送，一个任务
                String taskNo = batchNo + "-" + RandomStringUtils.randomNumeric(randomNumSize);
                MsgTask task = new MsgTask(taskNo, DeviceType.ALL, msgBatch);
                msgTaskService.insert(task);
            }
            //立即推送
            if (msgBatch.getIsInstant()) {
                msgTaskService.executeTask(msgBatch.getBatchNo());
            }
        }
    }

    @Override
    public boolean execute(List<MqMessage> mqMessages) {
        long starTime = System.currentTimeMillis();
        log.info("MsgPushMqMessageListener begin time=" + DateUtils.format(starTime, DateUtils.DEFAULT_FORMAT));

        try {
            savePushMsg(mqMessages);
        }catch (Exception e){
            log.error("MsgPushMqMessageListener exception:",e);
        }

        long endTime = System.currentTimeMillis();
        log.info("MsgPushMqMessageListener end time=" + DateUtils.format(endTime, DateUtils.DEFAULT_FORMAT));
        log.info("MsgPushMqMessageListener cost time=" + (endTime - starTime) + "ms");
        return true;
    }
}
