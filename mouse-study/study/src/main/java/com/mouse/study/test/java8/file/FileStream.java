package com.mouse.study.test.java8.file;

import java.io.*;
import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lwf
 * @date 2018/6/6
 * use:
 */
public class FileStream {

    public static void main(String[] args) {
        File file = new File("F:\\investasset_2018-06-05.txt");
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
        String result;
        AtomicInteger size = new AtomicInteger(0);
        try {
            while ((result = bufferedReader.readLine()) != null) {
                System.out.println("pre:------1" + result + "2--------" );
                size.incrementAndGet();
                String[] data = result.split("\\|");
                System.out.println(data[0] + "--" + new BigDecimal(data[1]) + "--" + new BigDecimal(data[2]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(size.get());
    }

}
