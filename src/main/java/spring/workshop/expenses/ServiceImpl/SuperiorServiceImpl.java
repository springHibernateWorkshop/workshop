package spring.workshop.expenses.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import spring.workshop.expenses.entities.Expense;
import spring.workshop.expenses.services.ExpenseService;
import spring.workshop.expenses.services.SuperiorService;

public class SuperiorServiceImpl implements SuperiorService {

    @Autowired
    private ExpenseService expenseService;

    @Override
    public Expense viewExpense(Long userId, Long expenseId) {
        Expense expense = expenseService.getExpenseById(expenseId);
        //TODO check if employee that owns the expense, reports to this superior
        return expense;  
    }

    @Override
    public List<Expense> viewExpenses(Long userId, Long employeeId, String year, String month, Long categoryId,
            Long shopId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'viewExpenses'");
    }

    @Override
    public List<Expense> viewAllExpenses(Long userId, String year, String month, Long categoryId, Long shopId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'viewAllExpenses'");
    }

    @Override
    public Object viewReport(Long userId, String year, String month, Long categoryId, Long shopId, String format) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'viewReport'");
    }

    @Override
    public void approveExpense(Long userId, Long expenseId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'approveExpense'");
    }
    
}
