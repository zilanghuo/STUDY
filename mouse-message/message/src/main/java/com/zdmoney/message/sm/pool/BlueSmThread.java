package com.zdmoney.message.sm.pool;

import com.bst.msm.http.vo.ResultMsg;
import com.zdmoney.message.api.dto.sm.*;
import com.zdmoney.message.api.utils.DateUtils;
import com.zdmoney.message.sm.model.SmChannelConfig;
import com.zdmoney.message.sm.provider.http.BuleHttpSender;
import com.zdmoney.message.sm.vo.BlueError;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by Administrator on 2016/11/21.
 */
@Slf4j
public class BlueSmThread extends AbstractSmThread {

    public BlueSmThread(SendSmCommonReqDto sendSmReqDto, SmChannelConfig config, SendSmType sendSmType) {
        super(sendSmReqDto, config, sendSmType);
    }

    public SendSmCommonRspDto sendMsg() throws Exception {
        log.info("BlueSmThread send " + sendSmReqDto.toString() + " start at " + DateUtils.formatCurDate());
        String rs = BuleHttpSender.batchSendPost(config.getUrl(), config.getUserName(), config.getPassword(), sendSmReqDto.getMobileStr(),
                sendSmReqDto.getSendMsg(), true, null);
        log.info("BlueSmThread send " + sendSmReqDto.toString() + " BlueSmThread.resultMsg=" + rs);
        log.info("BlueSmThread send " + sendSmReqDto.toString() + " end at " + DateUtils.formatCurDate());
        return parseResult(null, rs);
    }

    /**
     * 短信提交响应分为两行，第一行为响应时间和状态，第二行为服务器给出提交msgid。
     * 无论发送的号码是多少，一个发送请求只返回一个msgid，如果响应的状态不是“0”，
     * 则没有msgid即第二行数据。（每行以换行符(0x0a,即\n)分割）
     *
     * @param resultMsg
     * @param rs
     * @return
     */
    @Override
    public SendSmCommonRspDto parseResult(ResultMsg resultMsg, String rs) {
        SendSmCommonRspDto sendSmRspDto = new SendSmCommonRspDto();
        sendSmRspDto.setSmSendStatus(SmSendStatus.FAIL);
        sendSmRspDto.setSmChannelType(SmChannelType.BLUE);
        if (StringUtils.isEmpty(rs)) {
            return sendSmRspDto;
        }

        String blueRes[] = StringUtils.split(rs, "/\n");
        String lineRes1 = blueRes[0];
        String splitLine1[] = StringUtils.split(lineRes1, ",");
        sendSmRspDto.setRespTime(splitLine1[0]);
        String respStatus = splitLine1[1];
        sendSmRspDto.setThirdText(rs);
        if (StringUtils.equals("0", respStatus)) {
            sendSmRspDto.setSmSendStatus(SmSendStatus.SUCCESS);
            sendSmRspDto.setMsgId(blueRes[1]);
        } else {
            sendSmRspDto.setSmSendStatus(SmSendStatus.FAIL);
            BlueError blueError = BlueError.getBlueErrorDesc(respStatus);
            log.info("BlueSmThread parseResult error {}", blueError);
            if (blueError != null) {
                sendSmRspDto.setThirdText("253 ERROR " + rs + blueError);
            }
        }
        return sendSmRspDto;
    }

}
