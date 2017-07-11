package com.mouse.resource.list;

import java.util.*;

/**
 * Created by lwf on 2017/7/1.
 * use to do:
 */
public class ListDemo {

    @org.junit.Test
    public void setDemo() throws Exception{
        Set hashSet = new HashSet();
        Set treeSet = new TreeSet();
        Set linkedHashSet = new LinkedHashSet();
        Map hashMap = new HashMap();
        Map synchMap = Collections.synchronizedMap(new HashMap());//实现线程安全

        List<String> list = new ArrayList();


        LinkedHashMap<String, Integer> lmap = new LinkedHashMap<String, Integer>();
        lmap.put("语文", 1);
        lmap.put("数学", 2);
        lmap.put("英语", 3);
        lmap.put("历史", 4);
        lmap.put("政治", 5);
        lmap.put("地理", 6);
        lmap.put("生物", 7);
        lmap.put("化学", 8);
        for(Map.Entry<String, Integer> entry : lmap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        System.out.println( UUID.randomUUID().version());
        System.out.println( UUID.randomUUID().variant());

        System.out.println(1 << 30);
        System.out.println(5 >> 1);
        System.out.println(3 ^ 2);


        String str = "账户:PERSONAL_WITHDRAW_FRZ20170707001更新余额异常：【PERSONA";
        System.out.println(str.length());
        System.out.println(str.length() >10?str.substring(0,10):str);


    }

}
