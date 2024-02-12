package spring.workshop.expenses.aspects;

import java.security.Principal;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import spring.workshop.expenses.security.Right;
import spring.workshop.expenses.security.SecureMethod;

@Aspect
@Component
public class SecurityAspect {

	private static final Logger LOG = LogManager.getLogger(PerformanceLoggingAspect.class);

	// AOP expression for which methods shall be intercepted
	@Around("@annotation(spring.workshop.expenses.security.SecureMethod)")
	public Object applySecurityCheck(ProceedingJoinPoint joinPoint) throws Throwable {
		String username = Arrays.stream(joinPoint.getArgs())
				.filter(arg -> arg instanceof Principal)
				.map(arg -> ((Principal) arg).getName())
				.findFirst()
				.orElse(null);

		if (Arrays.stream(joinPoint.getArgs()).anyMatch(arg -> arg instanceof Principal)) {
			Right[] userRights = { Right.VIEW_EXPENSES }; // TODO get user rights from DB
			MethodSignature signature = (MethodSignature) joinPoint.getSignature();
			SecureMethod annotation = signature.getMethod()
					.getAnnotation(SecureMethod.class);
			System.out.println(annotation);
			Right[] annotationRights = annotation.Rights();
			if (Arrays.stream(annotationRights)
					.allMatch(userRight -> Arrays.stream(userRights).anyMatch(userRight::equals))) {
				LOG.info(
						"User " + username + " is authorized to perform "
								+ signature.getDeclaringType().getSimpleName() + "." +
								signature.getName());
				Object result = joinPoint.proceed();
				return result;
			}
		}
		throw new SecurityException("User " + username + " is not authorized to perform this action");
	}
}
