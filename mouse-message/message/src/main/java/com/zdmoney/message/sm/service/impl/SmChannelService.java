package com.zdmoney.message.sm.service.impl;

import com.zdmoney.message.api.common.dto.MessagePageResultDto;
import com.zdmoney.message.api.common.dto.MessageResultDto;
import com.zdmoney.message.api.dto.sm.*;
import com.zdmoney.message.api.utils.PropertiesUtils;
import com.zdmoney.message.common.base.BaseServiceImpl;
import com.zdmoney.message.sm.dao.SmChannelDAO;
import com.zdmoney.message.sm.model.SmChannel;
import com.zdmoney.message.sm.service.ISmChannelService;
import com.zdmoney.zdqd.dao.complexQuery.QueryParamBuilder;
import com.zdmoney.zdqd.dao.complexQuery.Sort;
import com.zdmoney.zdqd.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 短信通道 服务
 * Created by Administrator on 2016/11/7.
 */
@Service
@Slf4j
public class SmChannelService extends BaseServiceImpl<SmChannel> implements ISmChannelService {

    @Autowired
    private SmChannelDAO smChannelDAO;

    /**
     * 开关，如果有多个渠道，关闭其他渠道，只开一个渠道
     *
     * @param reqDto
     * @return
     */
    @Override
    @Transactional
    public MessageResultDto<ChannelRspDto> start(ChannelReqDto reqDto) {
        try {
            QueryParamBuilder builder = QueryParamBuilder.newBuilder();
            builder.addWithValueQueryParam("status", "=", SmChannelStatus.ON.getValue());
            List<SmChannel> smChannelList = query(builder.build(), null);
            for (SmChannel channel : smChannelList) {
                channel.setStatus(SmChannelStatus.OFF);
                channel.setModifyTime(new Date());
                if(reqDto.isResetMonthNum()) {
                    channel.setMonthNumber(0);    //月初发送数量从0开始
                }
            }
            update(smChannelList);

            SmChannel updateChannel = this.getSmChannelByNo(reqDto.getChannelNo());
            log.info("开启的通道是" + updateChannel.getNo() + "【" + updateChannel.getName() + "】");
            updateChannel.setStatus(SmChannelStatus.ON);
            updateChannel.setModifyTime(new Date());
            if(reqDto.isResetMonthNum()) {
                updateChannel.setMonthNumber(0);    //月初发送数量从0开始
            }
            update(updateChannel);
        } catch (Exception e) {
            log.error("开启通道{}异常：", reqDto, e);
            return MessageResultDto.FAIL("开启通道异常");
        }
        return MessageResultDto.SUCCESS();
    }

    @Override
    public MessageResultDto<ChannelRspDto> stop(ChannelReqDto reqDto) {
        try {
            QueryParamBuilder builder = QueryParamBuilder.newBuilder();
            builder.addWithValueQueryParam("status", "=", SmChannelStatus.ON.getValue());
            builder.addWithValueQueryParam("no", "!=", reqDto.getChannelNo());
            List<SmChannel> smChannelList = query(builder.build(), null);
            if (smChannelList.size() < 1) {
                return MessageResultDto.FAIL("没有其他已开启的渠道");
            }
            SmChannel updateChannel = this.getSmChannelByNo(reqDto.getChannelNo());
            updateChannel.setStatus(SmChannelStatus.OFF);
            updateChannel.setModifyTime(new Date());
            update(updateChannel);
            log.info("停用的通道是" + updateChannel.getNo() + "【" + updateChannel.getName() + "】");
        } catch (Exception e) {
            log.error("停用通道异常：", e);
        }
        return MessageResultDto.SUCCESS();
    }

    @Override
    public MessagePageResultDto<ChannelDto> searchChannel(ChannelSearchDto reqDto) {
        QueryParamBuilder builder = QueryParamBuilder.newBuilder();
        builder.addWithValueQueryParam("name",StringUtils.isNotEmpty(reqDto.getName())?"like":null,
                "%"+reqDto.getName()+"%")
                .addWithValueQueryParam("status", "=", reqDto.getStatus())
                .addWithValueQueryParam("modifyTime", ">=", reqDto.getModifyStartDate())
                .addWithValueQueryParam("modifyTime", "<=", reqDto.getModifyEndDate());
        List<Sort> sortList = new ArrayList();
        sortList.add(new Sort("modifyTime", Sort.Direction.DESC));
        int totalSize = countQuery(builder.build());
        List<SmChannel> channelList = query(builder.build(), sortList, reqDto.getStart(), reqDto.getPageSize());
        List<ChannelDto> channelDtos = PropertiesUtils.copyListNotExcludeRepeat(ChannelDto.class, channelList);
        return new MessagePageResultDto<>(channelDtos,totalSize, reqDto.getPageSize(), reqDto.getPageNo());
    }

    @Override
    public SmChannel getSmChannelByNo(String channelNo) {
        Validate.notEmpty(channelNo, "channelNo can not be empty");
        QueryParamBuilder builder = QueryParamBuilder.newBuilder();
        builder.addWithValueQueryParam("no", "=", channelNo);
        return query(builder.build(), null).get(0);
    }

    //查询开关开启
    @Override
    public MessageResultDto<ChannelDto> searchStartChannel() {
        ChannelSearchDto searchDto = new ChannelSearchDto();
        searchDto.setStatus(SmChannelStatus.ON.getValue());
        searchDto.setPageSize(10);
        MessagePageResultDto<ChannelDto> channelDto = searchChannel(searchDto);
        if (channelDto.getTotalSize() != 1) {
            return MessageResultDto.FAIL("可用渠道异常，请检查配置");
        }
        return new MessageResultDto(channelDto.getDataList().get(0));
    }
}
