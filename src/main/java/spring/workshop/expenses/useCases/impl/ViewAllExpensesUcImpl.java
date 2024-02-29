package spring.workshop.expenses.useCases.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import spring.workshop.expenses.entities.Employee;
import spring.workshop.expenses.entities.Expense;
import spring.workshop.expenses.entities.Superior;
import spring.workshop.expenses.entities.User;
import spring.workshop.expenses.services.EmployeeService;
import spring.workshop.expenses.services.ExpenseService;
import spring.workshop.expenses.services.SuperiorService;
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
        List<Expense> expenses = null;
        if (user.getRole() == "EMPLOYEE") { // TODO
            Employee employee = employeeService.getEmployeeByUser(user);
            expenses = expenseService.getExpenseByEmployee(employee);
        } else if (user.getRole() == "SUPERIOR") {
            Superior superior = superiorService.getSuperiorByUser(user);
            List<Employee> employees = employeeService.getEmployeesBySuperior(superior);
            for (Employee employee : employees) {
                List<Expense> employeeExpenses = expenseService.getExpenseByEmployee(employee);
                expenses.addAll(employeeExpenses);
            }
        }
        return expenseService.filter(expenses, year, month, categoryId, shopId);
    }

}
