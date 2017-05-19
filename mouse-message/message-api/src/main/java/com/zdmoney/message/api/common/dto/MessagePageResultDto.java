package com.zdmoney.message.api.common.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xinqigu on 2015/8/25.
 */
@Data
public class MessagePageResultDto<T> extends MessageResultDto<T> {

    /**
     * 总记录数
     */
    protected Integer totalSize = 0;

    /**
     * 总页数
     */
    protected Integer totalPage = 0;

    /**
     * 当前页数
     */
    protected Integer pageNo = 1;

    /**
     * 列表
     */
    protected List<T> dataList = new ArrayList<>();

    public MessagePageResultDto() {
    }

    public MessagePageResultDto(String msg, boolean isSuccess) {
        super(msg, isSuccess);
    }

    public MessagePageResultDto(List<T> data, Integer totalSize, Integer pageSize) {
        this.code = SUCCESS_CODE;
        this.totalPage = (totalSize + pageSize - 1) / pageSize;
        this.dataList = data;
        this.totalSize = totalSize;
    }

    public MessagePageResultDto(List<T> data, Integer totalSize, Integer pageSize, int pageNo) {
        this(data, totalSize, pageSize);
        this.pageNo = pageNo;
    }

    public MessagePageResultDto(String code, String msg, List<T> data) {
        super(code, msg);
        this.dataList = data;
    }

    public static MessagePageResultDto SUCCESS() {
        MessagePageResultDto resultDto = new MessagePageResultDto<>();
        resultDto.setCode(SUCCESS_CODE);
        return resultDto;
    }

    public static MessagePageResultDto FAIL(String message) {
        return new MessagePageResultDto<>(message, false);
    }
}
