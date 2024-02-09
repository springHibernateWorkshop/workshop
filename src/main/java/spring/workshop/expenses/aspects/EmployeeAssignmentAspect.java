package spring.workshop.expenses.aspects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import spring.workshop.expenses.entities.Employee;
import spring.workshop.expenses.entities.Superior;
import spring.workshop.expenses.exceptions.ForbiddenResourceException;

@Aspect
@Component
public class EmployeeAssignmentAspect {

    private static final Logger LOG = LogManager.getLogger(EmployeeAssignmentAspect.class);

    @Pointcut("execution(* spring.workshop.expenses.services.impl.EmployeeServiceImpl.updateEmployee(..)) && args(employee)")
    public void updateEmployeePointcut(Employee employee) {
    }

    @Before("updateEmployeePointcut(employee)")
    public void checkSuperiorActive(Employee employee) {

        LOG.info(
                "Aspect EmployeeAssignmentAspect is called.");

        Superior superior = employee.getSuperior();
        if (superior.getUser() == null) {
            throw new ForbiddenResourceException(
                    "Superior with id = " + superior.getId() + " is inactive (Superior has no User).");
        }

    }
}