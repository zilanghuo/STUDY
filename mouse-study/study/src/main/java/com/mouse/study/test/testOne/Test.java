package com.mouse.study.test.testOne;

import com.alibaba.fastjson.JSON;
import com.mouse.study.api.utils.DateUtils;
import com.mouse.study.test.PayUtils;
import com.mouse.study.utils.JackJsonUtil;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author lwf
 * @date 2017/11/21
 * use:
 */
public class Test {

    @org.junit.Test
    public void test() {
        UUID uuid = UUID.randomUUID();
        System.out.println(uuid.toString());


    }


    @org.junit.Test
    public void testOne() throws Exception {
        PageResult<DepositLogResp> pageResult = new PageResult();
        pageResult.setPageNo(2);
        List<DepositLogResp> list = new ArrayList();
        DepositLogResp one = new DepositLogResp();
        one.setCreateTime(new Date());
        one.setInterfaceCode("xxxx");
        list.add(one);
        DepositLogResp two = new DepositLogResp();
        two.setCreateTime(new Date());
        two.setInterfaceCode("\"merchantPageUrl\":\"http://172.17.34.144:8081/laocaibao_manager\"");
        list.add(two);
        pageResult.setDataList(list);
        String x = JackJsonUtil.objToStr(pageResult);
        System.out.println(x);
        PageResult<DepositLogResp> returnObj = (PageResult<DepositLogResp>) JackJsonUtil.strToObj(x, PageResult.class);
        String text = JSON.toJSONString(returnObj);
        System.out.println(text);
        PageResultOne vo = JSON.parseObject(text, PageResultOne.class);
        List<DepositLogResp> ts = (List<DepositLogResp>) JackJsonUtil.strToList(vo.getDataList(), DepositLogResp.class);
        System.out.println(ts.get(0).getCreateTime());
    }

    public static void main(String[] args) throws Exception {
        String x = "{\"totalSize\":173,\"totalPage\":173,\"dataList\":[{\"clientType\":\"1\",\"callbackCount\":0,\"loginId\":null,\"thirdSerialNo\":\"20181012095405384\",\"merchantSerialNo\":\"20181012095405384\",\"merchantPageUrl\":\"www.baidu.com\",\"interfaceCode\":\"1001\",\"merchantReq\":\"{\\\"backNotifyUrl\\\":\\\"http://172.17.34.8:8080/laocaibao_4.8/zdpay/backNotify\\\",\\\"userId\\\":\\\"892250\\\",\\\"mobileNo\\\":\\\"15221299989\\\",\\\"userAttr\\\":\\\"3\\\",\\\"custName\\\":\\\"捞财宝\\\",\\\"idCardNo\\\":\\\"340822198306191898\\\",\\\"email\\\":null,\\\"authStatus\\\":null,\\\"bankCityId\\\":null,\\\"parentBankId\\\":null,\\\"bankNm\\\":null,\\\"autoLendTerm\\\":null,\\\"autoLendAmt\\\":null,\\\"autoRepayTerm\\\":null,\\\"autoRepayAmt\\\":null,\\\"autoCompenTerm\\\":null,\\\"autoCompenAmt\\\":null,\\\"autoFeeTerm\\\":null,\\\"autoFeeAmt\\\":null,\\\"artifName\\\":\\\"戴总\\\",\\\"openType\\\":\\\"1\\\",\\\"merchantCode\\\":\\\"820180727661\\\",\\\"clientType\\\":\\\"1\\\",\\\"pageUrl\\\":\\\"www.baidu.com\\\",\\\"signature\\\":\\\"be7c1b4fd0e095afe2e2d6109b92f68e\\\"}\",\"thirdResultCode\":\"5019\",\"thirdResultMsg\":\"手机号已被注册\",\"merchantNotifyUrl\":\"http://172.17.34.8:8080/laocaibao_4.8/zdpay/backNotify\",\"thirdAsynResp\":null,\"thirdSynResp\":\"{\\\"auto_fee_term\\\":\\\"\\\",\\\"auto_lend_amt\\\":\\\"\\\",\\\"auto_repay_amt\\\":\\\"\\\",\\\"city_id\\\":\\\"\\\",\\\"auto_lend_term\\\":\\\"\\\",\\\"artif_nm\\\":\\\"\\\",\\\"used_lend_amt\\\":\\\"\\\",\\\"auth_st\\\":\\\"\\\",\\\"resp_code\\\":\\\"5019\\\",\\\"mchnt_cd\\\":\\\"0002900F0352200\\\",\\\"cust_nm\\\":\\\"\\\",\\\"certif_id\\\":\\\"\\\",\\\"parent_bank_id\\\":\\\"\\\",\\\"signature\\\":\\\"jMosp7RtSQucWVxUCi17Ur6w7NvApl9UF6zBaJwP5SDcrVGnXWupk79PKyiwM+oKY2anhp+VCRdB1BRbqMoOrst1VL/M/OT+TiTBAbXzaWThx9GtGopEeo3mgv/spsckr8Swx+NPmoxWP9SFpfQ3YSXF+ROdZprhrdumpsHWNGc=\\\",\\\"mobile_no\\\":\\\"\\\",\\\"mchnt_txn_ssn\\\":\\\"20181012095405384\\\",\\\"resp_desc\\\":\\\"手机号已被注册\\\",\\\"login_id\\\":\\\"\\\",\\\"auto_repay_term\\\":\\\"\\\",\\\"auto_fee_amt\\\":\\\"\\\",\\\"certif_tp\\\":\\\"\\\",\\\"card_no\\\":\\\"\\\",\\\"reserved\\\":\\\"\\\",\\\"contract_st\\\":\\\"\\\",\\\"auto_compen_amt\\\":\\\"\\\",\\\"b_auto_lend_amt\\\":\\\"\\\",\\\"audit_st\\\":\\\"\\\",\\\"email\\\":\\\"\\\",\\\"usr_attr\\\":\\\"\\\",\\\"auto_compen_term\\\":\\\"\\\",\\\"bank_nm\\\":\\\"\\\"}\",\"merchantNotifyResp\":null,\"interfaceName\":\"个人开户\",\"merchantSynResp\":null,\"thirdReq\":\"{auto_repay_term=null, code=regLegalUser, signature=Chx8CNATmu4c0ybLaEgQRZVXmBZQK4hNptr7qp6GXmKqvSpK4GZYmDlmUFhZy6tm43xmVTvgFaFKvsHnT7H+qkb7Ar5r8onRrYgrkZrS+Ntvu3mJ4zeI22QpLmISDAD5LQVGcNgKTzri8tsxpqGzDbEnJh63C6nGz7i8KdqNJkk=, certif_tp=null, mobile_no=15221299989, bank_nm=null, mchnt_txn_ssn=20181012095405384, mchnt_cd=0002900F0352200, cust_nm=捞财宝, auto_compen_amt=null, auto_fee_amt=null, auto_fee_term=null, artif_nm=戴总, page_notify_url=http://172.17.34.3:8087/zdpay_cashier/sync/pageNotify, auto_lend_term=null, ver=1.00, auto_lend_amt=null, certif_id=340822198306191898, parent_bank_id=null, auth_st=null, auto_compen_term=null, client_tp=1, auto_repay_amt=null, back_notify_url=http://172.17.34.3:8087/zdpay_cashier/sync/commonNotify, usr_attr=3, city_id=null}\",\"merchantAsynResp\":null,\"createTime\":1539309245000,\"modifyTime\":1539309322000,\"id\":\"43c38291-9d7c-4617-85b5-4abf079bb75c\"}],\"pageNo\":1,\"code\":\"0000\",\"success\":true,\"msg\":null,\"data\":null}";


    }

    public static void testForCurrentOne() {
        Map<String, String> returnMap = new HashMap<>();
        returnMap.put("return_code", "SUCCESS");
        returnMap.put("return_msg", "OK");
        System.out.println(PayUtils.mapToXml(returnMap));


        int lineNum = 1024;
        int rowNum = 1024;
        Long arr[][] = new Long[lineNum][rowNum];
        Long start = System.currentTimeMillis();
        for (int i = 0; i < lineNum; i++) {
            for (int j = 0; j < rowNum; j++) {
                arr[i][j] = Long.getLong(i * 2 + j + "");
            }
        }
        Long end = System.currentTimeMillis();
        System.out.println("cost:" + (end - start));
    }

    public static void testForCurrentTwo() {
        int lineNum = 1024;
        int rowNum = 1024;
        Long arr[][] = new Long[lineNum][rowNum];
        Long start = System.currentTimeMillis();
        for (int i = 0; i < lineNum; i++) {
            for (int j = 0; j < rowNum; j++) {
                arr[j][i] = Long.getLong(i * 2 + j + "");
            }
        }
        Long end = System.currentTimeMillis();
        System.out.println("cost:" + (end - start));
    }


    @org.junit.Test
    public void testReg() {
        String reg = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$";
        Pattern pattern = Pattern.compile(reg);
        Matcher m = pattern.matcher("543gjhgXER");
        Matcher m2 = pattern.matcher("5gjXER");
        boolean matches = m.matches();
        System.out.println(matches);

    }

    @org.junit.Test
    public void testDate() {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.DAY_OF_MONTH, -1);
        now.set(Calendar.HOUR, 1);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.MILLISECOND, 1);
        Date startDate = now.getTime();
        System.out.println(DateUtils.format(startDate));

    }


}
