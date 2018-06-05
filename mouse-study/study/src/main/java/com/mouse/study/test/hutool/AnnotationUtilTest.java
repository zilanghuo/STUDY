package com.mouse.study.test.hutool;

import cn.hutool.core.annotation.AnnotationUtil;
import com.mouse.study.test.fuyou.req.DisableOrderReqData;

import java.lang.annotation.Annotation;

/**
 * @author lwf
 * @date 2018/6/4
 * use:
 */
public class AnnotationUtilTest {

    public static void main(String[] args) {
        Annotation[] annotations = AnnotationUtil.getAnnotations(DisableOrderReqData.class, true);
        for (Annotation annotation : annotations) {
            System.out.println(annotation.toString());
        }
        Annotation[] annotations1 = DisableOrderReqData.class.getAnnotations();
        for (Annotation annotation : annotations1){
            System.out.println(annotation.toString());
            System.out.println("--"+annotation.annotationType());
        }


    }

}
