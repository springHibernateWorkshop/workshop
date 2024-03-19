package spring.workshop.expenses.useCases.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import spring.workshop.expenses.entities.Employee;
import spring.workshop.expenses.entities.Expense;
import spring.workshop.expenses.entities.Superior;
import spring.workshop.expenses.entities.User;
import spring.workshop.expenses.exceptions.ForbiddenResourceException;
import spring.workshop.expenses.security.Role;
import spring.workshop.expenses.services.EmployeeService;
import spring.workshop.expenses.services.ExpenseService;
import spring.workshop.expenses.services.SuperiorService;
import spring.workshop.expenses.services.impl.ValidateDateHelper;
import spring.workshop.expenses.useCases.ViewAllExpensesUc;

@Component
public class ViewAllExpensesUcImpl implements ViewAllExpensesUc {

    @Autowired
    ExpenseService expenseService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    SuperiorService superiorService;

    @Override
    public List<Expense> viewAllExpenses(User user, Integer year, Integer month, Long categoryId, Long shopId) {
        if (!ValidateDateHelper.validateDate(year, month)) {
            throw new IllegalArgumentException("Date is not valid.");
        }
        List<Expense> expenses = new ArrayList<>();
        if (user.getRole().getAuthority().equals(Role.ROLE_EMPLOYEE)) {
            Employee employee = employeeService.getEmployeeByUser(user);
            expenses = expenseService.getExpenseByEmployee(employee);
        } else if (user.getRole().getAuthority().equals(Role.ROLE_SUPERIOR)) {
            Superior superior = superiorService.getSuperiorByUser(user);
            List<Employee> employees = employeeService.getEmployeesBySuperior(superior);
            for (Employee employee : employees) {
                List<Expense> employeeExpenses = expenseService.getExpenseByEmployee(employee);
                expenses.addAll(employeeExpenses);
            }
        } else
            throw new ForbiddenResourceException("User role not allowed.");
        return expenseService.filter(expenses, year, month, categoryId, shopId);
    }
}
