package spring.workshop.expenses.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;

import spring.workshop.expenses.entities.Employee;
import spring.workshop.expenses.entities.Expense;

public interface ExpenseRepository extends AbstractRepository<Expense> {

    List<Expense> findByDate(LocalDate date);

    List<Expense> findByCategoryId(Long categoryId);

    List<Expense> findByShopId(Long shopId);

    List<Expense> findByEmployeeId(Long employeeId);

    @Query("SELECT e FROM Expense e JOIN e.employee emp JOIN emp.user u WHERE e.id = :expenseId AND u.username = :username")
    Optional<Expense> findByIdAndUsernameWithEmployee(Long expenseId, String username);

    @Query("SELECT e FROM Expense e JOIN e.employee emp JOIN emp.user u WHERE  u.id = :userId")
    List<Expense> findByUserId(Long userId);

    List<Expense> findByEmployee(Employee employee);

}
