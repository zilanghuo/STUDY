package com.zdmoney.message.push.service.impl;

import com.zdmoney.message.api.common.dto.MessagePageResultDto;
import com.zdmoney.message.api.common.dto.MessageResultDto;
import com.zdmoney.message.api.dto.push.MsgTaskDto;
import com.zdmoney.message.api.dto.push.MsgTaskSearchDto;
import com.zdmoney.message.api.utils.DateUtils;
import com.zdmoney.message.api.utils.PropertiesUtils;
import com.zdmoney.message.common.base.BaseServiceImpl;
import com.zdmoney.message.push.config.GetuiConfig;
import com.zdmoney.message.push.dao.MsgTaskDAO;
import com.zdmoney.message.push.model.MsgBatch;
import com.zdmoney.message.push.model.MsgTask;
import com.zdmoney.message.push.service.IMsgBatchService;
import com.zdmoney.message.push.service.IMsgTaskService;
import com.zdmoney.message.push.service.task.PushMsgTask;
import com.zdmoney.message.push.service.task.StatPushResultTask;
import com.zdmoney.zdqd.dao.complexQuery.QueryParamBuilder;
import com.zdmoney.zdqd.dao.complexQuery.Sort;
import com.zdmoney.zdqd.util.StringUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 任务业务逻辑类
 * Created by gaojc on 2016/7/19.
 */
@Service
public class MsgTaskService extends BaseServiceImpl<MsgTask> implements IMsgTaskService, InitializingBean, DisposableBean {

    @Autowired
    private MsgTaskDAO msgTaskDAO;
    @Autowired
    private IMsgBatchService msgBatchService;
    @Autowired
    private GetuiConfig config;

    //推送线程
    private ExecutorService executor;

    /**
     * 立即推送
     *
     * @param batchNo
     * @return
     */
    @Override
    public MessageResultDto executeTask(String batchNo) {
        MsgTask task = new MsgTask();
        task.setBatchNo(batchNo);

        List<MsgTask> tasks = msgTaskDAO.get(task);
        logger.info("execute immediate push,pushTasks.size=" + tasks.size());

        for (MsgTask msgTask : tasks) {
            executor.submit(new PushMsgTask(msgTask));
        }
        return MessageResultDto.SUCCESS();
    }

    /**
     * 定时推送
     *
     * @return
     * @throws InterruptedException
     */
    @Override
    public MessageResultDto executeTimerTask() {
        List<MsgTask> tasks = msgTaskDAO.getNeedPushTasks(new Date());
        logger.info("execute timed push,pushTasks.size=" + tasks.size());

        for (MsgTask msgTask : tasks) {
            executor.submit(new PushMsgTask(msgTask));
        }
        return MessageResultDto.SUCCESS();
    }

    /**
     * 统计推送结果
     */
    @Override
    public void statPushResult() {
        List<String> batchNos = msgTaskDAO.getPushedBatchNos(DateUtils.plusHours(new Date(), -(int) (config.getOffLineTime())));
        logger.info("execute statPushResult,pushBatch.size=" + batchNos.size());
        for (String batchNo : batchNos) {
            MsgTask task = new MsgTask();
            task.setBatchNo(batchNo);
            List<MsgTask> msgTasks = msgTaskDAO.get(task);

            AtomicInteger sendNum = new AtomicInteger(0),
                    arriverNum = new AtomicInteger(0), clickNum = new AtomicInteger(0);
            CountDownLatch latch = new CountDownLatch(msgTasks.size());
            try {
                for (MsgTask msgTask : msgTasks) {
                    executor.submit(new StatPushResultTask(msgTask, sendNum, arriverNum, clickNum, latch));
                }
                latch.await();
            } catch (InterruptedException e) {
                logger.info("MsgTaskService.statPushResult CountDownLatch exception:", e);
            }
            MsgBatch batch = msgBatchService.getMsgBatchByNo(batchNo);

            batch.setSucceedNum(sendNum.get());
            batch.setArriverNum(arriverNum.get());
            batch.setClickNum(clickNum.get());
            batch.setModifyTime(new Date());

            msgBatchService.update(batch);
        }
    }

    @Override
    public MsgTask getMsgTaskByTaskNo(String taskNo) {
        return msgTaskDAO.getOne(new MsgTask(taskNo));
    }

    @Override
    public MessagePageResultDto<MsgTaskDto> search(MsgTaskSearchDto searchDto) {
        QueryParamBuilder builder = QueryParamBuilder.newBuilder();

        builder.addWithValueQueryParam("batchNo", StringUtils.isNotEmpty(searchDto.getBatchNo()) ? "like" : null,
                "%" + searchDto.getBatchNo() + "%")
                .addWithValueQueryParam("thirdTaskNo", StringUtils.isNotEmpty(searchDto.getThirdTaskNo()) ? "like" : null,
                        "%" + searchDto.getThirdTaskNo() + "%")
                .addWithValueQueryParam("createTime", ">=", searchDto.getCreateStartDate())
                .addWithValueQueryParam("createTime", "<=", searchDto.getCreateEndDate())
                .addWithValueQueryParam("pushTime", ">=", searchDto.getPushStartDate())
                .addWithValueQueryParam("pushTime", "<=", searchDto.getPushEndDate());
        List<Sort> sortList =
                (CollectionUtils.isEmpty(searchDto.getSortDtoList()))
                        ? new ArrayList<Sort>() : PropertiesUtils.copyList(Sort.class, searchDto.getSortDtoList());
        sortList.add(new Sort("createTime", Sort.Direction.DESC));
        int totalSize = msgTaskDAO.countQuery(builder.build());
        List<MsgTask> list = msgTaskDAO.query(builder.build(), sortList, searchDto.getStart(), searchDto.getPageSize());
        List<MsgTaskDto> msgTaskDtos = PropertiesUtils.copyListNotExcludeRepeat(MsgTaskDto.class, list);
        for (int i = 0; i < msgTaskDtos.size(); i++) {
            msgTaskDtos.get(i).setDeviceType(list.get(i).getDeviceType().getMsg());
            msgTaskDtos.get(i).setStatus(list.get(i).getStatus().getMsg());
        }
        return new MessagePageResultDto<>(msgTaskDtos, totalSize, searchDto.getPageSize(), searchDto.getPageNo());
    }

    @Override
    public List<MsgTask> getByBatchNo(String batchNo) {
        MsgTask task = new MsgTask();
        task.setBatchNo(batchNo);
        return msgTaskDAO.get(task);
    }

    @Override
    public void destroy() throws Exception {
        executor.shutdown();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        executor = new ThreadPoolExecutor(1, 20, 5L, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>(),
                new MsgTaskServiceThreadFactory());
    }

    /**
     * 推送任务线程工厂
     */
    static class MsgTaskServiceThreadFactory implements ThreadFactory {

        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        MsgTaskServiceThreadFactory() {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() :
                    Thread.currentThread().getThreadGroup();
            namePrefix = "msgTaskService-pool-" +
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

}
