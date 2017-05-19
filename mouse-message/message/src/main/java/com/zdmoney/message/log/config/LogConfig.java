package com.zdmoney.message.log.config;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by user on 2017/2/7.
 */
@Data
@NoArgsConstructor
public class LogConfig {
    private int logAccessRemoveRate;
    private String logAccessCtrlKey;
}
