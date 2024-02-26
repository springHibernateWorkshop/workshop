package spring.workshop.expenses.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import spring.workshop.expenses.entities.Employee;
import spring.workshop.expenses.entities.User;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @NonNull
    Optional<Employee> findById(@NonNull Long id);

    Optional<Employee> findByUser(User user);

}
