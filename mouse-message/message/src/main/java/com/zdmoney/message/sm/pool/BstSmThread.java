package com.zdmoney.message.sm.pool;

import com.bst.msm.http.service.MsmSend;
import com.bst.msm.http.vo.MsmSingle;
import com.bst.msm.http.vo.ResultMsg;
import com.zdmoney.message.api.dto.sm.*;
import com.zdmoney.message.api.utils.DateUtils;
import com.zdmoney.message.api.utils.JsonUtils;
import com.zdmoney.message.sm.model.SmChannelConfig;
import com.zdmoney.message.sm.vo.BstError;
import com.zdmoney.message.utils.SmConfig;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * Created by Administrator on 2016/11/21.
 */
@Slf4j
public class BstSmThread extends AbstractSmThread {

    public BstSmThread(SendSmCommonReqDto sendSmReqDto, SmChannelConfig config, SendSmType sendSmType) {
        super(sendSmReqDto, config, sendSmType);
    }

    @Override
    public SendSmCommonRspDto sendMsg() throws Exception{
        log.info("BstSmThread send " + sendSmReqDto.toString() +" start at "+DateUtils.formatCurDate());
        MsmSingle msmSingle = new MsmSingle();
        msmSingle.setUsername(config.getUserName());
        msmSingle.setUserpwd(config.getPassword());
        msmSingle.setMobiles(sendSmReqDto.getMobiles());
        SendSmType sendSmType = super.sendSmType;
        msmSingle.setMsgtext("【证大捞财宝】" + sendSmReqDto.getSendMsg());
        if(SendSmType.MARKET == sendSmType) {
            msmSingle.setSrcphone(SmConfig.getSmUtils().getBstMarketScrphone());
        } else {
            msmSingle.setSrcphone(SmConfig.getSmUtils().getBstMsgScrphone());
        }
        msmSingle.setSendurl(config.getUrl());
        MsmSend msmSend = new MsmSend();
        ResultMsg resultMsg = msmSend.msmSingleSend(msmSingle);
        log.info("BstSmThread.resultMsg {} bst result is {}", sendSmReqDto.toString() , "code="+resultMsg.getCode()+",msgId="+resultMsg.getMsgid());
        log.info("BstSmThread send " + sendSmReqDto.toString() + " end at "+DateUtils.formatCurDate());
        return parseResult(resultMsg, null);
    }

    @Override
    public SendSmCommonRspDto parseResult(ResultMsg resultMsg, String rs) {
        SendSmCommonRspDto sendSmRspDto = new SendSmCommonRspDto();
        sendSmRspDto.setSmSendStatus(SmSendStatus.FAIL);
        sendSmRspDto.setSmChannelType(SmChannelType.BST);
        if(resultMsg == null) {
            return sendSmRspDto;
        }
        sendSmRspDto.setMsgId(resultMsg.getMsgid());
        String resultMsgJson = JsonUtils.toJson(resultMsg);
        sendSmRspDto.setThirdText(resultMsgJson);
        if(resultMsg.getCode() == 0 && resultMsg.isResult()) {
            sendSmRspDto.setSmSendStatus(SmSendStatus.SUCCESS);
        } else  {
            int code = resultMsg.getCode();
            BstError bstError = BstError.getBstErrorDesc(code + "");
            log.info("BstSmThread parseResult error {}",bstError);
            if(bstError != null) {
                sendSmRspDto.setThirdText("BST ERROR "+ resultMsgJson + bstError);
            }
        }
        sendSmRspDto.setRespStatus(resultMsg.getCode() + "");
        sendSmRspDto.setRespTime(DateUtils.format(new Date()));
        return sendSmRspDto;
    }


}
