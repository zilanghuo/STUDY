package com.mouse.study.utils;

import java.util.Calendar;

/**
 * @author lwf
 * @date 2018/1/30
 * use:
 */
public class IdCardUtils {

    public static void main(String[] args) {
        System.out.println(Integer.parseInt(getBirthday("350322199112136814")));
        System.out.println(Integer.parseInt(getBirthday("113343450321432")));
        System.out.println(getAge("350322199112136814"));
        System.out.println(getAge("113343450321432"));
    }

    /**
     * 获取年龄
     */
    public static Integer getAge(String idCardNo) {
        int i = Calendar.getInstance().get(Calendar.YEAR);
        if (idCardNo.length() == 18) {
            String substring = idCardNo.substring(6, 10);
            return i - Integer.parseInt(substring);
        } else {
            String subString = "19" + idCardNo.substring(6, 8);
            return i - Integer.parseInt(subString);
        }
    }

    /**
     * 获取生日
     *
     * @param idCardNo
     * @return
     */
    public static String getBirthday(String idCardNo) {
        if (idCardNo.length() == 18) {
            return idCardNo.substring(10, 12);
        } else {
            return idCardNo.substring(8, 10);
        }
    }

    /**
     * 获取男女
     *
     * @param idCardNo
     * @return
     */
    public static String getSex(String idCardNo) {
        Integer i = 0;
        if (idCardNo.length() == 18) {
            i = Integer.parseInt(String.valueOf(idCardNo.charAt(16)));
        } else if (idCardNo.length() == 15) {
            i = Integer.parseInt(String.valueOf(idCardNo.charAt(14)));
        }
        System.out.println(i.toString());
        System.out.println(i % 2);
        if ((i % 2) == 0) {
            return "女";
        } else {
            return "男";
        }
    }

}
