package com.mouse.study.test;

import com.mouse.study.utils.JackJsonUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lwf
 * @date 2018/7/23
 * use:
 */
public class TestJson {

    /**
     * str 转 bean，bean拥有的属性，str里面只可少，不能多
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception{

        Simple simple = new Simple(12,"小红");
        String str = JackJsonUtil.objToStr(simple);
        Complex complex = (Complex) JackJsonUtil.strToObj(str,Complex.class);
        System.out.println(complex.getName());
    }

}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Simple{

    private Integer age;

    private String name;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Complex{

    private String age;

    private String name;

    private String color;

}
