package com.zdmoney.message.api.common.validation;

import com.zdmoney.message.api.common.dto.MessagePageResultDto;
import com.zdmoney.message.api.common.dto.MessageResultDto;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * Created by rui on 16/3/3.
 */
@Aspect
@Component
@Slf4j
public class MessageValidAspect {


    @Around("execution(* *(@MessageValid (*)))")
    public Object invoke(ProceedingJoinPoint proceedingJoinPoint)
            throws Throwable {
        Signature signature = proceedingJoinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        Class<?> returnType = method.getReturnType();
        String simpleName = returnType.getSimpleName();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Object[] args = proceedingJoinPoint.getArgs();
        for (Object o : args) {
            Set<ConstraintViolation<Object>> resultSet = validator.validate(o);
            for (ConstraintViolation<Object> violation : resultSet) {
                String message = violation.getMessage();
                if (simpleName.equals("MessageResultDto")) {
                    return MessageResultDto.FAIL(message);
                }
                return MessagePageResultDto.FAIL(message);
            }
        }
        return proceedingJoinPoint.proceed();
    }
}
