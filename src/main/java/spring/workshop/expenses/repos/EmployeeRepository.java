package spring.workshop.expenses.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import spring.workshop.expenses.entities.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByName(String name);

    Optional<Employee> findById(Integer id);

}
