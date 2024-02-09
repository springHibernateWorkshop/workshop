package spring.workshop.expenses.useCases.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spring.workshop.expenses.entities.Employee;
import spring.workshop.expenses.entities.Superior;
import spring.workshop.expenses.exceptions.ForbiddenResourceException;
import spring.workshop.expenses.services.EmployeeService;
import spring.workshop.expenses.services.SuperiorService;
import spring.workshop.expenses.useCases.ReassignEmployeeUc;

@Component
public class ReassignEmployeeUcImpl implements ReassignEmployeeUc {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private SuperiorService superiorService;

    @Override
    public Employee reassignEmployee(Long employeeId, Long superiorId) {

        // Get Superior by superior_id
        Superior superior = superiorService.getSuperiorById(superiorId);

        // Check if Superior.user_id != Null
        // if (superior.getUser() == null)
        //     throw new ForbiddenResourceException("User for Superior with id = " +
        //             superiorId + "does not exist.");

        // Get Employee by id
        Employee updatedEmployee = employeeService.getEmployeeById(employeeId);

        // Update Superior of Employee and save updated Employee
        updatedEmployee.setSuperior(superior);
        Employee savedEmployee = employeeService.updateEmployee(updatedEmployee);

        // Return updated and saved Employee
        return savedEmployee;

    }

}
