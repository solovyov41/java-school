package com.tlg.aspect.log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class WebAppLogger {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Around("com.tlg.aspect.CommonJoinPointConfig.allLayers()")
    public Object logWebServiceCall(ProceedingJoinPoint thisJoinPoint) throws Throwable {
        String methodName = thisJoinPoint.getSignature().getName();
        Object[] methodArgs = thisJoinPoint.getArgs();

        logger.debug("Call method {} with args {}", methodName, methodArgs);

        Object result = thisJoinPoint.proceed();

        logger.debug("Method {} returns {}", methodName, result);

        return result;
    }
}