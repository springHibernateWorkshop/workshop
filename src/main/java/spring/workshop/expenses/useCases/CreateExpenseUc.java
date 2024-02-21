package spring.workshop.expenses.useCases;

import spring.workshop.expenses.entities.Expense;

public interface CreateExpenseUc {

    Expense createExpense(Long employeeId, Expense expense);

}
