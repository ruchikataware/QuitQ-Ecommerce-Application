package com.hexaware.QuitQApplication.aspect;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UserServiceAspect {

	private static final Logger log = LogManager.getLogger(UserServiceAspect.class);

	// Pointcut for saveUserDetails method in UserServiceImpl
	@Pointcut("execution(* com.hexaware.QuitQApplication.serviceImpl.UserServiceImpl.saveUserDetails(..))")
	public void saveUserDetailsPointcut() {
	}

	// Log before method execution
	@Before("saveUserDetailsPointcut()")
	public void logBefore(JoinPoint joinPoint) {
		log.info("Invoking method: {}", joinPoint.getSignature());
		Object[] args = joinPoint.getArgs();
		if (args != null && args.length > 0) {
			log.info("Arguments: {}", args[0]);
		} else {
			log.info("No arguments provided.");
		}
	}

	// Log after successful execution
	@AfterReturning(pointcut = "saveUserDetailsPointcut()", returning = "result")
	public void logAfterReturning(JoinPoint joinPoint, Object result) {
		log.info("Method executed successfully: {}", joinPoint.getSignature());
		log.info("Returned value: {}", result);
	}

	// Log exceptions
	@AfterThrowing(pointcut = "saveUserDetailsPointcut()", throwing = "ex")
	public void logAfterThrowing(JoinPoint joinPoint, Throwable ex) {
		log.error("Exception in method: {}", joinPoint.getSignature());
		log.error("Exception message: {}", ex.getMessage());
		log.error("Stack trace: ", ex);
	}

	// Measure execution time
	@Around("saveUserDetailsPointcut()")
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
