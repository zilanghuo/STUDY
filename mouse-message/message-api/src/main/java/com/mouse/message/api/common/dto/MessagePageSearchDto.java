package com.mouse.message.api.common.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xinqigu on 2015/8/25.
 */
public class MessagePageSearchDto implements Serializable {

    public static final int DEFAULT_PAGE_SIZE = 20;
    private static final long serialVersionUID = 4652475837182803934L;

    /** 页号 */
    protected int pageNo = 1;

    /** 每页数量 */
    protected int pageSize;

    /** 排序属性集合 */
    protected List<MessageSortDto> sortDtoList = new ArrayList<MessageSortDto>();

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<MessageSortDto> getSortDtoList() {
        return sortDtoList;
    }

    public void setSortDtoList(List<MessageSortDto> sortDtoList) {
        this.sortDtoList = sortDtoList;
    }

    public void addSorts(MessageSortDto... sortDtos) {
        for (MessageSortDto sortDto : sortDtos) {
            this.sortDtoList.add(sortDto);
        }
    }

    public int getStart() {
        if(this.pageSize == 0){
            this.pageSize = DEFAULT_PAGE_SIZE;
        }
        return (this.pageNo-1) * this.pageSize;
    }

}
