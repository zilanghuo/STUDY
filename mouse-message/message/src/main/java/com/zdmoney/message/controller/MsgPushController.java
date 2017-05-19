package com.zdmoney.message.controller;

import com.zdmoney.message.api.dto.push.MsgBatchSearchDto;
import com.zdmoney.message.api.facade.IMsgBatchFacadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2016/8/11.
 */
@RequestMapping("/msgpsh")
public class MsgPushController {

    @Autowired
    private IMsgBatchFacadeService msgBatchFacadeService;

    @RequestMapping(value = "/downloadUpaySettleFile")
    @ResponseBody
    public String downloadUpaySettleFile(@RequestParam(required = true) String merId, @RequestParam(required = true) String settleDate, @RequestParam(required = false) String settleType) {
//        return Base64.getBASE64(cashierService.getDownloadSettleFile(merId, settleDate, settleType));

        MsgBatchSearchDto dto = new MsgBatchSearchDto();
        dto.setBatchNo(merId);
        msgBatchFacadeService.search(dto);
        return  "";
    }
}
