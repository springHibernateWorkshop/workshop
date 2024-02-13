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
import spring.workshop.expenses.services.UserService;

@Service
public class ExpenseServiceImpl implements ExpenseService {
    private static final Logger LOG = LoggerFactory.getLogger(ExpenseServiceImpl.class);
    @Autowired
    private ExpenseRepository expensesRepository;

    @Autowired
    private UserService userService;

    @Override
    public List<Expense> getAllExpenses() {
        List<Expense> expenses = expensesRepository.findAll();
        return expenses;
    }

    @Override
    public Expense getExpenseById(Long id) {
        Expense expenses = expensesRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return expenses;
    }

    @Override
    public Expense getExpenseByIdAndUsername(Long id, String username) {
        Expense expenses = expensesRepository
                .findByIdAndUsernameWithEmployee(id, userService.getUserByUsername(username).getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return expenses;
    }

    @Override
    public Expense updateExpense(Expense expense) {
        Expense replaceExpenses = expensesRepository.findById(expense.getId()).map(upExpenses -> {
            upExpenses.setTotal(expense.getTotal());
            upExpenses.setDate(expense.getDate());
            upExpenses.setCategory(expense.getCategory());
            upExpenses.setShop(expense.getShop());
            upExpenses.setEmployee(expense.getEmployee());
            upExpenses.setNote(expense.getNote());
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
    public Expense addNewExpense(Expense expense) {
        return expensesRepository.save(expense);
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

    @Override
    public List<Expense> getExpensesByUsername(String username) {
        return expensesRepository.findByUserId(userService.getUserByUsername(username).getId());
    }

    @Override
    public List<Expense> findByUserId(Long userId) {
        return expensesRepository.findByUserId(userService.getUserById(userId).getId());
    }

}