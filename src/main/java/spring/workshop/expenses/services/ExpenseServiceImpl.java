package spring.workshop.expenses.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import spring.workshop.expenses.entities.Expense;
import spring.workshop.expenses.repos.ExpenseRepository;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    private ExpenseRepository expensesRepository;

        @Override
    public List<Expense> getAllExpenses() {
        List<Expense> expenses = expensesRepository.findAll();
        return expenses;
    }

    @Override
    public Expense getExpensesById(Long id) {
        Expense expenses = expensesRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return expenses;
    }
    @Override
    public Expense updateExpenses(Expense expense, Long id) {
        Expense replaceExpenses = expensesRepository.findById(id).map(upExpenses -> {
            upExpenses.setTotal(expense.getTotal());
            upExpenses.setDate(expense.getDate());
            upExpenses.setCategoryId(expense.getCategoryId());
            upExpenses.setShop(expense.getShop());
            upExpenses.setUser(expense.getUser());
            upExpenses.setNote(expense.getNote());
            return expensesRepository.save(upExpenses);
        })
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return replaceExpenses;
    }

    @Override
    public Expense deleteExpenses(Long id) {
        Expense expenses = expensesRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        expensesRepository.deleteById(id);
        return expenses;
    }

    @Override
    public Expense addNewExpenses(Expense expenses) {
        return expensesRepository.save(expenses);
    }

}