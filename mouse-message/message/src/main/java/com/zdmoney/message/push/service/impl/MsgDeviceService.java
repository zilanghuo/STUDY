package com.zdmoney.message.push.service.impl;

import com.zdmoney.message.api.common.dto.MessagePageResultDto;
import com.zdmoney.message.api.common.dto.MessageResultDto;
import com.zdmoney.message.api.dto.push.MsgDeviceCollectDto;
import com.zdmoney.message.api.dto.push.MsgDeviceDto;
import com.zdmoney.message.api.dto.push.MsgDeviceSearchDto;
import com.zdmoney.message.api.utils.PropertiesUtils;
import com.zdmoney.message.common.base.BaseServiceImpl;
import com.zdmoney.message.push.dao.MsgDeviceDAO;
import com.zdmoney.message.push.model.MsgDevice;
import com.zdmoney.message.push.service.IMsgDeviceService;
import com.zdmoney.zdqd.dao.complexQuery.QueryParamBuilder;
import com.zdmoney.zdqd.dao.complexQuery.Sort;
import com.zdmoney.zdqd.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/7/15.
 */
@Slf4j
@Service
public class MsgDeviceService extends BaseServiceImpl<MsgDevice> implements IMsgDeviceService {
    @Autowired
    private MsgDeviceDAO msgDeviceDAO;

    @Override
    public MsgDevice get(Long id) {
        if (null == id) {
            return null;
        }
        return msgDeviceDAO.getById(id);
    }

    @Override
    public MessagePageResultDto<MsgDeviceDto> search(MsgDeviceSearchDto msgDeviceSearchDto) {
        QueryParamBuilder builder = QueryParamBuilder.newBuilder();
        builder.addWithValueQueryParam("cellphoneNum", StringUtils.isNotEmpty(msgDeviceSearchDto.getCellphoneNum()) ? "like" : null,
                "%" + msgDeviceSearchDto.getCellphoneNum() + "%")
                .addWithValueQueryParam("deviceType", "=", msgDeviceSearchDto.getDeviceType())
                .addWithValueQueryParam("createTime", ">=", msgDeviceSearchDto.getStartDate())
                .addWithValueQueryParam("createTime", "<=", msgDeviceSearchDto.getEndDate());

        List<Sort> sortList =
                (CollectionUtils.isEmpty(msgDeviceSearchDto.getSortDtoList()))
                        ? new ArrayList<Sort>() : PropertiesUtils.copyList(Sort.class, msgDeviceSearchDto.getSortDtoList());
        sortList.add(new Sort("createTime", Sort.Direction.DESC));
        int totalSize = msgDeviceDAO.countQuery(builder.build());
        List<MsgDevice> list = msgDeviceDAO.query(builder.build(), sortList, msgDeviceSearchDto.getStart(), msgDeviceSearchDto.getPageSize());
        List<MsgDeviceDto> msgDeviceDtos = PropertiesUtils.copyListNotExcludeRepeat(MsgDeviceDto.class, list);

        for (int i = 0; i < msgDeviceDtos.size(); i++) {
            msgDeviceDtos.get(i).setDeviceType(list.get(i).getDeviceType().getMsg());
        }

        return new MessagePageResultDto<>(msgDeviceDtos, totalSize, msgDeviceSearchDto.getPageSize(), msgDeviceSearchDto.getPageNo());
    }

    @Override
    @Transactional
    public MessageResultDto collect(MsgDeviceCollectDto collectDto) {
        MsgDevice msgDevice = this.getByCellphoneNum(collectDto.getCellphoneNum());
        if (msgDevice == null) {
            this.insert(new MsgDevice(collectDto));
            MessageResultDto resultDto = new MessageResultDto<>();
            resultDto.setData(true);
            return MessageResultDto.SUCCESS();
        } else {
            if (!msgDevice.getDeviceId().equals(collectDto.getDeviceId())) {
                msgDevice.reset(collectDto);
            }
        }
        this.update(msgDevice);
        return MessageResultDto.SUCCESS();
    }

    @Override
    public MsgDevice getByCellphoneNum(String cellphone) {
        Validate.notEmpty(cellphone, "cellphoneNum can not be empty");
        return msgDeviceDAO.getOne(new MsgDevice(cellphone));
    }

}
