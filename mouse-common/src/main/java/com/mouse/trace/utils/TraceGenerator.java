package com.mouse.trace.utils;

import com.zdmoney.trace.common.Constant;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 * Created by lwf on 2017/5/11.
 */
public class TraceGenerator {

    private final static Logger logger = LoggerFactory.getLogger(TraceGenerator.class);

    public static String generatorId() {
        String traceId = MDC.get(Constant.TRACE_ID);
        return initTrackId(traceId);
    }

    public static String initTrackId(String traceId) {
        if (StringUtils.isEmpty(traceId)) {
            SnowFlake snowFlake = new SnowFlake(2, 3);
            traceId = snowFlake.nextId();
            logger.info("未携带参数值【traceId】，重新生成值【{}】",traceId);
        }
        MDC.put(Constant.TRACE_ID, traceId);
        return traceId;
    }

    public static void removeTraceId(){
        MDC.remove(Constant.TRACE_ID);
    }
}
