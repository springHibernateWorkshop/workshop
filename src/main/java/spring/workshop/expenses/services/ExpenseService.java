package spring.workshop.expenses.services;

import java.util.List;

import spring.workshop.expenses.entities.Expense;

public interface ExpenseService {
    public List<Expense> getAllExpenses();
    public Expense getExpensesById(Long id);
    public Expense updateExpenses(Expense expenses, Long id);
    public Expense deleteExpenses(Long id);
    public Expense addNewExpenses(Expense expenses);   
}
