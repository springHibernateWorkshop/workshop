package spring.workshop.expenses.services;

import java.time.LocalDate;
import java.util.List;

import spring.workshop.expenses.entities.Expense;

public interface ExpenseService {
    public List<Expense> getAllExpenses();

    public Expense getExpenseById(Long id);

    public Expense getExpenseByIdAndUsername(Long id, String username);

    public Expense updateExpense(Expense expense);

    public Boolean deleteExpense(Long id);

    public Expense addNewExpense(Expense expense);

    public List<Expense> findByDate(LocalDate date);

    public List<Expense> findByCategoryId(Long categoryId);

    public List<Expense> findByShopId(Long shopId);

    public List<Expense> findByEmployeeId(Long employeeId);

    public List<Expense> findByUserId(Long userId);

    public List<Expense> getExpensesByUsername(String namename);

}
