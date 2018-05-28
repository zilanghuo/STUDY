package com.mouse.study.test.hutool;

import cn.hutool.core.util.ArrayUtil;

/**
 * @author lwf
 * @date 2018/5/28
 * use:
 */
public class ArrayUtilTest {

    public static void main(String[] args) {
        Integer size[] = new Integer[]{1,2,3,4,5};
        System.out.println(ArrayUtil.getArrayType(size.getClass()));
    }
}
