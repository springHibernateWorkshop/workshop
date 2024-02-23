package spring.workshop.expenses.useCases;

import spring.workshop.expenses.entities.Expense;
import spring.workshop.expenses.entities.User;

public interface CreateExpenseUc {

    Expense createExpense(User user, Expense expense);

}
