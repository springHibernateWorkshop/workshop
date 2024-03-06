package spring.workshop.expenses.useCases.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import spring.workshop.expenses.entities.Employee;
import spring.workshop.expenses.entities.Expense;
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
    public Expense submitExpense(Long expenseId, Long employeeId) {
        Expense expense=expenseService.getExpenseById(expenseId);
        Employee employee= employeeService.getEmployeeById(expense.getEmployee().getId());
        if(expense.getEmployee().getId()!=employee.getId()){
            //TODO
        }
        if(expense.getStatus()!=ExpenseStatus.INITIAL){
            throw new ForbiddenResourceException("Expense should have 'Initial' status");
        }

        expense.setStatus(ExpenseStatus.PENDING);
        return expense;
    }

}