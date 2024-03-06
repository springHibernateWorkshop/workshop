package spring.workshop.expenses.useCases;

import spring.workshop.expenses.entities.Expense;

public interface SubmitExpenseUc {

    
    Expense submitExpense(Long expenseId, Long employeeId);
}
