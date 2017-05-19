package com.zdmoney.message.facade;

import com.zdmoney.message.InitDBTestEnvironment;
import com.zdmoney.message.api.common.dto.MessageResultDto;
import com.zdmoney.message.api.dto.sm.SendSmNotifyReqDto;
import com.zdmoney.message.api.dto.sm.SendSmReqDto;
import com.zdmoney.message.api.dto.sm.SendVerifyCodeMsgReqDto;
import com.zdmoney.message.api.dto.sm.VerifyCodeReqDto;
import com.zdmoney.message.api.facade.ISmFacadeService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaojc on 2016/11/9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "classpath:appContext.xml")
//@Transactional
@Slf4j
public class SmFacadeServiceTest extends InitDBTestEnvironment {

    @Autowired
    private ISmFacadeService smFacadeService;

    @Test
    public void testSendVerifyCode() throws Exception {
        SendVerifyCodeMsgReqDto reqDto = new SendVerifyCodeMsgReqDto("11110000000", "1346", "你的验证码是1346,有效期10分钟");
        MessageResultDto rspDto = smFacadeService.sendVerifyCodeMsg(reqDto);

        if (rspDto.isSuccess()) {
            log.info("code=" + rspDto.getCode());
            log.info("msg=" + rspDto.getMsg());
        } else {
            log.info("错误消息=" + rspDto.getMsg());
        }
    }

    @Test
    public void testSendNotifyMsg() throws Exception {
        SendSmNotifyReqDto reqDto = new SendSmNotifyReqDto("22222211111", "这是通知短信测试-" + System.currentTimeMillis());
        MessageResultDto rspDto = smFacadeService.sendNotifyMsg(reqDto);

        if (rspDto.isSuccess()) {
            log.info("code=" + rspDto.getCode());
            log.info("msg=" + rspDto.getMsg());
        } else {
            log.info("错误消息=" + rspDto.getMsg());
        }
        testSendVerifyCode();
        Thread.sleep(2000 * 1000L);
    }


    @Test
    public void testSendMarketMsg() throws Exception {
        SendSmReqDto reqDto = new SendSmReqDto();
        reqDto.setBatchNo("010012000420170210012");
        reqDto.setSendMsg("营销短信" + System.currentTimeMillis()+"-退订回复TD");
        reqDto.setInstant(true);
//        reqDto.setSendTime(DateUtils.parse("2016-11-14 10:00:00"));
        List<String> mobiles = new ArrayList<>();
//        mobiles.add("18121139438");
//        mobiles.add("18650309362");

        for (int i = 0; i < 1; i++) {
            mobiles.add("22222222222" + i);
        }
        reqDto.setMobiles(mobiles);

        MessageResultDto rspDto = smFacadeService.sendMarketMsg(reqDto);
        if (rspDto.isSuccess()) {
            log.info("code=" + rspDto.getCode());
            log.info("msg=" + rspDto.getMsg());
        } else {
            log.info("错误消息=" + rspDto.getMsg());
        }

        Thread.sleep(2000 * 1000L);
    }


    @Test
    public void testVerificationCode() throws Exception {
        VerifyCodeReqDto reqDto = new VerifyCodeReqDto("18102110988", "2566");
        MessageResultDto rspDto = smFacadeService.verificationCode(reqDto);
        if (rspDto.isSuccess()) {
            log.info("code=" + rspDto.getCode());
            log.info("msg=" + rspDto.getMsg());
        } else {
            log.info("错误消息=" + rspDto.getMsg());
        }
    }

}