package com.cevier.shop.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class ApiMonitor {

    @Around("execution(* com.cevier.shop.controller..*.*(..))")
    public Object ApiTimer(ProceedingJoinPoint joint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object o = joint.proceed();
        long endTime = System.currentTimeMillis();
        long time = endTime - startTime;
        log.info("接口监控：{}.{} 耗时{}ms", joint.getTarget().getClass(), joint.getSignature().getName(), time);
        return o;
    }
}
