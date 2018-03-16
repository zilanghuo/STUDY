package com.mouse.study.test.sort;

import java.util.Arrays;

/**
 * @author lwf
 * @date 2018/3/16
 * use: 冒泡排序：一个两次排序，第一个循环，得到最大值，这样逐一调用
 */
public class BubbleSort {


    public static void main(String[] args) {
        sort();
    }

    public static void sort() {
        Integer[] integers = {49, 38, 65, 97, 76, 13, 27};
        for (int i = 0; i < integers.length; i++) {
            for (int j = 0; j < integers.length - i -1; j++) {
                if (integers[j] > integers[j + 1]) {
                    int tmp = integers[j + 1];
                    integers[j + 1] = integers[j];
                    integers[j] = tmp;
                }
            }
            System.out.println(Arrays.toString(integers));
        }
    }
}

