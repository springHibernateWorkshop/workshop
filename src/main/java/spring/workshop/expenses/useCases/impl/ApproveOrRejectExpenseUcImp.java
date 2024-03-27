package spring.workshop.expenses.useCases.impl;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import spring.workshop.expenses.entities.Employee;
import spring.workshop.expenses.entities.Expense;
import spring.workshop.expenses.entities.Superior;
import spring.workshop.expenses.enums.ExpenseStatus;
import spring.workshop.expenses.exceptions.ForbiddenResourceException;
import spring.workshop.expenses.exceptions.ResourceNotFoundException;
import spring.workshop.expenses.services.EmployeeService;
import spring.workshop.expenses.services.ExpenseService;
import spring.workshop.expenses.services.SuperiorService;
import spring.workshop.expenses.useCases.ApproveOrRejectExpenseUc;

@Component
public class ApproveOrRejectExpenseUcImp implements ApproveOrRejectExpenseUc{
    @Autowired
    ExpenseService expenseService;
    @Autowired
    EmployeeService employeeService;

    @Autowired
    SuperiorService superiorService;

    @Override
    public Expense approveOrRejectExpense(Long expenseId, Long superiorId, ExpenseStatus status, String note) {
        Expense expense=expenseService.getExpenseById(expenseId); 
        Employee employee= employeeService.getEmployeeById(expense.getEmployee().getId());
        Superior superior= superiorService.getSuperiorById(employee.getSuperior().getId());
        if(superiorId!=superior.getId()){
            throw new ResourceNotFoundException("Superior cannot approve this Expense");
        }
        if(expense.getStatus()!=ExpenseStatus.PENDING){
            throw new ForbiddenResourceException("Expense should have 'Pending' status");
        }
        if(!status.equals(ExpenseStatus.APPROVED) && !status.equals(ExpenseStatus.REJECTED)){
            throw new IllegalArgumentException("Expense should have 'Approved' or 'Rejected' status");
        }
        expense.setStatus(status);
        expense.setNote(note);
        return expenseService.updateExpense(expense);
    }
    
}
