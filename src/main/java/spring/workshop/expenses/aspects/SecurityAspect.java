package spring.workshop.expenses.aspects;

import java.security.Principal;
import java.util.Arrays;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import spring.workshop.expenses.entities.User;
import spring.workshop.expenses.enums.Right;
import spring.workshop.expenses.security.SecureMethod;
import spring.workshop.expenses.services.UserService;

@Aspect
@Component
public class SecurityAspect {

	private static final Logger LOG = LogManager.getLogger(PerformanceLoggingAspect.class);

	@Autowired
	private UserService userService;

	// AOP expression for which methods shall be intercepted
	@Around("@annotation(spring.workshop.expenses.security.SecureMethod)")
	public Object applySecurityCheck(ProceedingJoinPoint joinPoint) throws Throwable {
		String username = getUsername(joinPoint);
		User user = userService.getUserByUsername(username);

		if (username != null) {
			Set<Right> userRights = user.getRole().getRights();
			MethodSignature signature = (MethodSignature) joinPoint.getSignature();
			Right[] annotationRights = getAnnotationRights(signature);
			if (userRights.containsAll(Arrays.stream(annotationRights).toList())) {
				LOG.info(
						"User " + username + " is authorized to perform "
								+ signature.getDeclaringType().getSimpleName() + "." +
								signature.getName());
				Object result = joinPoint.proceed();
				return result;
			}
			throw new SecurityException("User " + username + " is not authorized to perform this action");
		}
		throw new SecurityException("Please log in to perform this action");
	}

	private Right[] getAnnotationRights(MethodSignature signature) {
		return signature.getMethod().getAnnotation(SecureMethod.class).Rights();
	}

	private String getUsername(ProceedingJoinPoint joinPoint) {
		return Arrays.stream(joinPoint.getArgs())
				.filter(arg -> arg instanceof Principal)
				.map(arg -> ((Principal) arg).getName())
				.findFirst()
				.orElse(null);
	}
}
