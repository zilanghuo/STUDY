package com.mouse.study.test.java8.List;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lwf
 * @date 2018/5/25
 * use:
 */
public class TestMain {

    public static void main(String[] args) {
        List<String> repayUser = new ArrayList();
        repayUser.add("repay1");
        repayUser.add("repay2");
        //还款记录的返回结果
        ResultList collectInfoPageResultDto = new ResultList();
        collectInfoPageResultDto.setExtendList(repayUser);

        // 还款计划的userId 结果集
        List<String> preRepay = collectInfoPageResultDto.getExtendList();

        //红包记录
        List<String> couponUser = new ArrayList();
        couponUser.add("coupon1");
        couponUser.add("coupon2");
        //还款记录的返回结果
        ResultList couponPageResultDto = new ResultList();
        couponPageResultDto.setExtendList(couponUser);

        collectInfoPageResultDto = couponPageResultDto;

        System.out.println(preRepay.get(0));
        System.out.println(collectInfoPageResultDto.getExtendList().get(0));
    }
}
