package com.zdmoney.message.sm.service;

import com.zdmoney.message.api.common.dto.MessagePageResultDto;
import com.zdmoney.message.api.common.dto.MessageResultDto;
import com.zdmoney.message.api.dto.sm.*;
import com.zdmoney.message.sm.model.SmVerifyCode;
import com.zdmoney.message.sm.model.StatRecord;
import com.zdmoney.zdqd.service.EntityService;

import java.util.List;

/**
 * Created by gaojc on 2016/11/10.
 */
public interface ISmVerifyCodeService extends EntityService<SmVerifyCode> {

    /**
     * 验证码查询接口
     * @param searchDto
     * @return
     */
    MessagePageResultDto<VerifyCodeRspDto> searchVerifyCode(SmSearchDto searchDto);

    /**
     * 验证码验证
     *
     * @param reqDto
     * @return
     */
    MessageResultDto verificationCode(VerifyCodeReqDto reqDto);

    /**
     * 查询1分钟内的该手机号码次数
     * @param mobile
     * @return
     */
    MessageResultDto conditionVerifyCode(String mobile);

    /**
     * 获取最近的手机验证码
     * @param mobile
     * @param verifyCode
     * @return
     */
    SmVerifyCode getRecentSmVerifyCode(String mobile, String verifyCode, SmSendStatus smSendStatus);

    MessageResultDto updateVerifyCode(SendVerifyCodeMsgReqDto sendDto, SendSmCommonRspDto sendSmCommonRspDto);

    MessageResultDto saveVerifyCodeMsgReq(SendVerifyCodeMsgReqDto sendDto);

    List<StatRecord> statSmVerifyCode(String startTime, String endTime);

    void batchDelete(List<SmVerifyCode> smVerifyCodeList);
}
