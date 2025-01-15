package com.hexaware.QuitQApplication.aspect;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
public class AdminServiceAspect {

    private static final Logger log = LogManager.getLogger(AdminServiceAspect.class);

    // Pointcut for addCategories method
    @Pointcut("execution(* com.hexaware.QuitQApplication.serviceImpl.AdminServiceImpl.addCategories(..))")
    public void addCategoriesPointcut() {}

    // Log before the method is executed
    @Before("addCategoriesPointcut()")
    public void logBeforeAddCategories(JoinPoint joinPoint) {
        log.info("Invoking method: {}", joinPoint.getSignature());
        Object[] args = joinPoint.getArgs();
        if (args != null && args.length > 0) {
            log.info("Method arguments: {}", args[0]); // Log the category list
        } else {
            log.info("Method arguments: None provided.");
        }
    }

    // Log after successful execution
    @AfterReturning(pointcut = "addCategoriesPointcut()", returning = "result")
    public void logAfterReturningAddCategories(JoinPoint joinPoint, List<?> result) {
        log.info("Method executed successfully: {}", joinPoint.getSignature());
        log.info("Returned value: {}", result);
    }

    // Log when an exception is thrown
    @AfterThrowing(pointcut = "addCategoriesPointcut()", throwing = "ex")
    public void logAfterThrowingAddCategories(JoinPoint joinPoint, Throwable ex) {
        log.error("Exception in method: {}", joinPoint.getSignature());
        log.error("Exception message: {}", ex.getMessage());
    }

    // Measure and log execution time
    @Around("addCategoriesPointcut()")
    public Object logExecutionTimeAddCategories(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            long endTime = System.currentTimeMillis();
            log.info("Execution time for method {}: {} ms", joinPoint.getSignature(), (endTime - startTime));
            return result;
        } catch (Throwable ex) {
            long endTime = System.currentTimeMillis();
            log.error("Execution time for method {}: {} ms (failed)", joinPoint.getSignature(), (endTime - startTime));
            throw ex;
        }
    }
}
