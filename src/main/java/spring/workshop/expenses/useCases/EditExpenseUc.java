package spring.workshop.expenses.useCases;

import spring.workshop.expenses.dto.ExpenseDTO;
import spring.workshop.expenses.entities.Expense;
import spring.workshop.expenses.entities.User;

public interface EditExpenseUc {
    ExpenseDTO editExpense(User user, Expense expense);
}
