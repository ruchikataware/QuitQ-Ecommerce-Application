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
public class ProductServiceAspect {

	private static final Logger log = LogManager.getLogger(ProductServiceAspect.class);

	// Pointcut for getAllProducts, browseProducts, addProduct, and updateProduct
	// methods
	@Pointcut("execution(* com.hexaware.QuitQApplication.serviceImpl.ProductServiceImpl.getAllProducts(..))")
	public void getAllProductsPointcut() {
	}

	@Pointcut("execution(* com.hexaware.QuitQApplication.serviceImpl.ProductServiceImpl.browseProducts(..))")
	public void browseProductsPointcut() {
	}

	@Pointcut("execution(* com.hexaware.QuitQApplication.serviceImpl.ProductServiceImpl.addProduct(..))")
	public void addProductPointcut() {
	}

	@Pointcut("execution(* com.hexaware.QuitQApplication.serviceImpl.ProductServiceImpl.updateProduct(..))")
	public void updateProductPointcut() {
	}

	// Log before method execution
	@Before("getAllProductsPointcut() || browseProductsPointcut() || addProductPointcut() || updateProductPointcut()")
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
	@AfterReturning(pointcut = "getAllProductsPointcut() || browseProductsPointcut() || addProductPointcut() || updateProductPointcut()", returning = "result")
	public void logAfterReturning(JoinPoint joinPoint, Object result) {
		log.info("Method executed successfully: {}", joinPoint.getSignature());
		log.info("Returned value: {}", result);
	}

	// Log exceptions
	@AfterThrowing(pointcut = "getAllProductsPointcut() || browseProductsPointcut() || addProductPointcut() || updateProductPointcut()", throwing = "ex")
	public void logAfterThrowing(JoinPoint joinPoint, Throwable ex) {
		log.error("Exception in method: {}", joinPoint.getSignature());
		log.error("Exception message: {}", ex.getMessage());
		log.error("Stack trace: ", ex);
	}

	// Measure execution time
	@Around("getAllProductsPointcut() || browseProductsPointcut() || addProductPointcut() || updateProductPointcut()")
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
