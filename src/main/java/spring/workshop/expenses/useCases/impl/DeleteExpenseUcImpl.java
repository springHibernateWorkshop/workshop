package spring.workshop.expenses.useCases.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import spring.workshop.expenses.entities.Employee;
import spring.workshop.expenses.entities.Expense;
import spring.workshop.expenses.enums.ExpenseStatus;
import spring.workshop.expenses.exceptions.ForbiddenResourceException;
import spring.workshop.expenses.exceptions.ResourceNotFoundException;
import spring.workshop.expenses.services.EmployeeService;
import spring.workshop.expenses.services.ExpenseService;
import spring.workshop.expenses.useCases.DeleteExpenseUc;

@Component
public class DeleteExpenseUcImpl implements DeleteExpenseUc {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    ExpenseService expenseService;

    @Override
    public void deleteExpense(Long employeeId, Long expenseId) {
        // Get Employee by employee_id
        Employee employee = employeeService.getEmployeeById(employeeId);

        // Get Expense by expense_id
        Expense expense = expenseService.getExpenseById(expenseId);

        // Check if Expense.employee_id = employee_id
        if (!expense.getEmployee().equals(employee))
            throw new ResourceNotFoundException("No expense with given id found for this employee.");

        // Check if Expense.status in ('INITIAL', 'REJECTED')
        if (!expense.getStatus().equals(ExpenseStatus.INITIAL) && !expense.getStatus().equals(ExpenseStatus.REJECTED))
            throw new ForbiddenResourceException(
                    "Expense status needs to be INITIAL or REJECTED (Current status: " + expense.getStatus() + ").");

        // Delete Expense with given expense_id
        expenseService.deleteExpense(expenseId);

    }

}
