package spring.workshop.expenses.useCases;

import spring.workshop.expenses.entities.Expense;
import spring.workshop.expenses.entities.User;

public interface ViewOneExpenseUc {
    Expense viewOneExpense(User user, Long expenseId);
}
