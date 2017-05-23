package com.mouse.trace.utils;

import com.google.gson.Gson;


/**
 * Created by rui on 15/8/14.
 */
public class JsonUtils<D> {

    public static<D> D fromJson(String json,Class<D> d){
        Gson gson = new Gson();
        return gson.fromJson(json,d);
    }


    public static String toJson(Object o){
        Gson gson = new Gson();
        return gson.toJson(o);
    }

}
