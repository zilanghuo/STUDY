package com.mouse.study.test;

import com.mouse.study.test.es.model.People;
import com.mouse.study.utils.JackJsonUtil;
import net.sf.json.JSONObject;

/**
 * @author lwf
 * @date 2017/11/21
 * use:
 */
public class Test {

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
