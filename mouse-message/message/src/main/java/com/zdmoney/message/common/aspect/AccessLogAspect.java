package com.zdmoney.message.common.aspect;

import com.zdmoney.message.api.dto.log.LogDataReqDto;
import com.zdmoney.message.api.facade.ILogFacadeService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by gaojc on 2017/2/15.
 */
@Slf4j
@Aspect
@Component
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class AccessLogAspect {
    // 获取访问时的当前时间
    private static Date startTime;
    private static Date endTime;

    @Autowired
    private ILogFacadeService logFacadeService;

    /**
     * 切入值
     * * 仅对facade包进行统计,如果需要对多个,可以用"or"或者"||"链接多个包,例如
     *
     * @Pointcut("execution(public * package1..*.*(..)) or execution(public * package2..*.*(..))")
     */
    @Pointcut("execution(public * com.zdmoney.message.facade..*.*(..))")
    private static void recordTime() {
    }

    @Before(value = "recordTime()")
    public void before() {
        // 获取访问时的当前时间
        startTime = new Date();
    }

    @After(value = "recordTime()")
    public void after(JoinPoint jp) {
        // 获得方法名
        String methodName = jp.getSignature().getName();
        if (methodName.equals("accessLog"))
            return;
        // 获取访问时的当前时间
        endTime = new Date();
        // 获取类名
        String className = jp.getTarget().toString();
        String classMethod = getTargetName(jp);
        // 计算出调用方法返回的用时
        long costTime = endTime.getTime() - startTime.getTime();
        log.info("接口耗时统计：类：[{}],方法：[{}],耗时：[{}]毫秒", className, methodName, costTime);
        LogDataReqDto reqDto = new LogDataReqDto("message", classMethod, startTime, endTime, "方法执行用时:" + costTime + "毫秒");
        logFacadeService.accessLog(reqDto);
    }

    private String getTargetName(JoinPoint point) {
        return point.getSignature().getDeclaringTypeName() + "." + point.getSignature().getName();
    }

}

