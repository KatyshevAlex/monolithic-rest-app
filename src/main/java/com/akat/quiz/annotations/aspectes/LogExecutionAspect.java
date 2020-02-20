package com.akat.quiz.annotations.aspectes;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LogExecutionAspect {

    @Around("@annotation(com.akat.quiz.annotations.LogExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        Long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        Long executionTime = System.currentTimeMillis() - start;

        log.info("Method {} was executed in {} ms", joinPoint.getSignature().getName(), executionTime);
        return proceed;
    }

}
