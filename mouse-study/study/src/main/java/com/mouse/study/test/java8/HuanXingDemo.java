package com.mouse.study.test.java8;

import com.mouse.study.api.utils.DateUtils;
import lombok.Data;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lwf
 * @date 2018/7/12
 * use: 环形队列，每秒轮询,根据对象的index进行(是否会出现modifyException)
 */
public class HuanXingDemo {

    public static void main(String[] args) throws Exception {
        //10轮询一次
        Vector<Set<HuanDto>> setList = new Vector(10);
        for (int i = 0; i < 10; i++) {
            Set<HuanDto> dtos = new TreeSet();
            setList.add(dtos);
        }
        AtomicInteger currentIndex = new AtomicInteger(0);
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(2,
                new BasicThreadFactory.Builder().namingPattern("example-schedule-pool-%d").daemon(true).build());

        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println(DateUtils.formatCurDate() + ":" + currentIndex.get());
                Set<HuanDto> huanDtos = setList.get(currentIndex.get());
                currentIndex.incrementAndGet();
                if (currentIndex.get() == setList.size()) {
                    currentIndex.set(0);
                }
              //  throw new NullPointerException("12432");
            }
        }, 0, 1, TimeUnit.SECONDS);
        Thread.sleep(50 * 1000);
    }
}

@Data
class HuanDto {

    private Integer currentIndex;

    private Object task;

}
