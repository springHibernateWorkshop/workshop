package spring.workshop.expenses.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import spring.workshop.expenses.entities.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByDate(LocalDate date);

    List<Expense> findByCategoryId(Long categoryId);

    List<Expense> findByShopId(Long shopId);

    List<Expense> findByEmployeeId(Long employeeId);

}
