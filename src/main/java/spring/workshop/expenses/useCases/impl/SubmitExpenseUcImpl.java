package spring.workshop.expenses.useCases.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import spring.workshop.expenses.entities.Employee;
import spring.workshop.expenses.entities.Expense;
import spring.workshop.expenses.entities.User;
import spring.workshop.expenses.enums.ExpenseStatus;
import spring.workshop.expenses.exceptions.ForbiddenResourceException;
import spring.workshop.expenses.services.EmployeeService;
import spring.workshop.expenses.services.ExpenseService;
import spring.workshop.expenses.useCases.SubmitExpenseUc;

@Component
public class SubmitExpenseUcImpl implements SubmitExpenseUc {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    ExpenseService expenseService;

    @Override
    public Expense submitExpense(Long expenseId, User user) {
        Expense expense = expenseService.getExpenseById(expenseId);
        Employee employee = employeeService.getEmployeeByUser(user);
        if (expense.getEmployee().getId() != employee.getId()) {
            // TODO throw new NotAuthorized(msg);
            throw new ForbiddenResourceException(
                    "Employee with ID = " + employee.getId() + " is not authorized to submit this expense");
        }
        if (expense.getStatus() != ExpenseStatus.INITIAL) {
            throw new ForbiddenResourceException("Expense should have 'Initial' status");
        }

        expense.setStatus(ExpenseStatus.PENDING);
        expenseService.updateExpense(expense);
        return expense;
    }

}