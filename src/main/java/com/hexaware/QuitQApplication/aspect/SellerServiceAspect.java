package com.hexaware.QuitQApplication.aspect;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Aspect
@Component
public class SellerServiceAspect {

	private static final Logger log = LogManager.getLogger(SellerServiceAspect.class);

	// Pointcut for saveSellerDetails, getAllSellers, updateOrderStatus, and
	// getProductsBySellerAndCategory methods
	@Pointcut("execution(* com.hexaware.QuitQApplication.serviceImpl.SellerServiceImpl.saveSellerDetails(..))")
	public void saveSellerDetailsPointcut() {
	}

	@Pointcut("execution(* com.hexaware.QuitQApplication.serviceImpl.SellerServiceImpl.getAllSellers(..))")
	public void getAllSellersPointcut() {
	}

	@Pointcut("execution(* com.hexaware.QuitQApplication.serviceImpl.SellerServiceImpl.updateOrderStatus(..))")
	public void updateOrderStatusPointcut() {
	}

	@Pointcut("execution(* com.hexaware.QuitQApplication.serviceImpl.SellerServiceImpl.getProductsBySellerAndCategory(..))")
	public void getProductsBySellerAndCategoryPointcut() {
	}

	// Log before method execution
	@Before("saveSellerDetailsPointcut() || getAllSellersPointcut() || updateOrderStatusPointcut() || getProductsBySellerAndCategoryPointcut()")
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
	@AfterReturning(pointcut = "saveSellerDetailsPointcut() || getAllSellersPointcut() || updateOrderStatusPointcut() || getProductsBySellerAndCategoryPointcut()", returning = "result")
	public void logAfterReturning(JoinPoint joinPoint, Object result) {
		log.info("Method executed successfully: {}", joinPoint.getSignature());
		log.info("Returned value: {}", result);
	}

	// Log exceptions
	@AfterThrowing(pointcut = "saveSellerDetailsPointcut() || getAllSellersPointcut() || updateOrderStatusPointcut() || getProductsBySellerAndCategoryPointcut()", throwing = "ex")
	public void logAfterThrowing(JoinPoint joinPoint, Throwable ex) {
		log.error("Exception in method: {}", joinPoint.getSignature());
		log.error("Exception message: {}", ex.getMessage());
		log.error("Stack trace: ", ex);
	}

	// Measure execution time
	@Around("saveSellerDetailsPointcut() || getAllSellersPointcut() || updateOrderStatusPointcut() || getProductsBySellerAndCategoryPointcut()")
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
