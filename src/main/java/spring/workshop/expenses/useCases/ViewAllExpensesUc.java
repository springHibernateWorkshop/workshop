package spring.workshop.expenses.useCases;

import java.util.List;

import spring.workshop.expenses.dto.ExpenseDTO;
import spring.workshop.expenses.entities.User;

public interface ViewAllExpensesUc {

    List<ExpenseDTO> viewAllExpenses(User user, Integer year, Integer month, Long categoryId, Long shopId);

}
