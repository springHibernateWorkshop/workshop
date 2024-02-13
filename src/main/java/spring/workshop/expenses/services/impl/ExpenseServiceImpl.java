package spring.workshop.expenses.services.impl;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import spring.workshop.expenses.entities.Expense;
import spring.workshop.expenses.repositories.ExpenseRepository;
import spring.workshop.expenses.services.ExpenseService;

@Service
public class ExpenseServiceImpl implements ExpenseService {
    private static final Logger LOG = LoggerFactory.getLogger(ExpenseServiceImpl.class);
    @Autowired
    private ExpenseRepository expensesRepository;

    @Override
    public List<Expense> getAllExpenses() {
        List<Expense> expenses = expensesRepository.findAll();
        return expenses;
    }

    @Override
    public Expense getExpenseById(Long id) {
        Expense expense = expensesRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return expense;
    }

    @Override
    public Expense updateExpense(Expense expense) {
        Expense replaceExpenses = expensesRepository.findById(expense.getId()).map(upExpenses -> {
            upExpenses.setTotal(expense.getTotal());
            upExpenses.setDate(expense.getDate());
            upExpenses.setCategory(expense.getCategory());
            upExpenses.setShop(expense.getShop());
            upExpenses.setEmployee(expense.getEmployee());
            upExpenses.setName(expense.getName());
            return expensesRepository.save(upExpenses);
        })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return replaceExpenses;
    }

    @Override
    public Boolean deleteExpense(Long id) {

        return expensesRepository.findById(id).map(e -> {
            expensesRepository.delete(e);
            LOG.info("Expense {} deleted", e.getId());
            return true;
        }).orElseGet(() -> {
            LOG.warn("Expense ID {} not found for delete", id);
            return false;
        });
    }

    @Override
    public Expense addNewExpense(Expense expenses) {
        return expensesRepository.save(expenses);
    }

    @Override
    public List<Expense> findByDate(LocalDate date) {
        return expensesRepository.findByDate(date);
    }

    @Override
    public List<Expense> findByCategoryId(Long categoryId) {
        return expensesRepository.findByCategoryId(categoryId);
    }

    @Override
    public List<Expense> findByShopId(Long shopId) {
        return expensesRepository.findByShopId(shopId);
    }

    @Override
    public List<Expense> findByEmployeeId(Long employeeId) {
        return expensesRepository.findByEmployeeId(employeeId);
    }

}