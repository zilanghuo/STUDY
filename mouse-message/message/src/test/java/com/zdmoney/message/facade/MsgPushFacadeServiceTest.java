package com.zdmoney.message.facade;

import com.google.common.collect.Maps;
import com.zdmoney.message.InitH2TestEnvironment;
import com.zdmoney.message.api.common.dto.MessageResultDto;
import com.zdmoney.message.api.dto.push.MsgDeviceCollectDto;
import com.zdmoney.message.api.dto.push.MsgPushDto;
import com.zdmoney.message.api.dto.push.MsgWechatPushDto;
import com.zdmoney.message.api.facade.IMsgPushFacadeService;
import com.zdmoney.message.api.utils.DateUtils;
import com.zdmoney.message.push.model.MsgDevice;
import com.zdmoney.message.push.model.PushResult;
import com.zdmoney.message.push.service.IMsgDeviceService;
import com.zdmoney.message.push.service.IPushMsgProvider;
import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by gaojc on 2016/8/1.
 */
@Slf4j
public class MsgPushFacadeServiceTest extends InitH2TestEnvironment {

    @Autowired
    private IMsgPushFacadeService msgPushFacadeService;

    @Autowired
    private IMsgDeviceService msgDeviceService;

    @Autowired
    private IPushMsgProvider pushMsgProvider;

    @Test
    @Ignore
    public void testPush() throws Exception {
        MsgPushDto pushDto = new MsgPushDto();
        pushDto.setSerialNo("201608021786487515320122");
        pushDto.setPushTime(new Date());

        pushDto.setTitle("加息券x2");
        pushDto.setContent("可以拿来投资了");
        pushDto.setIsInstant(true);
        pushDto.setIsAllpush(false);

        String jsonData = "{\"type\":\"5\",\"url\":\"https://www.laocaibao.com\",\"title\":\"通知\",\"content\":\"领奖\"}";
        pushDto.setData(jsonData);
        List<String> phoneList = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            phoneList.add("18121100222");
        }
        pushDto.setPhones(phoneList);

        MessageResultDto resultDto = msgPushFacadeService.push(pushDto);
        log.info("retCode=" + resultDto.getCode() + ",retMsg=" + resultDto.getMsg());
        assertTrue(resultDto.isSuccess());
        Thread.sleep(30000 * 1000);
    }

    @Test
    @Ignore
    public void testCollect() throws Exception {

        MsgDeviceCollectDto collectDto = new MsgDeviceCollectDto();
        collectDto.setDeviceType("1");
        collectDto.setCellphoneNum("15221299967");
        collectDto.setDeviceId("68d2680156e4812788d9b5079961f4d0");
        MessageResultDto resultDto = msgPushFacadeService.collect(collectDto);
        assertTrue(resultDto.isSuccess());
        MsgDevice msgDevice = msgDeviceService.getByCellphoneNum("18321432852");

        log.info(msgDevice.toString());
        assertEquals("1", msgDevice.getDeviceType().getValue());

        collectDto = new MsgDeviceCollectDto();
        collectDto.setDeviceType("1");
        collectDto.setCellphoneNum("18321432852");
        collectDto.setDeviceId("7338a79cac461c7b52b9dc670eb02c1d");
        resultDto = msgPushFacadeService.collect(collectDto);
        assertTrue(resultDto.isSuccess());

        msgDevice = msgDeviceService.getByCellphoneNum("18321432852");
        assertEquals("1", msgDevice.getDeviceType().getValue());
        assertEquals("6951c73487fyre8f44273c0002", msgDevice.getDeviceId());
    }

    @Test
    @Ignore
    public void testCollectClientId() throws Exception {
        MsgDeviceCollectDto collectDto = new MsgDeviceCollectDto();
        List<MsgDevice> devices = new ArrayList<>();

        boolean flag;
        for (int i = 0; i < 200000; i++) {
            /*if (i % 2 == 0)
                flag = true;
            else
                flag = false;*/

            if (i % 5 == 0) {
                collectDto.setDeviceId("7338a79cac461c7b52b9dc670eb02c1d");
                collectDto.setDeviceType("2");
            } else if (i % 5 == 1) {
                collectDto.setDeviceId("f5db1b35f2ab02237ce63717e84b5e1b");
                collectDto.setDeviceType("1");
            } else if (i % 5 == 2) {
                collectDto.setDeviceId("fd282cae31d0211baa588668b1b64a79");
                collectDto.setDeviceType("2");
            } else if (i % 5 == 3) {
                //for my phone use
                collectDto.setDeviceId("dc223e3ef7d9885a0cedc77b8d9f23ad");
                collectDto.setDeviceType("1");
            } else if (i % 5 == 4) {
                collectDto.setDeviceId("68d2680156e4812788d9b5079961f4d0");
                collectDto.setDeviceType("1");
            } else {
                log.debug("i % 5= " + (i % 5));
            }
//            collectDto.setDeviceType(flag ? "1" : "2");
            collectDto.setCellphoneNum("189000" + String.valueOf(i));
//            collectDto.setDeviceId(RandomStringUtils.randomAlphanumeric(20));

            MsgDevice device = new MsgDevice(collectDto);
            devices.add(device);
        }
        msgDeviceService.insert(devices);
    }

    @Test
    public void testStatPushResult() {
        String thirdTaskId = "OSL-0826_c20Ei3Jqih9Ut0Yxu2Om15";
        PushResult result = pushMsgProvider.statPushResult(thirdTaskId);

        log.info(result.toString());
    }

    @Test
    @Ignore
    public void testSendWeMsg() {
        String openId = "obBL-vhfaHzIzNg03_liS19L8O0U";
        String tmlShortId = "OPENTM207128600";
        Map<String, String> params = Maps.newHashMap();
        params.put("first", "恭喜您，提现成功！");
        params.put("keyword1", "6222****0449");
        params.put("keyword2", "10000元");
        params.put("keyword3", DateUtils.format(new Date(), "yyyy年MM月dd日 HH:mm"));

        params.put("remark", "1-2个工作日内到账，请注意查收,具体到账时间以银行卡发卡行为准");

        MsgWechatPushDto wechatPushDto = new MsgWechatPushDto(openId, tmlShortId, "", params);
        MessageResultDto resultDto = msgPushFacadeService.sendWechatMsg(wechatPushDto);

        assertTrue(resultDto.isSuccess());

    }

}