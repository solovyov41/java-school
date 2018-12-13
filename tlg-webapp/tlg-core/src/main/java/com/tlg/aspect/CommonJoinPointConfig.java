package com.tlg.aspect;

import org.aspectj.lang.annotation.Pointcut;

public class CommonJoinPointConfig {

    @Pointcut("execution(* com.tlg.core.service.*.*(..))")
    public void businessLayerExecution() {
    }

    @Pointcut("execution(* com.tlg.core.utils.*.*(..))")
    public void utilsExecution() {
    }

    @Pointcut("execution(* com.tlg.core.dao.*.*(..))")
    public void dataLayerExecution() {
    }

    @Pointcut("businessLayerExecution() || dataLayerExecution() || utilsExecution()")
    public void allLayers() {
    }
}
