package com.akat.quiz.aop;

import com.akat.quiz.services.interfaces.IMainService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class AfterCreatingAspect {

    /**
     * Target is annotation @Repository and
     * methods with create query
     * only create methods will be processed by
     * this aspect in case
     * it will be successfully finished.
     * Something like "finally"
     */

    IMainService service;

    @Autowired
    public AfterCreatingAspect(IMainService service) {
        this.service = service;
    }

    @Pointcut("@target(org.springframework.stereotype.Repository)")
    public void repositoryMethods() {}

    @Pointcut("execution(* *..create*(Long,..))")
    public void firstLongParamMethods() {}

    @Pointcut("repositoryMethods() && firstLongParamMethods()")
    public void entityCreationMethods() {}

    @AfterReturning(value = "entityCreationMethods()", returning = "entity")
    public void logMethodCall(JoinPoint jp, Object entity) throws Throwable {
        log.info("new entity created: {}", entity);
    }
}
