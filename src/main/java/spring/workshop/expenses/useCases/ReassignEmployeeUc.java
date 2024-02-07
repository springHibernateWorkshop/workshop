package spring.workshop.expenses.useCases;

import spring.workshop.expenses.entities.Employee;

public interface ReassignEmployeeUc {

    Employee reassignEmployee(Long employeeId, Long superiorId);

}
