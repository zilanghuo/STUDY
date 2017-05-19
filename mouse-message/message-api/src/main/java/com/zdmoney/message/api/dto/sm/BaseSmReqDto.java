package com.zdmoney.message.api.dto.sm;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/21.
 */
@Data
public class BaseSmReqDto implements Serializable {

    private static final long serialVersionUID = -5017639681425909773L;

    protected SmSource smSource=SmSource.LCB;//来源，没有默认为LCB

    public SendSmReqDto buildSendSmReqDto(String mobile, String sendMsg) {
        SendSmReqDto sendSmReqDto = new SendSmReqDto();
        sendSmReqDto.setInstant(true);
        sendSmReqDto.setSendMsg(sendMsg);
        List<String> list = new ArrayList(1);
        list.add(mobile);
        sendSmReqDto.setMobiles(list);
        return sendSmReqDto;
    }

    public List<String> getMobiles(String mobile) {
        List<String> mobiles = new ArrayList(1);
        mobiles.add(mobile);
        return mobiles;
    }
}
