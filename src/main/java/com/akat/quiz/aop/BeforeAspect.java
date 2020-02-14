package com.akat.quiz.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class BeforeAspect {

    /**
     * Target is annotation @Repository
     * each method that will be called
     * will be logged  @Before executing
     * by this aspect
     */

    @Pointcut("@target(org.springframework.stereotype.Repository") // target is @Repository
    public void repositoryMethods() {
    };

    @Before("repositoryMethods()")
    public void logMethodCall(JoinPoint jp) {
        log.info("Method {} was used in Repository", jp.getSignature().getName());
    };

}
