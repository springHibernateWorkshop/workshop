package spring.workshop.expenses.useCases;

import spring.workshop.expenses.entities.Expense;
import spring.workshop.expenses.entities.User;

public interface SubmitExpenseUc {

    Expense submitExpense(Long expenseId, User user);
}
