package spring.workshop.expenses.useCases.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import spring.workshop.expenses.entities.Employee;
import spring.workshop.expenses.entities.Expense;
import spring.workshop.expenses.entities.Superior;
import spring.workshop.expenses.entities.User;
import spring.workshop.expenses.exceptions.ResourceNotFoundException;
import spring.workshop.expenses.security.Role;
import spring.workshop.expenses.services.EmployeeService;
import spring.workshop.expenses.services.ExpenseService;
import spring.workshop.expenses.services.SuperiorService;
import spring.workshop.expenses.useCases.ViewOneExpenseUc;

@Component
public class ViewOneExpenseUcImpl implements ViewOneExpenseUc {

    @Autowired
    SuperiorService superiorService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    ExpenseService expenseService;

    @Override
    public Expense viewOneExpense(User user, Long expenseId) {

        // Get Expense by expense_id
        Expense expense = expenseService.getExpenseById(expenseId);

        Employee expenseEmployee = expense.getEmployee();

        if (user.getRole().getAuthority().equals(Role.ROLE_EMPLOYEE)) {
            // Get Employee by User
            Employee userEmployee = employeeService.getEmployeeByUser(user);

            // Check if Expense.employee_id = employee_id
            if (!expenseEmployee.getId().equals(userEmployee.getId()))
            throw new ResourceNotFoundException("No expense with given id found for this employee.");

        } else if (user.getRole().getAuthority().equals(Role.ROLE_SUPERIOR)) {
            Superior expenseSuperior = expenseEmployee.getSuperior();
            Superior userSuperior = superiorService.getSuperiorByUser(user);
            
            if (!expenseSuperior.getId().equals(userSuperior.getId()))
            throw new ResourceNotFoundException("No expense with given id found for this superior."); 

        } else {
            throw new IllegalArgumentException(
                    "It's not allowed to view an expense with Role other than EMPLOYEE or SUPERIOR");
        }

        return expense;
    }
    
}
