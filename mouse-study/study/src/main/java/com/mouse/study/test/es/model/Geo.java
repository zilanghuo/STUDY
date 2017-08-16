package com.mouse.study.test.es.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by lwf on 2017/8/16.
 * use to do: 地点对象
 */
@Data
public class Geo {

    private GeoApoin location;

    private String name;


    @AllArgsConstructor
    @Data
    public static class GeoApoin {
        private String lat;

        private String lon;
    }
}


