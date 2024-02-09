package spring.workshop.expenses.aspects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import spring.workshop.expenses.entities.Superior;
import spring.workshop.expenses.exceptions.ForbiddenResourceException;

@Aspect
@Component
public class EmployeeAssignmentAspect {

    private static final Logger LOGGER = LogManager.getLogger(EmployeeAssignmentAspect.class);

    @Pointcut("execution(* spring.workshop.expenses.entities.Employee.setSuperior(..)) && args(superior)")
    public void assignEmployeePointcut(Superior superior) {
    }

    @Before("assignEmployeePointcut(superior)")
    public void checkSuperiorActive(Superior superior) {

        LOGGER.info(
                "Aspect EmployeeAssignmentAspect is called.");

        if (superior.getUser() == null) {
            throw new ForbiddenResourceException("User for Superior with id = " +
                    superior.getId() + " does not exist.");
        }

        LOGGER.info(
                "Aspect EmployeeAssignmentAspect is finished.");

    }
}