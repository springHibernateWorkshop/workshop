package spring.workshop.expenses.services;

import java.util.List;

import spring.workshop.expenses.entities.Expenses;

public interface ExpensesService {
    public List<Expenses> getAllExpenses();
    public Expenses getExpensesById(Long id);
    public Expenses updateExpenses(Expenses expenses, Long id);
    public Expenses deleteExpenses(Long id);
    public Expenses addNewExpenses(Expenses expenses);   
}
