package spring.workshop.expenses.useCases;

import spring.workshop.expenses.entities.Expense;
import spring.workshop.expenses.entities.User;

public interface EditExpenseUc {
    Expense editExpense(User user, Expense expense);
}
