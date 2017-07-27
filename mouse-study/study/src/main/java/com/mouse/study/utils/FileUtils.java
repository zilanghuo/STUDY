package com.mouse.study.utils;

import java.io.FileWriter;

/**
 * Created by lwf on 2017/7/26.
 * use to do:
 */
public class FileUtils {

    public static void put(String str) {
        try {
            final FileWriter fileWriter = new FileWriter("f:\\text.txt", true);
            fileWriter.write(str + "\r");
            fileWriter.close();
        }catch (Exception e){
        }
    }
}
