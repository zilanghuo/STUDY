package com.zdmoney.mq.client.producer;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.client.producer.SendStatus;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.zdmoney.mq.client.MqByteMessage;
import com.zdmoney.mq.client.MqMessage;
import com.zdmoney.mq.client.MqSendResult;
import com.zdmoney.mq.client.config.MqConfig;
import com.zdmoney.mq.client.config.MqConfigException;
import com.zdmoney.mq.client.group.MqGroup;
import com.zdmoney.mq.client.producer.handler.MqSendFailHandler;
import com.zdmoney.mq.client.producer.handler.MqSendHandler;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by rui on 16/8/24.
 */
@Data
@Slf4j
public class MqProducer implements InitializingBean, DisposableBean {

    private MqConfig mqConfig;

    private String group;

    private String topic;

    private String tag;

    private DefaultMQProducer producer;

    private ExecutorService executor;

    private AtomicBoolean initialized = new AtomicBoolean(false);


    public synchronized void init() {
        if (initialized.get()) {
            return;
        }
        log.info("init {} ...", this.getClass().getName());
        this.validate();
        try {
            producer = MqProducerContainer.build(mqConfig, group);
            log.info("init {} success", this.getClass().getName());
            initialized.set(true);
        } catch (Exception e) {
            log.error("init {} error", this.getClass().getName(), e);
        }

    }


    public MqSendResult send(MqByteMessage byteMessage) {
        log.info("send origin byteMessage {}", byteMessage.toString());
        return this.send(new Message(topic, tag,
                byteMessage.getKey(), byteMessage.getData()));
    }


    public MqSendResult send(MqByteMessage byteMessage, MqSendFailHandler failHandler) {
        log.info("send origin byteMessage {}", byteMessage.toString());
        MqSendResult sendResult = this.send(new Message(topic, tag,
                byteMessage.getKey(), byteMessage.getData()));
        if (sendResult.isFail()) {
            executor.submit(new MqSendFailHandlerTask(failHandler));
        }
        return sendResult;
    }


    public MqSendResult send(MqByteMessage byteMessage, MqSendHandler handler) {
        log.info("send origin byteMessage {}", byteMessage.toString());
        handler.before();
        MqSendResult sendResult = this.send(new Message(topic, tag,
                byteMessage.getKey(), byteMessage.getData()));
        handler.complete();
        if (sendResult.isFail()) {
            handler.fail();
        }
        if (sendResult.isSuccess()) {
            handler.success();
        }
        return sendResult;
    }


    public MqSendResult send(MqMessage sendMessage) {
        log.info("send origin message {}", sendMessage.toString());
        return this.send(new Message(topic, tag,
                sendMessage.getKey(), sendMessage.getData().getBytes()));
    }

    public MqSendResult send(MqMessage sendMessage, MqSendFailHandler failHandler) {
        log.info("send origin message {}", sendMessage.toString());
        MqSendResult sendResult = this.send(new Message(topic, tag,
                sendMessage.getKey(), sendMessage.getData().getBytes()));
        if (sendResult.isFail()) {
            executor.submit(new MqSendFailHandlerTask(failHandler));
        }
        return sendResult;
    }

    public MqSendResult send(MqMessage sendMessage, MqSendHandler handler) {
        log.info("send origin message {}", sendMessage.toString());
        handler.before();
        MqSendResult sendResult = this.send(new Message(topic, tag,
                sendMessage.getKey(), sendMessage.getData().getBytes()));
        handler.complete();
        if (sendResult.isFail()) {
            handler.fail();
        }
        if (sendResult.isSuccess()) {
            handler.success();
        }
        return sendResult;
    }

    private MqSendResult send(Message message) {
        MqSendResult sendResult;
        try {
            log.info("send message {}", message.toString());
            SendResult result = producer.send(message);
            log.info("send message result {}", result.toString());
            if (result.getSendStatus() == SendStatus.SEND_OK) {
                sendResult = new MqSendResult(MqSendResult.Status.SUCCESS);
            } else {
                sendResult = new MqSendResult(MqSendResult.Status.PROCESSING, result.getSendStatus().name());
            }
        } catch (Exception e) {
            log.error("send message error", e);
            sendResult = new MqSendResult(MqSendResult.Status.FAIL, e.getMessage());
        }
        return sendResult;
    }


    private void validate() {
        if (this.mqConfig == null) {
            throw new MqConfigException("not set mqConfig for producer");
        }

        if (this.mqConfig.getAddress() == null) {
            throw new MqConfigException("not set address for producer");
        }

        if (this.topic == null) {
            throw new MqConfigException("not set topic for producer");
        }

        if (this.tag == null) {
            throw new MqConfigException("not set tag for producer");
        }

        if (this.group == null) {
            throw new MqConfigException("not set group for producer");
        }
        MqGroup.validate(group, topic, tag);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        init();
        executor = new ThreadPoolExecutor(1, 10, 5L, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>(),
                new MqSendFailHandlerThreadFactory());
    }

    @Override
    public void destroy() throws Exception {
        producer.shutdown();
        executor.shutdown();
    }


    static class MqSendFailHandlerThreadFactory implements ThreadFactory {

        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        MqSendFailHandlerThreadFactory() {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() :
                    Thread.currentThread().getThreadGroup();
            namePrefix = "mqSendFailHandler-pool-" +
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


    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        /*DefaultMQProducer producer = new DefaultMQProducer("TEST");
        producer.setNamesrvAddr("172.17.34.150:9876");
        producer.start();

        Message message = new Message("TEST","TEST","1","TEST".getBytes());
        SendResult result = producer.send(message);
        System.out.println(result);*/


        DefaultMQProducer producer = new DefaultMQProducer("TEST");
        producer.setNamesrvAddr("172.17.34.150:9876");
        try {
            producer.start();

            Message msg = new Message("TEST",
                    "TEST",
                    "1",
                    "Just for test1.".getBytes());
            System.out.println(msg.toString());
            SendResult result = producer.send(msg);
            System.out.println("id:" + result.getMsgId() +
                    " result:" + result.getSendStatus());

            System.out.println("======================================");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            producer.shutdown();
        }
    }
}
