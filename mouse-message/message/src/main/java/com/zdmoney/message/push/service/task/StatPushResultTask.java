package com.zdmoney.message.push.service.task;

import com.zdmoney.message.api.dto.push.enums.PushStatus;
import com.zdmoney.message.push.model.MsgTask;
import com.zdmoney.message.push.model.PushResult;
import com.zdmoney.message.push.service.IMsgTaskService;
import com.zdmoney.message.push.service.IPushMsgProvider;
import com.zdmoney.message.utils.SpringContextHelper;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by gaojc on 2016/8/5.
 */
@Slf4j
/**
 * 统计推送结果 线程
 */
public class StatPushResultTask implements Runnable {
    private IPushMsgProvider provider = (IPushMsgProvider) SpringContextHelper.getBean("getuiProvider");
    private IMsgTaskService msgTaskService = (IMsgTaskService) SpringContextHelper.getBean("msgTaskService");

    private MsgTask msgTask;
    private AtomicInteger sendNum;
    private AtomicInteger arriverNum;
    private AtomicInteger clickNum;
    private CountDownLatch latch;

    public StatPushResultTask(MsgTask msgTask, AtomicInteger sendNum, AtomicInteger arriverNum,
                              AtomicInteger clickNum, CountDownLatch latch) {
        this.msgTask = msgTask;
        this.sendNum = sendNum;
        this.arriverNum = arriverNum;
        this.clickNum = clickNum;
        this.latch = latch;
    }

    @Override
    public void run() {
        long starTime = System.currentTimeMillis();
        String threadName = Thread.currentThread().getName();
        log.info(threadName + "StatPushResultTask begin time=" + starTime);

        try {
            PushResult result = provider.statPushResult(msgTask.getThirdTaskNo());
            if (result != null) {
                msgTask.setClickNum(result.getClickNum());
                msgTask.setArriverNum(result.getArriverNum());
                msgTask.setSucceedNum(result.getSendNum());
                msgTask.setModifyTime(new Date());
                msgTask.setStatus(PushStatus.STATED);

                sendNum.addAndGet(result.getSendNum());
                arriverNum.addAndGet(result.getArriverNum());
                clickNum.addAndGet(result.getClickNum());

                msgTaskService.update(msgTask);

                long endTime = System.currentTimeMillis();
                log.info(threadName + "StatPushResultTask end time=" + endTime);
                log.info(threadName + "StatPushResultTask cost time=" + (endTime - starTime) + "ms");
            }
        } catch (Exception e) {
            log.error("StatPushResultTask.run异常", e);
        } finally {
            latch.countDown();
        }
    }
}
