package spring.workshop.expenses.useCases.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import spring.workshop.expenses.entities.Employee;
import spring.workshop.expenses.entities.Expense;
import spring.workshop.expenses.entities.User;
import spring.workshop.expenses.enums.ExpenseStatus;
import spring.workshop.expenses.exceptions.ForbiddenResourceException;
import spring.workshop.expenses.exceptions.ResourceNotFoundException;
import spring.workshop.expenses.services.EmployeeService;
import spring.workshop.expenses.services.ExpenseService;
import spring.workshop.expenses.useCases.EditExpenseUc;

@Component
public class EditExpenseUcImpl implements EditExpenseUc {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private EmployeeService employeeService;

    @Override
    @PreAuthorize("hasAuthority('EDIT_EXPENSES')")
    public Expense editExpense(User user, Expense expenseNew) {
        // Get Employee by User
        Employee employee = employeeService.getEmployeeByUser(user);

        // Get Expense by expense_id
        Expense expenseOld = expenseService.getExpenseById(expenseNew.getId());

        // Check if Expense.employee_id = employee_id
        if (!expenseOld.getEmployee().getId().equals(employee.getId()))
            throw new ResourceNotFoundException("No expense with given id found for this employee.");

        // Check if Expense.status in ('INITIAL')
        if (!expenseOld.getStatus().equals(ExpenseStatus.INITIAL))
            throw new ForbiddenResourceException(
                    "Expense status needs to be INITIAL (Current status: " + expenseNew.getStatus() + ").");

        return expenseService.updateExpense(expenseNew);
    }
}
