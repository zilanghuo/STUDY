package com.mouse.study.test.es.mapping;


import com.mouse.study.test.es.model.Geo;
import com.mouse.study.utils.JackJsonUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by lwf on 2017/8/17.
 * use to do: 测试体
 */

@Slf4j
@Data
public class TestModel  {


    private String name;

    private Geo.GeoApoin location;

    private Integer ageOne;

    private Boolean useOne;

    private String startTimeOne;



    public static void main(String[] args) {
        TestModel testModel = new TestModel();
        testModel.setName("name1");
        testModel.setAgeOne(34);
        Geo.GeoApoin location = new Geo.GeoApoin("32", "32");
        testModel.setLocation(location);
        testModel.setUseOne(true);
        System.out.println(JackJsonUtil.objToStr(testModel));

    }
}
