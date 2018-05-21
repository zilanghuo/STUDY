package com.mouse.study.test.hutool;

import cn.hutool.core.collection.CollUtil;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lwf
 * @date 2018/5/18
 * use:
 */
public class CollUtilsTest {


    @org.junit.Test
    public void reverse(){
        List<Integer> integerList = new ArrayList();
        for (int i = 0; i < 200; i++) {
            integerList.add(i);
        }
        TestCase.assertEquals(new Integer(0),integerList.get(0));
        List<Integer> reverseList = CollUtil.reverseNew(integerList);
        TestCase.assertEquals(new Integer(199),reverseList.get(0));
        TestCase.assertEquals(new Integer(0),integerList.get(0));

    }

    @org.junit.Test
    public void group(){
        List<Integer> integerList = new ArrayList();
        for (int i = 0; i < 200; i++) {
            integerList.add(i);
        }
        List<List<Integer>> group = CollUtil.group(integerList, new CollUtil.Hash<Integer>() {
            @Override
            public int hash(Integer o) {
                return o % 5;
            }
        });
        TestCase.assertNotNull(group);
        TestCase.assertEquals(5,group.size());
        TestCase.assertEquals(40,group.get(0).size());

        List<List<Integer>> lists = CollUtil.group(integerList, null);
        TestCase.assertEquals(200,lists.size());
        System.out.println(new Integer(150502).hashCode());

    }

}
