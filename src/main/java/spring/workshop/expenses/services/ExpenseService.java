package spring.workshop.expenses.services;

import java.util.List;

import spring.workshop.expenses.entities.Employee;
import spring.workshop.expenses.entities.Expense;

public interface ExpenseService {
    public List<Expense> getAllExpenses();

    public Expense getExpenseById(Long id);

    public Expense updateExpense(Expense expense);

    public void deleteExpense(Long id);

    public Expense createExpense(Expense expense);

    public List<Expense> getExpenseByEmployee(Employee employee);

    public List<Expense> filter(List<Expense> expenses, Integer year, Integer month, Long categoryId, Long shopId);

}
