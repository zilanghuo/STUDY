package com.mouse.study.test.java8.delay;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * Created by lwf on 2017/9/20.
 * use to do:
 */
public class Exam {

    public static void main(String[] args) throws InterruptedException {

        int studentNumber = 20;
        CountDownLatch countDownLatch = new CountDownLatch(studentNumber + 1);
        DelayQueue<Student> students = new DelayQueue<Student>();
        Random random = new Random();
        for (int i = 0; i < studentNumber; i++) {
            students.put(new Student("student" + (i + 1), 30 + random.nextInt(120), countDownLatch));
        }
        Thread teacherThread = new Thread(new Teacher(students));
        students.put(new EndExam(students, 120, countDownLatch, teacherThread));
        teacherThread.start();
        countDownLatch.await();
        System.out.println(" 考试时间到，全部交卷！");
    }

}

@NoArgsConstructor
@Data
class Student implements Runnable, Delayed {

    private String name;
    private long workTime;
    private long submitTime;
    private boolean isForce = false;
    private CountDownLatch countDownLatch;

    public Student(String name, long workTime, CountDownLatch countDownLatch) {
        this.name = name;
        this.workTime = workTime;
        //提交时间 = 当前时间 + 作答时间
        this.submitTime = TimeUnit.NANOSECONDS.convert(workTime, TimeUnit.NANOSECONDS) + System.nanoTime();
        this.countDownLatch = countDownLatch;
    }

    @Override
    public int compareTo(Delayed o) {
        // 按照作答时长正序排序（队头放的是你认为最先需要处理的元素，在这里体现为需要最先交卷，所以是正序）
        if (o == null || !(o instanceof Student)) return 1;
        if (o == this) return 0;
        Student s = (Student) o;
        if (this.workTime > s.workTime) {
            return 1;
        } else if (this.workTime == s.workTime) {
            return 0;
        } else {
            return -1;
        }
    }

    @Override
    public long getDelay(TimeUnit unit) {
        // 提交时间 - 当前时间  用来判断延迟是否到期（即是否可以提交试卷，可以进行take或者poll）
        // 返回正数：延迟还有多少时间到期。负数：延迟已经在多长时间前到期。负数代表可以take或者poll
        return unit.convert(submitTime - System.nanoTime(), TimeUnit.NANOSECONDS);
    }

    @Override
    public void run() {
        if (isForce) {
            System.out.println(name + " 交卷, 希望用时" + workTime + "分钟" + " ,实际用时 120分钟");
        } else {
            System.out.println(name + " 交卷, 希望用时" + workTime + "分钟" + " ,实际用时 " + workTime + " 分钟");
        }
        countDownLatch.countDown();
    }

}

class EndExam extends Student {

    private DelayQueue<Student> students;
    private CountDownLatch countDownLatch;
    private Thread teacherThread;

    public EndExam(DelayQueue<Student> students, long workTime, CountDownLatch countDownLatch, Thread teacherThread) {
        super("强制收卷", workTime, countDownLatch);
        this.students = students;
        this.countDownLatch = countDownLatch;
        this.teacherThread = teacherThread;
    }


    @Override
    public void run() {
        teacherThread.interrupt();
        Student tmpStudent;
        for (Iterator<Student> iterator2 = students.iterator(); iterator2.hasNext(); ) {
            tmpStudent = iterator2.next();
            tmpStudent.setForce(true);
            System.out.println(tmpStudent.getName() + "===" + tmpStudent.getDelay(TimeUnit.NANOSECONDS));
            tmpStudent.run();
        }
        countDownLatch.countDown();
    }

}

class Teacher implements Runnable {

    private DelayQueue<Student> students;

    public Teacher(DelayQueue<Student> students) {
        this.students = students;
    }

    @Override
    public void run() {
        try {
            System.out.println(" test start");
            while (!Thread.interrupted()) {
                Student s = students.take();
                System.out.println(s.getName() + "===" + s.getDelay(TimeUnit.NANOSECONDS));
                s.run();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
