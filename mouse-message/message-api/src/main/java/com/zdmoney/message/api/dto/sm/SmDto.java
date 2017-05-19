package com.zdmoney.message.api.dto.sm;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/11.
 * 一条短信
 */
@Data
@NoArgsConstructor
public class SmDto implements Serializable {

    private static final long serialVersionUID = -1942185353704712031L;
    private String msg;
    private String mobile;

    public SmDto(String msg, String mobile) {
        this.msg = msg;
        this.mobile = mobile;
    }
}
