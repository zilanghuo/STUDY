package com.mouse.study.test.sort;

import com.mouse.study.utils.JackJsonUtil;

/**
 * @author lwf
 * @date 2018/3/12
 * use: 插入排序
 */
public class InsertionSort {


    public static void main(String[] args) {
        Integer[] arr = {6, 5, 3, 1, 8, 7, 2, 4};
        sort(arr);

    }

    /**
     * 一个一个排序,小---->大
     *
     * @param arr
     */
    public static void sort(Integer[] arr) {

        for (int i = 0; i < arr.length - 1; i++) {
            //当前元素：arr[i]
            for (int j = i + 1; j > 0; j--) {
                if (arr[j - 1] <= arr[j]) {
                    break;
                }
                Integer temp = arr[j];
                arr[j] = arr[j - 1];
                arr[j - 1] = temp;
                System.out.println("sort:" + JackJsonUtil.objToStr(arr));
            }
        }
    }
}
