package com.zdmoney.message.api.dto.log;

import com.zdmoney.message.api.base.BaseDto;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Administrator on 2017/2/4.
 */
@NoArgsConstructor
@Data
public class LogDataStatDto extends BaseDto {
    private static final long serialVersionUID = -118809193469945622L;

    private String systemName;
    private String methodName;  //方法
    private Long avgSpendTime;  //平均耗时(毫秒)
    private int amount;         //记录数
    private String statDate;      //日期
}
