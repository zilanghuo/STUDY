package com.mouse.study;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lwf on 2017/10/11.
 * use to do:
 */
public class TestDemo {


    public static void main(String[] args) {
        String str = "473";
        String patten = "[+-_]";

        Pattern compile = Pattern.compile(patten);
        Matcher matcher = compile.matcher(str);
        System.out.println(matcher.find());
    }



}
