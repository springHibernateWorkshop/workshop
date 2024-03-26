package spring.workshop.expenses.useCases;

import java.util.List;

import spring.workshop.expenses.entities.Expense;
import spring.workshop.expenses.entities.User;

public interface ViewAllExpensesUc {

    List<Expense> viewAllExpenses(User user, Integer year, Integer month, Long categoryId, Long shopId);

}
