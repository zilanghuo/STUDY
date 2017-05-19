package com.zdmoney.message.api.facade;

import com.zdmoney.message.api.common.dto.MessagePageResultDto;
import com.zdmoney.message.api.common.dto.MessageResultDto;
import com.zdmoney.message.api.common.validation.MessageValid;
import com.zdmoney.message.api.dto.sm.*;

/**
 * Created by Administrator on 2016/11/3.
 */

public interface ISmFacadeService {

    /**
     * 发送验证码
     *
     * @param reqDto
     * @return
     */
    MessageResultDto<SendVerifyCodeMsgRspDto> sendVerifyCodeMsg(@MessageValid SendVerifyCodeMsgReqDto reqDto);

    /**
     * 发送营销短信
     *
     * @param reqDto
     * @return
     */
    MessageResultDto<SendSmRspDto> sendMarketMsg(@MessageValid SendSmReqDto reqDto);

    /**
     * 发送通知短信
     * @param reqDto
     * @return
     */
    MessageResultDto<SendSmRspDto> sendNotifyMsg(@MessageValid SendSmNotifyReqDto reqDto);

    /**
     * 验证码验证接口
     *
     * @param reqDto
     * @return
     */
    MessageResultDto verificationCode(@MessageValid VerifyCodeReqDto reqDto);

    /**
     * 启动通道
     *
     * @return
     */
    MessageResultDto<ChannelRspDto> startChannel(@MessageValid ChannelReqDto reqDto);

    /**
     * 停用通道
     *
     * @return
     */
    MessageResultDto<ChannelRspDto> stopChannel(@MessageValid ChannelReqDto reqDto);

    /**
     * 查询验证码
     *
     * @param reqDto
     * @return
     */
    MessagePageResultDto<VerifyCodeRspDto> searchVerifyCode(SmSearchDto reqDto);

    /**
     * 查询营销短信
     *
     * @param reqDto
     * @return
     */
    MessagePageResultDto<ShortMsgDto> searchShortMsg(SmSearchDto reqDto);

    /**
     * 查询通知短信
     *
     * @param reqDto
     * @return
     */
    MessagePageResultDto<SmNotifyDto> searchSmNotify(SmSearchDto reqDto);


    /**
     * 查询渠道
     *
     * @param reqDto
     * @return
     */
    MessagePageResultDto<ChannelDto> searchChannel(ChannelSearchDto reqDto);

}
