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
public class PaymentServiceAspect {

	private static final Logger log = LogManager.getLogger(PaymentServiceAspect.class);

	// Pointcut for processPayment, getPaymentsByCustomerId, and getAllPayments
	// methods
	@Pointcut("execution(* com.hexaware.QuitQApplication.serviceImpl.PaymentServiceImpl.processPayment(..))")
	public void processPaymentPointcut() {
	}

	@Pointcut("execution(* com.hexaware.QuitQApplication.serviceImpl.PaymentServiceImpl.getPaymentsByCustomerId(..))")
	public void getPaymentsByCustomerIdPointcut() {
	}

	@Pointcut("execution(* com.hexaware.QuitQApplication.serviceImpl.PaymentServiceImpl.getAllPayments(..))")
	public void getAllPaymentsPointcut() {
	}

	// Log before method execution
	@Before("processPaymentPointcut() || getPaymentsByCustomerIdPointcut() || getAllPaymentsPointcut()")
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
	@AfterReturning(pointcut = "processPaymentPointcut() || getPaymentsByCustomerIdPointcut() || getAllPaymentsPointcut()", returning = "result")
	public void logAfterReturning(JoinPoint joinPoint, Object result) {
		log.info("Method executed successfully: {}", joinPoint.getSignature());
		log.info("Returned value: {}", result);
	}

	// Log exceptions
	@AfterThrowing(pointcut = "processPaymentPointcut() || getPaymentsByCustomerIdPointcut() || getAllPaymentsPointcut()", throwing = "ex")
	public void logAfterThrowing(JoinPoint joinPoint, Throwable ex) {
		log.error("Exception in method: {}", joinPoint.getSignature());
		log.error("Exception message: {}", ex.getMessage());
		log.error("Stack trace: ", ex);
	}

	// Measure execution time
	@Around("processPaymentPointcut() || getPaymentsByCustomerIdPointcut() || getAllPaymentsPointcut()")
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
