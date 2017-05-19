package com.zdmoney.message.common.base;

import com.zdmoney.message.api.common.dto.MessagePageSearchDto;
import com.zdmoney.message.api.utils.PropertiesUtils;
import com.zdmoney.zdqd.dao.complexQuery.CustomQueryParam;
import com.zdmoney.zdqd.dao.complexQuery.Sort;
import com.zdmoney.zdqd.model.Entity;
import com.zdmoney.zdqd.service.EntityServiceImpl;

import java.util.List;

/**
 * Created by rui on 15/8/27.
 */
public class BaseServiceImpl<T extends Entity> extends EntityServiceImpl<T> {

    public List<T> get(MessagePageSearchDto searchDto, T findParams, Integer start, Integer limit) {
        List<Sort> sortList = PropertiesUtils.copyList(Sort.class, searchDto.getSortDtoList());
        return super.get(findParams, sortList, start, limit);
    }

    public List<T> find(MessagePageSearchDto searchDto, T findParams, Integer start, Integer limit) {
        List<Sort> sortList = PropertiesUtils.copyList(Sort.class, searchDto.getSortDtoList());
        return super.find(findParams, sortList, start, limit);
    }

    public List<T> query(MessagePageSearchDto searchDto, List<CustomQueryParam> customQueryParams) {
        List<Sort> sortList = PropertiesUtils.copyList(Sort.class, searchDto.getSortDtoList());
        return super.query(customQueryParams, sortList);
    }

    public List<T> query(MessagePageSearchDto searchDto, List<CustomQueryParam> customQueryParams, Integer start, Integer limit) {
        List<Sort> sortList = PropertiesUtils.copyList(Sort.class, searchDto.getSortDtoList());
        return super.query(customQueryParams, sortList, start, limit);
    }

}
