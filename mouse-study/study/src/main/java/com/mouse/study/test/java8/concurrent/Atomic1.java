package com.mouse.study.test.java8.concurrent;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * @author Benjamin Winterberg
 */
public class Atomic1 {

    private static final int NUM_INCREMENTS = 1000;

    private static AtomicInteger atomicInt = new AtomicInteger(0);

    public static void main(String[] args) {
        testIncrement();
        testAccumulate();
        testUpdate();
        testDemo();
    }

    private static void testUpdate() {
        atomicInt.set(0);

        ExecutorService executor = Executors.newFixedThreadPool(2);

        IntStream.range(0, NUM_INCREMENTS)
                .forEach(i -> {
                    Runnable task = () ->
                            atomicInt.updateAndGet(n -> n + 2);
                    executor.submit(task);
                });

        ConcurrentUtils.stop(executor);

        System.out.format("Update: %d\n", atomicInt.get());
    }

    private static void testAccumulate() {
        atomicInt.set(0);

        ExecutorService executor = Executors.newFixedThreadPool(2);

        IntStream.range(0, NUM_INCREMENTS)
                .forEach(i -> {
                    Runnable task = () ->
                            atomicInt.accumulateAndGet(i, (n, m) -> n + m);
                    executor.submit(task);
                });

        ConcurrentUtils.stop(executor);

        System.out.format("Accumulate: %d\n", atomicInt.get());
    }

    private static void testIncrement() {
        atomicInt.set(0);

        ExecutorService executor = Executors.newFixedThreadPool(2);

        IntStream.range(0, NUM_INCREMENTS)
                .forEach(i -> executor.submit(atomicInt::incrementAndGet));

        ConcurrentUtils.stop(executor);

        System.out.format("Increment: Expected=%d; Is=%d\n", NUM_INCREMENTS, atomicInt.get());
    }

    private static void testDemo() {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        executor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("new Runnable");
            }
        });
        executor.execute(() -> System.out.println("---> Runnable"));

    }

    private static void testList() {
        //Old way:
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
        int sum = 0;
        for (Integer n : list) {
            int x = n * n;
            sum = sum + x;
        }
        System.out.println(sum);

        //New way:
        List<Integer> list2 = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
        int sum2 = list2.stream().map(x -> x * x).reduce((x, y) -> x + y).get();
        System.out.println(sum2);
    }

}
