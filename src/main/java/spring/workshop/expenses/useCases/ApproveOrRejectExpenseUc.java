package spring.workshop.expenses.useCases;

import spring.workshop.expenses.entities.Expense;
import spring.workshop.expenses.enums.ExpenseStatus;

public interface ApproveOrRejectExpenseUc {

    public Expense approveOrRejectExpense(Long expenseId, Long superiorId, ExpenseStatus status, String note);

}
