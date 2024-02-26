package spring.workshop.expenses.repositories;

import java.util.Optional;

import spring.workshop.expenses.entities.Employee;
import spring.workshop.expenses.entities.User;

public interface EmployeeRepository extends AbstractRepository<Employee> {

    Optional<Employee> findByUser(User user);

}
