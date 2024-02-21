package spring.workshop.expenses.useCases.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import spring.workshop.expenses.entities.Employee;
import spring.workshop.expenses.entities.Expense;
import spring.workshop.expenses.enums.ExpenseStatus;
import spring.workshop.expenses.services.EmployeeService;
import spring.workshop.expenses.services.ExpenseService;
import spring.workshop.expenses.useCases.CreateExpenseUc;

@Component
public class CreateExpenseUcImpl implements CreateExpenseUc {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ExpenseService expenseService;

    @Override
    public Expense createExpense(Long employeeId, Expense expense) {

        // Get Employee by employee_id
        Employee employee = employeeService.getEmployeeById(employeeId);

        // Set Employee and status for Expense and create Expense
        expense.setEmployee(employee);
        expense.setStatus(ExpenseStatus.INITIAL);
        Expense createdExpense = expenseService.addNewExpense(expense);

        // Return created Expense
        return createdExpense;

    }

}
