package com.mouse.study.test.arithmetic.intersect;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lwf
 * @date 2018/7/19
 * use:
 */
public class TestDemo {

    //a\b 各10个元素 非排序的情况下
    private static final String aStr = new String("he ll ow or ld me too so yin gw en");
    private static final String bStr = new String("ni shi he ge hen hao de xiao huo zi");
    private static final String[] aSplit = aStr.split(" ");
    private static final String[] bSplit = bStr.split(" ");

    public static void main(String[] args) {
        intersectByNotSort();
    }

    /**
     * 未排序交集
     */
    private static void intersectByNotSort() {
        List<String> result = new ArrayList();
        int size = 0;
        for (String a : aSplit) {
            for (String b : bSplit) {
                size++;
                if (a.equals(b)) {
                    result.add(a);
                }
            }
        }
        System.out.println("时间复杂度（M*N）：" + size + ",结果集：" + result);
    }
}
