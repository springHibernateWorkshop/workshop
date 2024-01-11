package spring.workshop.expenses.services;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import spring.workshop.expenses.entities.Expenses;
import spring.workshop.expenses.repos.ExpensesRepository;

@Service
public class ExpensesServiceImpl implements ExpensesService {

    @Autowired
    private ExpensesRepository expensesRepository;

        @Override
    public List<Expenses> getAllExpenses() {
        List<Expenses> expenses = expensesRepository.findAll();
        return expenses;
    }

    @Override
    public Expenses getExpensesById(Long id) {
        Expenses expenses = expensesRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return expenses;
    }
    @Override
    public Expenses updateExpenses(Expenses expenses, Long id) {
        Expenses replaceExpenses = expensesRepository.findById(id).map(upExpenses -> {
            upExpenses.setTotal(expenses.getTotal());
            upExpenses.setDate(expenses.getDate());
            upExpenses.setCategoryId(expenses.getCategoryId());
            upExpenses.setShopId(expenses.getShopId());
            upExpenses.setUserId(expenses.getUserId());
            upExpenses.setNote(expenses.getNote());
            return expensesRepository.save(upExpenses);
        })
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return replaceExpenses;
    }

    @Override
    public Expenses deleteExpenses(Long id) {
        Expenses expenses = expensesRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        expensesRepository.deleteById(id);
        return expenses;
    }

    @Override
    public Expenses addNewExpenses(Expenses expenses) {
        return expensesRepository.save(expenses);
    }

}