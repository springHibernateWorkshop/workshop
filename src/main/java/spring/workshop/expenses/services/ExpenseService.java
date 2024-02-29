package spring.workshop.expenses.services;

import java.time.LocalDate;
import java.util.List;

import spring.workshop.expenses.entities.Expense;

public interface ExpenseService {
    public List<Expense> getAllExpenses();

    public Expense getExpenseById(Long id);

    public Expense updateExpense(Expense expense);

    public void deleteExpense(Long id);

    public Expense addNewExpense(Expense expense);

    public List<Expense> findByDate(LocalDate date);

    public List<Expense> findByCategoryId(Long categoryId);

    public List<Expense> findByShopId(Long shopId);

    public List<Expense> findByEmployeeId(Long employeeId);

    public List<Expense> filter(List<Expense> expenses, Integer year, Integer month, Long categoryId, Long shopId);
}
