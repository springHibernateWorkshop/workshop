package spring.workshop.expenses.services;

import java.util.List;

import spring.workshop.expenses.entities.Expense;

public interface SuperiorService {
    public Expense viewExpense(Long userId, Long expenseId);

    public List<Expense> viewExpenses(Long userId, Long employeeId, String year, String month, Long categoryId, Long shopId);

    public List<Expense> viewAllExpenses(Long userId, String year, String month, Long categoryId, Long shopId);

    public Object viewReport(Long userId, String year, String month, Long categoryId, Long shopId, String format);    

    public void approveExpense(Long userId, Long expenseId);

}
