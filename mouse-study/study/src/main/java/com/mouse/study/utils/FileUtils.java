package com.mouse.study.utils;


import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

/**
 * Created by lwf on 2017/7/26.
 * use to do:
 */
public class FileUtils {


    public static ArrayList<String> traverseFolder(String path,ArrayList<String> gzFiles) {
        File file = new File(path);
        if (file.exists()) {
            File[] subfiles = file.listFiles();
            if (subfiles.length == 0) {
                return gzFiles;
            } else {
                for (File subFile : subfiles) {
                    if (subFile.isDirectory()) {
                        traverseFolder(subFile.getAbsolutePath(),gzFiles);
                    } else {
                        if (subFile.getName().endsWith(".gz")) {
                            System.out.println(subFile.getAbsolutePath());
                            gzFiles.add(subFile.getAbsolutePath());
                        }
                    }
                }
            }
        } else {
            System.out.println("文件不存在!");
        }
        return gzFiles;
    }

    public static void put(String str) {
        try {
            final FileWriter fileWriter = new FileWriter("f:\\text.txt", true);
            fileWriter.write(str + "\r");
            fileWriter.close();
        } catch (Exception e) {
        }
    }
}
