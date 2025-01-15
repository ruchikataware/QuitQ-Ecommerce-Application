package com.hexaware.QuitQApplication.aspect;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
public class CategoryServiceAspect {

	private static final Logger log = LogManager.getLogger(CategoryServiceAspect.class);

	// Pointcut for getAllCategories method in CategoryServiceImpl
	@Pointcut("execution(* com.hexaware.QuitQApplication.serviceImpl.CategoryServiceImpl.getAllCategories(..))")
	public void getAllCategoriesPointcut() {
	}

	// Log before method execution
	@Before("getAllCategoriesPointcut()")
	public void logBeforeGetAllCategories(JoinPoint joinPoint) {
		log.info("Invoking method: {}", joinPoint.getSignature());
		log.info("Arguments: None (Method does not take parameters)");
	}

	// Log after successful execution
	@AfterReturning(pointcut = "getAllCategoriesPointcut()", returning = "result")
	public void logAfterReturningGetAllCategories(JoinPoint joinPoint, List<?> result) {
		log.info("Method executed successfully: {}", joinPoint.getSignature());
		log.info("Returned value: {}", result);
	}

	// Log exceptions
	@AfterThrowing(pointcut = "getAllCategoriesPointcut()", throwing = "ex")
	public void logAfterThrowingGetAllCategories(JoinPoint joinPoint, Throwable ex) {
		log.error("Exception in method: {}", joinPoint.getSignature());
		log.error("Exception message: {}", ex.getMessage());
		log.error("Stack trace: ", ex);
	}

	// Measure execution time
	@Around("getAllCategoriesPointcut()")
	public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
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
