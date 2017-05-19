package com.zdmoney.message.push.provider;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.ITemplate;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.zdmoney.message.api.dto.push.enums.DeviceType;
import com.zdmoney.message.push.config.GetuiConfig;
import com.zdmoney.message.push.model.MsgPhone;
import com.zdmoney.message.push.model.MsgTask;
import com.zdmoney.message.push.model.PushResult;
import com.zdmoney.message.push.service.IPushMsgProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 推送消息服务-个推
 * Created by gaojc on 2016/8/4.
 */
@Slf4j
@Service
public class GetuiProvider implements IPushMsgProvider, InitializingBean {
    @Autowired
    private GetuiConfig getuiConfig;

    private long timeMill = 0L;

    private IGtPush push = null;
    private String appId = null;
    private String appKey = null;

    @Override
    public void afterPropertiesSet() throws Exception {
        timeMill = getuiConfig.getOffLineTime() * 3600 * 1000;
        appId = getuiConfig.getAppId();
        appKey = getuiConfig.getAppKey();

        String masterSecret = getuiConfig.getMasterSecret();
        String host = getuiConfig.getHost();

        push = new IGtPush(host, appKey, masterSecret);
    }

    public PushResult pushMessageToSingle(MsgTask msgTask, MsgPhone phone) {
        log.info("GetuiProvider.pushMessageToSingle start");
        ITemplate template = getTemplate(msgTask, phone.getDeviceType());
        SingleMessage message = new SingleMessage();
        //可以离线推送
        message.setOffline(true);
        // 离线有效时间，单位为毫秒，可选
        message.setOfflineExpireTime(timeMill);
        message.setData(template);

        Target target = new Target();
        target.setAppId(appId);
        target.setClientId(phone.getDeviceId());

        IPushResult ret = null;
        try {
            msgTask.setPushBeginTime(new Date());
            //正常发送
            ret = push.pushMessageToSingle(message, target);
        } catch (RequestException e) {
            log.error("pushMessageToSingle invoke getui Exception", e);
            // 异常重试发送
            ret = push.pushMessageToSingle(message, target, e.getRequestId());
        }
        log.info("GetuiProvider.pushMessageToSingle end,PushResult=" + ret.getResponse().toString());
        msgTask.setPushFinishTime(new Date());
        return getHandlePushResult(ret);
    }

    /**
     * 对指定列表用户推送，最多1000个
     * @param msgTask
     * @param phones
     * @return
     */
    public PushResult pushMessageToList(MsgTask msgTask, List<MsgPhone> phones) {
        log.info("GetuiProvider.pushMessageToList start");
        ITemplate template = getTemplate(msgTask, msgTask.getDeviceType());
        ListMessage message = new ListMessage();
        message.setData(template);
        message.setOffline(true);
        message.setOfflineExpireTime(timeMill);
        // 配置推送目标
        List targets = new ArrayList();
        for (MsgPhone phone : phones) {
            Target target = new Target();
            target.setAppId(appId);
            target.setClientId(phone.getDeviceId());
            targets.add(target);
        }
        //为每个用户返回用户状态
        System.setProperty("gexin.rp.sdk.pushlist.needDetails", getuiConfig.getPushNeedDetails());
        // taskId用于在推送时去查找对应的message
        String taskId = push.getContentId(message);
        IPushResult ret = null;
        try {
            msgTask.setPushBeginTime(new Date());
            ret = push.pushMessageToList(taskId, targets);
        } catch (RequestException e) {
            log.error("pushMessageToList invoke getui Exception", e);
        }
        log.info("GetuiProvider.pushMessageToList end,PushResult=" + ret.getResponse().toString());
        msgTask.setPushFinishTime(new Date());
        return getHandlePushResult(ret);
    }

    /**
     * 全员推送
     * @param msgTask
     * @return
     */
    public PushResult pushMessageToApp(MsgTask msgTask) {
        log.info("GetuiProvider.pushMessageToApp start");
        ITemplate template = getTemplate(msgTask, DeviceType.ALL);
        AppMessage message = new AppMessage();
        message.setData(template);
        message.setOffline(true);
        message.setOfflineExpireTime(timeMill);
        //推送给App
        message.setAppIdList(new ArrayList<String>() {{
            add(appId);
        }});

        IPushResult ret = null;
        try {
            msgTask.setPushBeginTime(new Date());
            ret = push.pushMessageToApp(message);
        } catch (Exception e) {
            log.error("pushMessageToApp invoke getui Exception", e);
        }
        log.info("GetuiProvider.pushMessageToApp end,PushResult=" + ret.getResponse().toString());
        msgTask.setPushFinishTime(new Date());
        return getHandlePushResult(ret);
    }

    /**
     * 按照第三方推送任务号，统计推送结果
     *
     * @param thirdTaskNo
     * @return
     */
    public PushResult statPushResult(String thirdTaskNo) {
        PushResult pushResult = null;
        Map<String, Object> map = push.getPushResult(thirdTaskNo).getResponse();
        log.info("GetuiProvider.statPushResult.response="+map);

        if (map.get("result").equals("ok")) {
            String msgTotal = map.get("msgTotal").toString();
            String clickNum = map.get("clickNum").toString();
            String msgProcess = map.get("msgProcess").toString();

            pushResult = new PushResult(msgTotal, msgProcess, clickNum);
            pushResult.setRetCode("ok");
        }
        return pushResult;
    }

    /**
     * 获取消息模板
     *
     * @param task
     * @return
     */
    private ITemplate getTemplate(MsgTask task, DeviceType deviceType) {
        TransmissionTemplate template = new TransmissionTemplate();
        // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
        template.setTransmissionType(2);
        template.setTransmissionContent(task.getData());
        template.setAppId(appId);
        template.setAppkey(appKey);

        //IOSPush,ALLPush都需要设置APNS通知参数
        if (!deviceType.equals(DeviceType.ANDROID)) {
            APNPayload payload = new APNPayload();
            payload.setSound("default");
            //字典模式使用
            APNPayload.DictionaryAlertMsg alertMsg = new APNPayload.DictionaryAlertMsg();
            //通知文本消息标题
            alertMsg.setTitle(task.getTitle());
            //通知文本消息字符串
            alertMsg.setBody(task.getContent());
            payload.setAlertMsg(alertMsg);
            payload.setAutoBadge("-1");
//            payload.setBadge(1);
            payload.addCustomMsg("payload",task.getData());

            template.setAPNInfo(payload);
        }
        return template;
    }

    /**
     * 处理返回结果
     *
     * @param pushResult
     * @return
     */
    private PushResult getHandlePushResult(IPushResult pushResult) {
        log.info("GetuiProvider.getHandlePushResult");
        Map<String, Object> response = pushResult.getResponse();
        String retCode = response.get("result").toString();
        Object status = response.get("status");
        Object taskId = response.get("taskId");
        Object contentId = response.get("contentId");
        Object details = response.get("details");

        PushResult result = new PushResult(retCode);
        if (status != null) {
            result.setStatus(status.toString());
        }
        if (taskId != null) {
            result.setRetTaskNo(taskId.toString());
        }
        if (contentId != null) {
            result.setRetTaskNo(contentId.toString());
        }
        if (details != null) {
            result.setDetails(details.toString());
        }
        return result;
    }

}
