package org.my.springDemo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class CRMLoggingAspect {

    private final Logger logger = Logger.getLogger(getClass().getName());

    @Pointcut("execution(* org.my.springDemo.controller.*.*(..))")
    private void forControllerPackage() {
    }

    @Pointcut("execution(* org.my.springDemo.service.*.*(..))")
    private void forServicePackage() {
    }

    @Pointcut("execution(* org.my.springDemo.dao.*.*(..))")
    private void forDAOPackage() {
    }

    @Pointcut("forControllerPackage() || forServicePackage() || forDAOPackage()")
    private void forAppFlow() {
    }

    @Before("forAppFlow()")
    public void before(JoinPoint joinPoint) {
        logger.info("====> in @Before: calling method: " +
                joinPoint.getSignature().toShortString());
        Object[] args = joinPoint.getArgs();
        for (Object tmp : args) {
            logger.info("======> argument: " + tmp);
        }
    }

    @AfterReturning(
            pointcut = "forAppFlow()",
            returning = "result")
    public void after(JoinPoint joinPoint, Object result) {
        logger.info("====> in @After: calling method: " +
                joinPoint.getSignature().toShortString());
        logger.info("======> result: " + result);
    }
}
