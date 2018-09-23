package com.mouse.study.test.javaList;

import org.junit.Test;

import java.util.*;

/**
 * @author laiwufa
 * @date 2018/8/24
 * use:
 */
public class ListDemo {

    @Test
    public void fastFail() {
        HashMap<String, Integer> map = new HashMap(8);
        for (int i = 0; i < 5; i++) {
            map.put(i + "", i);
        }
        Iterator<Map.Entry<String, Integer>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, Integer> next = iterator.next();
            if (next.getValue() == 2){
                map.put("10",10);
            }
        }
    }

    @Test
    public void testRead() {
        add(new ArrayList());
        add(new LinkedList());
        add(new Vector());
    }

    public static void add(List<Integer> list) {
        Long begin = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            list.add(i);
        }
        Long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - begin));
        System.out.println("size:" + list.size());
    }

    @org.junit.Test
    public void testStack() {
        Stack<Integer> stackOne = new Stack();
        for (int i = 0; i < 10; i++) {
            stackOne.push(i);
        }

        Stack<Integer> stackTwo = new Stack();
        for (int i = 0; i < 10; i = i + 2) {
            stackTwo.push(i);
        }


    }

    @org.junit.Test
    public void testVector() {
        Vector<Integer> vectorOne = new Vector();
        for (int i = 0; i < 10; i++) {
            vectorOne.add(i);
        }
        Vector<Integer> vectorTwo = new Vector();
        for (int i = 0; i < 10; i = i + 2) {
            vectorTwo.add(i);
        }
        vectorOne.addAll(vectorTwo);
        System.out.println(vectorOne);
        System.out.println(vectorOne.firstElement());
        System.out.println(vectorOne.lastElement());
    }

    @org.junit.Test
    public void testBatchRemove() {
        ArrayList<String> listOne = new ArrayList();
        for (int i = 0; i < 10; i++) {
            listOne.add(i + "");
        }
        ArrayList<String> listTwo = new ArrayList();
        for (int i = 1; i < 10; i = i + 2) {
            listTwo.add(i + "");
        }
        //包含重复的数据
        listOne.addAll(listTwo);
        System.out.println(listOne);

    }


    @org.junit.Test
    public void testRetain() {
        List<String> listOne = new ArrayList();
        for (int i = 0; i < 10; i++) {
            listOne.add(i + "");
        }
        List<String> listTwo = new ArrayList();
        for (int i = 1; i < 10; i = i + 2) {
            listTwo.add(i + "");
        }
        listOne.retainAll(listTwo);
        System.out.println(listOne);
    }

}
