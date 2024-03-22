package spring.workshop.expenses.services.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spring.workshop.expenses.entities.Expense;
import spring.workshop.expenses.exceptions.ResourceNotFoundException;
import spring.workshop.expenses.repositories.AbstractRepositoryHelper;
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

    @Autowired
    private AbstractRepositoryHelper<Expense> abstractRepository;

    @Override
    public List<Expense> getAllExpenses() {
        List<Expense> expenses = expensesRepository.findAll();
        return expenses;
    }

    @Override
    public Expense getExpenseById(Long id) {
        Expense expenses = expensesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense with id = " + id + " not found."));
        return expenses;
    }

    @Override
    public Expense getExpenseByIdAndUsername(Long id, String username) {
        Expense expenses = expensesRepository
                .findByIdAndUsernameWithEmployee(id, userService.getUserByUsername(username).getUsername())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Expense with id = " + id + " and for username = " + username + " not found."));
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
                .orElseThrow(
                        () -> new ResourceNotFoundException("Expense with id = " + expense.getId() + " not found."));
        return replaceExpenses;
    }

    @Override
    public void deleteExpense(Long id) {
        expensesRepository.deleteById(id);
        LOG.info("Expense with id = {} deleted successfully.", id);
    }

    @Override

    @Transactional
    public Expense addNewExpense(Expense expense) {
        Expense savedExpense = abstractRepository.saveAndRefresh(expensesRepository, expense);
        LOG.info("Expense with id = " + expense.getId() + " created successfully.");
        return savedExpense;

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

    public List<Expense> filter(List<Expense> expenses, Integer year, Integer month, Long categoryId, Long shopId) {
        List<Expense> filter = expenses.stream()
                .filter(e -> categoryId == null || e.getCategory().getId() == categoryId)
                .filter(e -> shopId == null || e.getShop().getId() == shopId)
                .filter(e -> {
                    if (year == null && month == null) {
                        return true; // Case 4: Select all expenses
                    } else if (year != null && month == null) {
                        return e.getDate().getYear() == year; // Case 2: Select expenses with matching year
                    } else if (year == null && month != null) {
                        return e.getDate().getYear() == LocalDate.now().getYear()
                                && e.getDate().getMonth().getValue() == month; // Case 3: Select expenses with current
                        // year and matching month
                    } else {
                        return e.getDate().getYear() == year && e.getDate().getMonth().getValue() == month; // Case 1:
                        // Select
                        // expenses
                        // with
                        // matching
                        // year and
                        // month
                    }
                })
                .collect(Collectors.toList());
        return filter;
    }

    public List<Expense> getExpensesByUsername(String username) {
        return expensesRepository.findByUserId(userService.getUserByUsername(username).getId());
    }

    @Override
    public List<Expense> findByUserId(Long userId) {
        return expensesRepository.findByUserId(userService.getUserById(userId).getId());

    }

}