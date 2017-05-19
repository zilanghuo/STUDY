package com.zdmoney.message.push.service.task;

/**
 * Created by gaojc on 2016/7/25.
 */

import com.zdmoney.message.api.utils.DateUtils;
import com.zdmoney.message.push.model.MsgPhone;
import com.zdmoney.message.push.model.MsgTask;
import com.zdmoney.message.push.model.PushResult;
import com.zdmoney.message.push.service.IMsgPhoneService;
import com.zdmoney.message.push.service.IPushMsgProvider;
import com.zdmoney.message.push.service.IPushMsgUpdater;
import com.zdmoney.message.utils.SpringContextHelper;
import com.zdmoney.zdqd.dao.complexQuery.QueryParamBuilder;
import com.zdmoney.zdqd.dao.complexQuery.Sort;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 执行推送任务
 * Created by gaojc on 2016/7/19.
 */
@Slf4j
@NoArgsConstructor
public class PushMsgTask implements Runnable {
    private IMsgPhoneService msgPhoneService = (IMsgPhoneService) SpringContextHelper.getBean("msgPhoneService");
    private IPushMsgProvider provider = (IPushMsgProvider) SpringContextHelper.getBean("getuiProvider");
    private IPushMsgUpdater updater = (IPushMsgUpdater) SpringContextHelper.getBean("pushMsgUpdater");

    //10条以内单条推送
    private static final int SINGLE_PUSH_SIZE = 10;
    //列表推送最多数量
    private static final int GROUP_SIZE = 1000;

    private MsgTask msgTask;

    public PushMsgTask(MsgTask msgTask) {
        this.msgTask = msgTask;
    }

    @Override
    public void run() {
        long starTime = System.currentTimeMillis();
        log.info("execute pushMsgTask.TaskNo=" + msgTask.getBatchNo() + ", start time=" + DateUtils.format(starTime, DateUtils.DEFAULT_FORMAT) + "ms");

        //非全员推送
        if (!msgTask.getIsAllpush()) {
            log.info("PushMsgTask NotAllPush start");
            int retNum = msgTask.getPushNum() / GROUP_SIZE;
            int modNum = msgTask.getPushNum() % GROUP_SIZE;
            int times = retNum + (modNum > 0 ? 1 : 0);    //发送次数

            for (int groupNo = 0; groupNo < times; groupNo++) {
                //分页查询 1000一页
                List<MsgPhone> phones = querySubList(GROUP_SIZE, groupNo, msgTask.getTaskNo());

                if (msgTask.getPushNum() > 1) {
                    //列表推送
                    if (msgTask.getPushNum() > SINGLE_PUSH_SIZE) {
                        PushResult result = provider.pushMessageToList(msgTask, phones);

                        updater.updateMsgTask(msgTask, result);
                        if (result.getDetails() != null) {
                            updater.updateMsgPhones(msgTask.getTaskNo(), result);
                        }
                    } else {
                        PushResult pushResult = new PushResult();
                        //少数用户单推
                        for (MsgPhone phone : phones) {
                            PushResult result = provider.pushMessageToSingle(msgTask, phone);
                            updater.updateMsgPhone(phone, result);

                            if (pushResult.getRetCode() == null) {
                                if (result.getRetCode().equals("ok")) {
                                    pushResult.setRetCode("ok");
                                }
                            }
                        }
                        updater.updateMsgTask(msgTask, pushResult);
                    }
                } else {
                    //单个用户推送
                    PushResult result = provider.pushMessageToSingle(msgTask, phones.get(0));
                    updater.updateMsgPhone(phones.get(0), result);
                    updater.updateMsgTask(msgTask, result);
                }
            }
            log.info("PushMsgTask NotAllPush end");
        }
        //全员推送
        else {
            log.info("PushMsgTask AllPush start");
            PushResult result = provider.pushMessageToApp(msgTask);
            updater.updateMsgTask(msgTask, result);
            log.info("PushMsgTask AllPush end");
        }

        long endTime = System.currentTimeMillis();
        log.info("execute pushMsgTask.TaskNo=" + msgTask.getBatchNo() + ", end time=" + DateUtils.format(endTime, DateUtils.DEFAULT_FORMAT));
        log.info("execute pushMsgTask.TaskNo=" + msgTask.getBatchNo() + ", cost time=" + (endTime - starTime) + "ms");
    }

    private List<MsgPhone> querySubList(int groupSize, int groupNo, String taskNo) {
        QueryParamBuilder builder = QueryParamBuilder.newBuilder();
        builder.addWithValueQueryParam("taskNo", "=", taskNo);
        List<Sort> sortList = new ArrayList<Sort>();
        sortList.add(new Sort("id", Sort.Direction.DESC));

        return msgPhoneService.query(builder.build(), sortList, groupNo * groupSize, groupSize);
    }
}
