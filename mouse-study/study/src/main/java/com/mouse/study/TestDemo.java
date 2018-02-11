package com.mouse.study;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lwf on 2017/10/11.
 * use to do:
 */
public class TestDemo {


    public static void main(String[] args) {
        HashMap<String,String> map = new HashMap(3);
        map.put("riskResult",null);
        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next().getValue());
        }
    }

    /**
     * 分割字段
     *
     * @param originList
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> partitionList(List<T> originList) {
        int BATCH_SIZE = 4;
        List<List<T>> partitionList = new ArrayList();
        if (null != originList && originList.size() != 0) {

            int allSize = originList.size();
            int modSize = allSize % BATCH_SIZE;
            int multipleSize = allSize / BATCH_SIZE;

            for (int i = 0; i <= multipleSize; i++) {

                if (modSize == 0) {
                    List<T> info = originList.subList(i * BATCH_SIZE, (i + 1) * BATCH_SIZE);
                    partitionList.add(info);
                } else {
                    if (i == multipleSize) {
                        List<T> info = originList.subList(i * BATCH_SIZE, (i * BATCH_SIZE) + modSize);
                        partitionList.add(info);
                    } else {
                        List<T> info = originList.subList(i * BATCH_SIZE, (i + 1) * BATCH_SIZE);
                        partitionList.add(info);
                    }
                }
            }
        }
        return partitionList;
    }


    private static void matchPattern() {
        String str = "473";
        String patten = "[+-_]";

        Pattern compile = Pattern.compile(patten);
        Matcher matcher = compile.matcher(str);
        System.out.println(matcher.find());
    }


}
