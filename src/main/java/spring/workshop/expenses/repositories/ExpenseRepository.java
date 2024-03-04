package spring.workshop.expenses.repositories;

import java.time.LocalDate;
import java.util.List;

import spring.workshop.expenses.entities.Expense;

public interface ExpenseRepository extends AbstractRepository<Expense> {

    List<Expense> findByDate(LocalDate date);

    List<Expense> findByCategoryId(Long categoryId);

    List<Expense> findByShopId(Long shopId);

    List<Expense> findByEmployeeId(Long employeeId);

}
