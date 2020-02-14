package com.akat.quiz.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Aspect
@Component
@Slf4j
public class AroundAspect {

    /**
     * Target is annotation @Repository
     * each executing time will be calculated by
     * by this aspect via @Around
     */

    @Pointcut("within(@org.springframework.stereotype.Repository *)")
    public void repositoryClassMethods() {};

    @Around("repositoryClassMethods()")
    public Object measureMethodExecutionTime(ProceedingJoinPoint pjp) throws Throwable {
        Long start = System.nanoTime();
        Object retVal = pjp.proceed();
        Long end = System.nanoTime();
        String methodName = pjp.getSignature().getName();
        log.info("Execution of {}} took {}} ms", methodName, TimeUnit.NANOSECONDS.toMillis(end - start));

        return retVal;
    }
}