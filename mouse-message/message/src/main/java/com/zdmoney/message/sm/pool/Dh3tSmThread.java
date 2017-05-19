package com.zdmoney.message.sm.pool;

import com.bst.msm.http.vo.ResultMsg;
import com.ctc.smscloud.json.JSONHttpClient;
import com.zdmoney.message.api.dto.sm.*;
import com.zdmoney.message.api.utils.DateUtils;
import com.zdmoney.message.api.utils.JsonUtils;
import com.zdmoney.message.sm.model.SmChannelConfig;
import com.zdmoney.message.sm.vo.Dh3tError;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by gaojc on 2017/1/6.
 */
@Slf4j
public class Dh3tSmThread extends AbstractSmThread {
    public Dh3tSmThread(SendSmCommonReqDto sendSmReqDto, SmChannelConfig config, SendSmType sendSmType) {
        super(sendSmReqDto, config, sendSmType);
    }

    @Override
    protected SendSmCommonRspDto sendMsg() throws Exception {
        log.info("Dh3tSmThread send " + sendSmReqDto.toString() + " start at " + DateUtils.formatCurDate());
        JSONHttpClient jsonHttpClient = JSONHttpClient.getInstance(config.getUrl());
        String rs = jsonHttpClient.sendSms(config.getUserName(), config.getPassword(),
                sendSmReqDto.getMobileStr(), sendSmReqDto.getSendMsg(), "【证大捞财宝】",null);
        log.info("Dh3tSmThread send " + sendSmReqDto.toString() + " Dh3tSmThread.resultMsg=" + rs);
        log.info("Dh3tSmThread send " + sendSmReqDto.toString() + " end at " + DateUtils.formatCurDate());
        return parseResult(null, rs);
    }

    @Override
    public SendSmCommonRspDto parseResult(ResultMsg resultMsg, String rs) {
        HashMap<String, String> jObj = null;

        SendSmCommonRspDto sendSmRspDto = new SendSmCommonRspDto();
        sendSmRspDto.setSmSendStatus(SmSendStatus.FAIL);
        sendSmRspDto.setSmChannelType(SmChannelType.DH3T);

        try {
            jObj = JsonUtils.fromJson(rs, HashMap.class);
        } catch (Exception e) {
            log.error("Dh3tSmThread parseResult JsonParse error {}", e);
        }
        if (jObj == null || jObj.isEmpty()) {
            return sendSmRspDto;
        }
        String msgid = jObj.get("msgid");
        String code = jObj.get("result");

        sendSmRspDto.setMsgId(msgid);
        sendSmRspDto.setThirdText(rs);
        if (!StringUtils.equals(code, "0")) {
            Dh3tError dh3tError = Dh3tError.getDh3tErrorDesc(code);
            log.info("Dh3tSmThread parseResult error {}", dh3tError);
            if (dh3tError != null) {
                sendSmRspDto.setThirdText("Dh3t ERROR " + rs + dh3tError);
            }
            return sendSmRspDto;
        }
        sendSmRspDto.setSmSendStatus(SmSendStatus.SUCCESS);
        sendSmRspDto.setRespStatus(code);
        sendSmRspDto.setRespTime(DateUtils.format(new Date()));
        return sendSmRspDto;
    }
}
