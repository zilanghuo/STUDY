package com.zdmoney.message.push.service.impl;

import com.zdmoney.message.api.dto.push.enums.PushStatus;
import com.zdmoney.message.push.model.MsgPhone;
import com.zdmoney.message.push.model.MsgTask;
import com.zdmoney.message.push.model.PushResult;
import com.zdmoney.message.push.service.IMsgPhoneService;
import com.zdmoney.message.push.service.IMsgTaskService;
import com.zdmoney.message.push.service.IPushMsgUpdater;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 更新推送数据库
 * Created by gaojc on 2016/7/25.
 */
@Slf4j
@Service
public class PushMsgUpdater implements IPushMsgUpdater {
    @Autowired
    private IMsgTaskService msgTaskService;
    @Autowired
    private IMsgPhoneService msgPhoneService;

    /**
     * 更新多条推送手机表记录
     *
     * @param taskNo
     * @param result
     */
    @Override
    public void updateMsgPhones(String taskNo, PushResult result) {
        log.info("PushMsgUpdater.updateMsgPhones");
        String details = result.getDetails();
        Map<String, String> map = strToMap(details);

        for (String deviceId : map.keySet()) {
            MsgPhone phone = new MsgPhone();
            phone.setTaskNo(taskNo);
            phone.setDeviceId(deviceId);

            MsgPhone updPhone = msgPhoneService.getOne(phone);
            String code = map.get(deviceId);
            updPhone.setRetMsg(code);
            if (PushResult.getRetCodes().contains(code)) {
                updPhone.setStatus(PushStatus.SUCCEED);
            } else {
                updPhone.setStatus(PushStatus.FAILED);
            }
            updPhone.setModifyTime(new Date());

            log.info("updated MsgPhone.deviceId =" + deviceId);
            msgPhoneService.update(updPhone);
        }
    }

    /**
     * 更新一条推送手机表记录
     *
     * @param phone
     * @param result
     */
    @Override
    public void updateMsgPhone(MsgPhone phone, PushResult result) {
        log.info("PushMsgUpdater.updateMsgPhone：" + phone.getCellphoneNum());
        //修改任务
        String retCode = result.getRetCode();
        if (retCode.equals("ok")) {
            phone.setStatus(PushStatus.SUCCEED);
        } else {
            phone.setStatus(PushStatus.FAILED);
        }
        phone.setRetMsg(retCode);
        phone.setModifyTime(new Date());
        msgPhoneService.update(phone);
    }

    /**
     * 更新推送任务表
     *
     * @param task
     * @param result
     */
    @Override
    public void updateMsgTask(MsgTask task, PushResult result) {
        log.info("PushMsgUpdater.updateMsgTask：" + task.getTaskNo());
        //根据返回结果更新数据库
        String retCode = result.getRetCode();

        if (result.getRetCode().equals("ok")) {
            task.setThirdTaskNo(result.getRetTaskNo());
            task.setStatus(PushStatus.SUCCEED);
        } else {
            task.setStatus(PushStatus.FAILED);
        }
        task.setRetMsg(retCode);
        task.setModifyTime(new Date());
        msgTaskService.update(task);
    }

    private Map strToMap(String str) {
        Map<String, String> map = new HashMap<>();
        String tmpStr = str.substring(1, str.length() - 1);

        String[] entry = null;
        if (tmpStr.indexOf(",") != -1) {
            entry = tmpStr.split(",");
        } else {
            entry = new String[]{tmpStr};
        }

        for (int i = 0; i < entry.length; i++) {
            int pIndex = entry[i].indexOf("=");
            String key = entry[i].substring(0, pIndex);
            String value = entry[i].substring(pIndex + 1, entry[i].length());
            map.put(key.trim(), value.trim());
        }
        return map;
    }
}
