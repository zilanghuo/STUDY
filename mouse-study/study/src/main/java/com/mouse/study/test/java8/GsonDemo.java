package com.mouse.study.test.java8;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Data;

import java.util.Date;

/**
 * Created by lwf on 2017/9/18.
 * use to do:Gson 模板
 */
public class GsonDemo {

    @org.junit.Test
    public void demoTime(){
        Gson gson = new Gson();

        GsonModel gsonModel = new GsonModel();
        gsonModel.setId(100);
        gsonModel.setCreateTime(new Date());
        gsonModel.setName("芦苇");
        gsonModel.setGood("溜槽");

        String toJson = gson.toJson(gsonModel);
        String jso = "{\"id\":100,\"name\":\"芦苇\",\"createTime\":\"2017-01-04 12:21:23:14\"}";
        System.out.println(toJson);
        GsonModel x = gson.fromJson(jso, GsonModel.class);
        System.out.println(x);
        System.out.println(x.getCreateTime().getTime());
    }

    @org.junit.Test
    public void DemoFormatTime(){
        Gson gson = new GsonBuilder()
                .serializeNulls()
                .setDateFormat("yyyy-MM-dd HH:mm:ss:SSS")
                .disableHtmlEscaping()
                //格式化输出
                .setPrettyPrinting()
                .create();
        String jso = "{\"id\":100,\"name\":\"芦苇\",\"createTime\":\"2017-01-04 12:21:23:144\"}";
        GsonModel x = gson.fromJson(jso, GsonModel.class);
        System.out.println(x);
        System.out.println(x.getCreateTime().getTime());

    }
}

@Data
class GsonModel{

    private int id;

    private String name;

    private Date createTime;

    protected String setGood(String s){
        return s;
    }

}
