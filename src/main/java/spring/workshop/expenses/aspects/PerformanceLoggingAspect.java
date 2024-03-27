package spring.workshop.expenses.aspects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class PerformanceLoggingAspect {

	private static final Logger LOGGER = LogManager.getLogger(PerformanceLoggingAspect.class);

	// AOP expression for which methods shall be intercepted
	@Around("execution(* spring.workshop.expenses.services..*(..)))")
	public Object profileAllServiceMethods(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();

		// Get intercepted method details
		String className = methodSignature.getDeclaringType().getSimpleName();
		String methodName = methodSignature.getName();

		final StopWatch stopWatch = new StopWatch();

		// Measure method execution time
		stopWatch.start();
		Object result = proceedingJoinPoint.proceed();
		stopWatch.stop();

		// Log method execution time
		LOGGER.debug(
				"Execution time of Service: " + className + "." + methodName + " :: " + stopWatch.getTotalTimeMillis()
						+ " ms");

		return result;
	}

	@Around("execution(* spring.workshop.expenses.useCases..*(..)))")
	public Object profileAllUcMethods(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();

		// Get intercepted method details
		String className = methodSignature.getDeclaringType().getSimpleName();
		String methodName = methodSignature.getName();

		final StopWatch stopWatch = new StopWatch();

		// Measure method execution time
		stopWatch.start();
		Object result = proceedingJoinPoint.proceed();
		stopWatch.stop();

		// Log method execution time
		LOGGER.info(
				"Execution time of UC: " + className + "." + methodName + " :: " + stopWatch.getTotalTimeMillis()
						+ " ms");

		return result;
	}

}
