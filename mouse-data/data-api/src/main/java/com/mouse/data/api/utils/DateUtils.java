package com.mouse.data.api.utils;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by hrs on 2014/7/25.
 */
public class DateUtils {

    /**
     * 默认日期字符串格式
     */
    public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final int DAY_SECOND = 3600;

    public static final int MUNITE_SECOND = 60;

    public static Date parse(String date) {
        return parse(date, DEFAULT_FORMAT);
    }

    /**
     * 根据格式把字符串转化为时间
     *
     * @param date   字符串时间
     * @param format 格式
     * @return
     */
    public static Date parse(String date, String format) {
        if (StringUtils.isBlank(date)) {
            return null;
        }
        DateTimeFormatter df = DateTimeFormat.forPattern(format);
        return DateTime.parse(date, df).toDate();
    }

    /**
     * 当前时间转化成字符串
     * <p>格式yyyy-MM-dd HH:mm:ss</p>
     *
     * @return
     */
    public static String formatCurDate() {
        return format(new Date(), DEFAULT_FORMAT);
    }

    /**
     * 根据格式把时间转化成字符串
     * <p>默认格式yyyy-MM-dd HH:mm:ss</p>
     *
     * @param date 时间
     * @return
     */
    public static String format(Date date) {
        return format(date, DEFAULT_FORMAT);
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
     * 根据格式把毫秒数转化成字符串
     *
     * @param milliseconds 毫秒
     * @param format       格式
     * @return
     */
    public static String format(Long milliseconds, String format) {
        if (milliseconds == null) {
            return "";
        }
        return new DateTime(milliseconds).toString(format);
    }


    /**
     * 根据时间添加年数
     *
     * @param date  时间
     * @param years 年
     * @return
     */
    public static Date plusYeas(Date date, int years) {
        return new DateTime(date.getTime()).plusYears(years).toDate();
    }

    /**
     * 根据时间添加天数
     *
     * @param date 时间
     * @param days 天数，可为负数
     * @return
     */
    public static Date plusDays(Date date, int days) {
        return new DateTime(date.getTime()).plusDays(days).toDate();
    }

    /**
     * 根据时间添加月数
     *
     * @param date   时间
     * @param months 月数，可为负数
     * @return
     */
    public static Date plusMonths(Date date, int months) {
        return new DateTime(date.getTime()).plusMonths(months).toDate();
    }


    /**
     * 根据时间添加小时数
     * @param date
     * @param hours
     * @return
     */
    public static Date plusHours(Date date,int hours){
        return new DateTime(date.getTime()).plusHours(hours).toDate();
    }

    /**
     * 根据时间添加时分数
     * @param date
     * @param minutes
     * @return
     */
    public static Date plusMinutes(Date date,int minutes){
        return new DateTime(date.getTime()).plusMinutes(minutes).toDate();
    }


    /**
     * 根据时间添加星期数
     * @param date
     * @param weeks
     * @return
     */
    public static Date plusWeeks(Date date,int weeks){
        return new DateTime(date.getTime()).plusWeeks(weeks).toDate();
    }


    /**
     * 根据时间添加秒数
     * @param date
     * @param seconds
     * @return
     */
    public static Date plusSeconds(Date date,int seconds){
        return new DateTime(date.getTime()).plusSeconds(seconds).toDate();
    }

    /**
     * 2个时间天数差
     *
     * @param start
     * @param end
     * @return
     */
    public static int daysBetween(Date start, Date end) {
        LocalDate startLd = new LocalDate(start.getTime());
        LocalDate endLd = new LocalDate(end.getTime());
        return Days.daysBetween(startLd, endLd).getDays();
    }


    /**
     * 是否为同一天
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        String d1 = format(date1, "yyyy-MM-dd");
        String d2 = format(date2, "yyyy-MM-dd");
        return isSameDay(d1, d2);
    }


    public static boolean isSameDay(String date1, String date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        return StringUtils.equals(date1, date2);
    }

    /**
     * 得到以当前时间转化为long
     * <p>yyyyMMddHHmmss</p>
     *
     * @return
     */
    public static Long getCurTimeNum() {
        return getDateNum(new Date(), "yyyyMMddHHmmss");
    }

    /**
     * 得到以当前日期转化为long
     * <p>yyyyMMdd</p>
     *
     * @return
     */
    public static Long getCurDateNum() {
        return getDateNum(new Date(), "yyyyMMddHH");
    }

    /**
     * 得到日期转化为long
     * <p>yyyyMMdd</p>
     *
     * @return
     */
    public static Long getDateNum(Date date, String pattern) {
        return Long.valueOf(format(date, pattern));
    }

    /**
     * 获取当前时间秒
     *
     * @return
     */
    public static int getCurrentSecond() {
        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);
        int currentSecond = currentHour * DAY_SECOND + currentMinute * MUNITE_SECOND;
        return currentSecond;
    }

    /**
     * dt 是否在startDt 和 endDt 范围 内
     *
     * @param dt
     * @param startDt
     * @param endDt
     * @return
     */
    public static boolean isBetween(Date dt, Date startDt, Date endDt) {
        long time = dt.getTime();
        return (startDt.getTime() <= time && time <= endDt.getTime());
    }

    public static boolean isBetween(Date startDt, Date endDt) {
        return isBetween(new Date(), startDt, endDt);
    }

    public static boolean isNotBetween(Date startDt, Date endDt) {
        return !isBetween(startDt, endDt);
    }

    public static boolean isGt(Date date1, Date date2) {
        return (date1.getTime() > date2.getTime());
    }

    public static boolean isLt(Date date1, Date date2) {
        return !isGt(date1, date2);
    }

    /**
     * 星期几
     * @param date  日期
     * @return week
     */
    public static int dayOfWeek(Date date) {
        return new DateTime(date.getTime()).getDayOfWeek();
    }

    public String week(Date date) {
        if (date != null) {
            DateTime dateTime = new DateTime(date.getTime());
            return "" + dateTime.getDayOfWeek();
        }
        return null;
    }


//    public static String getWeek(Date date){
//        DateTime dateTime = new DateTime(date.getTime());
//        String showDate = "";
//        switch(dateTime.getDayOfWeek()){
//            case   1:
//                showDate = "星期一";
//                break;
//            case   2:
//                showDate = "星期二";
//                break;
//            case   3:
//                showDate = "星期三";
//                break;
//            case   4:
//                showDate = "星期四";
//                break;
//            case  5:
//                showDate = "星期五";
//                break;
//            case   6:
//                showDate = "星期六";
//                break;
//            default:
//                showDate = "星期日";
//                break;
//        }
//        return showDate;
//    }


    public static boolean isSameDateTime(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        return date1.getTime() == date2.getTime();
    }

    public static String getDateBegin(Date date) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        return format(date, "yyyy-MM-dd 00:00:00");
    }

    public static String getDateEnd(Date date) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        return format(date, "yyyy-MM-dd 23:59:59");
    }


    public static void main(String[] args) {
        //2014-10-01 15:07:43	2014-11-30 15:07:49
        /*DateUtils.parse("2014-11-30 15:07:49", DEFAULT_FORMAT);
        boolean lt = isLt(new Date(), DateUtils.parse("2014-11-30 15:07:49", DEFAULT_FORMAT));
        System.out.println(lt);*/
    }

    /**
     * 根据格式把字符串转化为时间
     * 有这样一个字符串："20070911121547"，
     * 转换成时间格式：2007-09-11 12:15:47
     *
     * @param str   字符串时间
     * @return
     */
    public static String parseTime(String str) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String transTime = null;
        try {
            Date date = df.parse(str);
            df.applyPattern("yyyy-MM-dd HH:mm:ss");
            transTime = df.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return transTime;
    }
}
