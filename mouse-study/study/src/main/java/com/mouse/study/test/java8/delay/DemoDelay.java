package com.mouse.study.test.java8.delay;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * Created by lwf on 2017/9/20.
 * use to do:
 */
@Data
@AllArgsConstructor
public class DemoDelay<T> implements Delayed {
    private T t;
    private long liveTime;
    private long removeTime;

    @Override
    public int compareTo(Delayed o) {
        if (o == null) return 1;
        if (o == this) return 0;
        if (o instanceof DemoDelay) {
            DemoDelay<T> tmpDelayedItem = (DemoDelay<T>) o;
            if (liveTime > tmpDelayedItem.liveTime) {
                return 1;
            } else if (liveTime == tmpDelayedItem.liveTime) {
                return 0;
            } else {
                return -1;
            }
        }
        long diff = getDelay(TimeUnit.NANOSECONDS) - o.getDelay(TimeUnit.NANOSECONDS);
        return diff > 0 ? 1 : diff == 0 ? 0 : -1;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(removeTime - System.nanoTime(), unit);
    }

    @Override
    public int hashCode() {
        return t.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof DemoDelay) {
            return object.hashCode() == hashCode() ? true : false;
        }
        return false;
    }
}
