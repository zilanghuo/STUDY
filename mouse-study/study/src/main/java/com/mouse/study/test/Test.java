package com.mouse.study.test;

import com.mouse.study.api.utils.DateUtils;
import com.mouse.study.test.es.model.People;
import com.mouse.study.utils.JackJsonUtil;
import net.sf.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author lwf
 * @date 2017/11/21
 * use:
 */
public class Test {




    @org.junit.Test
    public void testReg(){
        String reg = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$";
        Pattern pattern = Pattern.compile(reg);
        Matcher m=pattern.matcher("543gjhgXER");
        Matcher m2=pattern.matcher("5gjXER");
        boolean matches = m.matches();
        System.out.println(matches);

    }

    @org.junit.Test
    public void testDate(){
        Calendar now = Calendar.getInstance();
        now.add(Calendar.DAY_OF_MONTH,-1);
        now.set(Calendar.HOUR, 1);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.MILLISECOND, 1);
        Date startDate = now.getTime();
        System.out.println(DateUtils.format(startDate));

    }


    public static void main(String[] args) {
        People people = new People();
        people.setAgeOne(3);
        people.setNameOne("hell");
        people.setStartTimeOne(null);
        String data = JackJsonUtil.objToStr(people);
        System.out.println("data:{}");
        JSONObject json = JSONObject.fromObject(data);
        System.out.println("----------" + json.getString("startTimeOne"));
        System.out.println(JackJsonUtil.objToStr(people));
        //System.out.println("----------" + json.getString("gooo"));


        for (int i = 0; i < 5; i++) {
            switch (i) {
                case 0:
                    System.out.println(0);
                    return;
                case 2:
                    System.out.println(2);
                    return;
                default:
                    System.out.println("default");
            }
        }



    }
}
