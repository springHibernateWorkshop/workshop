package spring.workshop.expenses.services;

import java.util.List;

import spring.workshop.expenses.entities.Employee;

public interface EmployeeService {

    Employee addEmployee(Employee employee);

    void deleteEmployee(Long id);

    Employee updateEmployee(Employee employee);

    List<Employee> getAllEmployees();

    Employee getEmployeeById(Long id);

}
