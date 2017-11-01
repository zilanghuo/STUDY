package com.mouse.study.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author lwf
 */
public class DateUtils {

    private static Logger log = LogManager.getLogger(DateUtils.class);

    /**
     * 默认日期字符串格式
     */
    public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String ES_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    public static final Integer MAX_SECOND = 3600;

    public static String formatByEsForDate(Date date) {
        return format(date, ES_FORMAT);
    }

    public static Date getDateByTimes(Long times) {

        SimpleDateFormat format = new SimpleDateFormat(ES_FORMAT);
        String d = format.format(times);
        Date date = null;
        try {
            date = format.parse(d);
        } catch (ParseException e) {
            e.printStackTrace();
            log.error("时间格式转换错误");
        }
        return date;
    }

    public static Date formatForString(String string) {
        try {
            return DateFormat.getDateInstance().parse(string);
        } catch (ParseException e) {
            log.error("时间格式转换失败");
        }
        return new Date();
    }

    public static String formatByEsForCurDate() {
        return format(new Date(), ES_FORMAT);
    }


    /**
     * 根据格式把时间转化成字符串
     *
     * @param date   时间
     * @param format 格式
     * @return
     */
    public static String format(Date date, String format) {
        if (date == null) {
            return "";
        }
        return new DateTime(date.getTime()).toString(format);
    }

    /**
     * 时间加秒
     */
    public static Date addSecond(Date date, int sec) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int hours = 0;
        int seconds = sec;
        if (Math.abs(sec) > MAX_SECOND) {
            hours = sec / MAX_SECOND;
            seconds = sec % MAX_SECOND;
            cal.add(Calendar.HOUR, hours);
        }
        cal.add(Calendar.SECOND, seconds);
        System.out.println("hours:【" + hours + "】,seconds:【" + seconds + "】");
        return cal.getTime();
    }

    public static void main(String[] args) {
        Date endTime = new Date();
        System.out.println(format(endTime, "yyyy-MM-dd HH:mm:ss"));
        Date date = addSecond(endTime, -60);
        System.out.println(format(date, "yyyy-MM-dd HH:mm:ss"));

    }
}
