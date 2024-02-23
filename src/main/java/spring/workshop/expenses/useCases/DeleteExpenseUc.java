package spring.workshop.expenses.useCases;

import spring.workshop.expenses.entities.User;

public interface DeleteExpenseUc {

    void deleteExpense(User user, Long expenseId);

}
