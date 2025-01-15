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
public class OrderServiceAspect {

	private static final Logger log = LogManager.getLogger(OrderServiceAspect.class);

	// Pointcut for placeOrder and calculateTotalAmount methods in OrderServiceImpl
	@Pointcut("execution(* com.hexaware.QuitQApplication.serviceImpl.OrderServiceImpl.placeOrder(..))")
	public void placeOrderPointcut() {
	}

	@Pointcut("execution(* com.hexaware.QuitQApplication.serviceImpl.OrderServiceImpl.calculateTotalAmount(..))")
	public void calculateTotalAmountPointcut() {
	}

	// Log before method execution
	@Before("placeOrderPointcut() || calculateTotalAmountPointcut()")
	public void logBefore(JoinPoint joinPoint) {
		log.info("Invoking method: {}", joinPoint.getSignature());
		Object[] args = joinPoint.getArgs();
		if (args != null && args.length > 0) {
			log.info("Arguments: {}", args);
		} else {
			log.info("No arguments provided.");
		}
	}

	// Log after successful execution
	@AfterReturning(pointcut = "placeOrderPointcut() || calculateTotalAmountPointcut()", returning = "result")
	public void logAfterReturning(JoinPoint joinPoint, Object result) {
		log.info("Method executed successfully: {}", joinPoint.getSignature());
		log.info("Returned value: {}", result);
	}

	// Log exceptions
	@AfterThrowing(pointcut = "placeOrderPointcut() || calculateTotalAmountPointcut()", throwing = "ex")
	public void logAfterThrowing(JoinPoint joinPoint, Throwable ex) {
		log.error("Exception in method: {}", joinPoint.getSignature());
		log.error("Exception message: {}", ex.getMessage());
		log.error("Stack trace: ", ex);
	}

	// Measure execution time
	@Around("placeOrderPointcut() || calculateTotalAmountPointcut()")
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
