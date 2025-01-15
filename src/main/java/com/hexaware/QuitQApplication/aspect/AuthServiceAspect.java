package com.hexaware.QuitQApplication.aspect;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuthServiceAspect {

	private static final Logger log = LogManager.getLogger(AuthServiceAspect.class);

	// Pointcut for login method
	@Pointcut("execution(* com.hexaware.QuitQApplication.serviceImpl.AuthServiceImpl.login(..))")
	public void loginPointcut() {
	}

	// Pointcut for registerCustomer method
	@Pointcut("execution(* com.hexaware.QuitQApplication.serviceImpl.AuthServiceImpl.registerCustomer(..))")
	public void registerCustomerPointcut() {
	}

	// Pointcut for registerSeller method
	@Pointcut("execution(* com.hexaware.QuitQApplication.serviceImpl.AuthServiceImpl.registerSeller(..))")
	public void registerSellerPointcut() {
	}

	// Log before method execution
	@Before("loginPointcut() || registerCustomerPointcut() || registerSellerPointcut()")
	public void logBeforeMethods(JoinPoint joinPoint) {
		log.info("Invoking method: {}", joinPoint.getSignature());
		Object[] args = joinPoint.getArgs();
		if (args != null && args.length > 0) {
			log.info("Arguments: {}", args[0]);
		} else {
			log.info("No arguments provided.");
		}
	}

	// Log after successful execution
	@AfterReturning(pointcut = "loginPointcut() || registerCustomerPointcut() || registerSellerPointcut()", returning = "result")
	public void logAfterReturningMethods(JoinPoint joinPoint, Object result) {
		log.info("Method executed successfully: {}", joinPoint.getSignature());
		log.info("Return value: {}", result);
	}

	// Log exceptions
	@AfterThrowing(pointcut = "loginPointcut() || registerCustomerPointcut() || registerSellerPointcut()", throwing = "ex")
	public void logAfterThrowingMethods(JoinPoint joinPoint, Throwable ex) {
		log.error("Exception in method: {}", joinPoint.getSignature());
		log.error("Exception message: {}", ex.getMessage());
		log.error("Stack trace: ", ex);
	}

	// Measure execution time
	@Around("loginPointcut() || registerCustomerPointcut() || registerSellerPointcut()")
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
