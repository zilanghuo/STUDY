package com.zdmoney.message.push.service.impl;

import com.zdmoney.message.api.common.dto.MessagePageResultDto;
import com.zdmoney.message.api.common.dto.MessageResultDto;
import com.zdmoney.message.api.dto.push.MsgPhoneDto;
import com.zdmoney.message.api.dto.push.MsgPhoneSearchDto;
import com.zdmoney.message.api.utils.PropertiesUtils;
import com.zdmoney.message.common.base.BaseServiceImpl;
import com.zdmoney.message.push.dao.MsgPhoneDAO;
import com.zdmoney.message.push.model.MsgPhone;
import com.zdmoney.message.push.service.IMsgPhoneService;
import com.zdmoney.zdqd.dao.complexQuery.QueryParamBuilder;
import com.zdmoney.zdqd.dao.complexQuery.Sort;
import com.zdmoney.zdqd.util.StringUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lwf on 2016/7/20.
 */
@Service
public class MsgPhoneService extends BaseServiceImpl<MsgPhone> implements IMsgPhoneService {

    @Autowired
    private MsgPhoneDAO msgPhoneDAO;

    @Override
    public void batchInsert(List<MsgPhone> msgPhones) {
        msgPhoneDAO.batchInsert(msgPhones);
    }

    @Override
    public MessagePageResultDto<MsgPhoneDto> search(MsgPhoneSearchDto phoneSearchDto) {
        QueryParamBuilder builder = QueryParamBuilder.newBuilder();
        builder.addWithValueQueryParam("cellphoneNum", StringUtils.isNotEmpty(phoneSearchDto.getCellphoneNum()) ? "like" : null,
                "%" + phoneSearchDto.getCellphoneNum() + "%")
                .addWithValueQueryParam("batchNo", StringUtils.isNotEmpty(phoneSearchDto.getBatchNo()) ? "like" : null,
                        "%" + phoneSearchDto.getBatchNo() + "%")
                .addWithValueQueryParam("createTime", ">=", phoneSearchDto.getCreateStartDate())
                .addWithValueQueryParam("createTime", "<=", phoneSearchDto.getCreateEndDate())
                .addWithValueQueryParam("pushTime", ">=", phoneSearchDto.getPushStartDate())
                .addWithValueQueryParam("pushTime", "<=", phoneSearchDto.getPushEndDate());
        List<Sort> sortList =
                (CollectionUtils.isEmpty(phoneSearchDto.getSortDtoList()))
                        ? new ArrayList<Sort>() : PropertiesUtils.copyList(Sort.class, phoneSearchDto.getSortDtoList());
        sortList.add(new Sort("pushTime", Sort.Direction.DESC));
        int totalSize = msgPhoneDAO.countQuery(builder.build());
        List<MsgPhone> list = msgPhoneDAO.query(builder.build(), sortList, phoneSearchDto.getStart(), phoneSearchDto.getPageSize());
        List<MsgPhoneDto> MsgPhoneDtos = PropertiesUtils.copyListNotExcludeRepeat(MsgPhoneDto.class, list);
        for (int i = 0; i < MsgPhoneDtos.size(); i++) {
            MsgPhoneDtos.get(i).setDeviceType(list.get(i).getDeviceType().getMsg());
            MsgPhoneDtos.get(i).setStatus(list.get(i).getStatus().getMsg());
        }
        return new MessagePageResultDto<>(MsgPhoneDtos, totalSize, phoneSearchDto.getPageSize(), phoneSearchDto.getPageNo());
    }

    @Override
    public MessageResultDto addPhone(MsgPhoneDto msgPhoneDto) {
        MsgPhone msgPhone = new MsgPhone(msgPhoneDto);
        this.insert(msgPhone);
        return MessageResultDto.SUCCESS();
    }

    @Override
    public MessageResultDto updatePhone(MsgPhone msgPhoneTask) {
        msgPhoneTask.setModifyTime(new Date());
        msgPhoneDAO.update(msgPhoneTask);
        return new MessageResultDto();
    }

    @Override
    public MessageResultDto insertPhone(MsgPhone msgPhone) {
        this.insert(msgPhone);
        return MessageResultDto.SUCCESS();
    }

    @Override
    public List<MsgPhone> getByTaskNo(String taskNo) {
        MsgPhone phone = new MsgPhone();
        phone.setTaskNo(taskNo);
        return msgPhoneDAO.get(phone);
    }

    @Override
    public MsgPhone getOne(MsgPhone msgPhone) {
        return msgPhoneDAO.getOne(msgPhone);
    }

}
