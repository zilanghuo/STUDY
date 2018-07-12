package com.mouse.study.test;

import com.mouse.study.api.utils.DateUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author lwf
 * @date 2017/11/21
 * use:
 */
public class Test {


    @org.junit.Test
    public void testReg() {
        String reg = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$";
        Pattern pattern = Pattern.compile(reg);
        Matcher m = pattern.matcher("543gjhgXER");
        Matcher m2 = pattern.matcher("5gjXER");
        boolean matches = m.matches();
        System.out.println(matches);

    }

    @org.junit.Test
    public void testDate() {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.DAY_OF_MONTH, -1);
        now.set(Calendar.HOUR, 1);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.MILLISECOND, 1);
        Date startDate = now.getTime();
        System.out.println(DateUtils.format(startDate));

    }


    public static void main(String[] args) {
        Random random = new Random();
        int max = 9999;
        int min = 1000;

        for (int i = 0; i < 100; i++) {
            int size = random.nextInt(max)%(max-min+1) + min;
            System.out.println(size);
        }


    }
}
