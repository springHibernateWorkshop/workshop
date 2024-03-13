package spring.workshop.expenses.services.impl;

import java.time.LocalDate;
import java.util.List;
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

@Service
public class ExpenseServiceImpl implements ExpenseService {
    private static final Logger LOG = LoggerFactory.getLogger(ExpenseServiceImpl.class);

    private ExpenseRepository expenseRepository;

    @Autowired
    private AbstractRepositoryHelper<Expense> abstractRepositoryHelper;

    @Autowired
    private void setExpenseRepository(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
        abstractRepositoryHelper.setRepository(expenseRepository);
    }

    @Override
    public List<Expense> getAllExpenses() {
        List<Expense> expenses = expenseRepository.findAll();
        return expenses;
    }

    @Override
    public Expense getExpenseById(Long id) {
        Expense expenses = expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense with id = " + id + " not found."));
        return expenses;
    }

    @Override
    public Expense updateExpense(Expense expense) {
        Expense replaceExpenses = expenseRepository.findById(expense.getId()).map(upExpenses -> {
            upExpenses.setTotal(expense.getTotal());
            upExpenses.setDate(expense.getDate());
            upExpenses.setCategory(expense.getCategory());
            upExpenses.setShop(expense.getShop());
            upExpenses.setEmployee(expense.getEmployee());
            upExpenses.setNote(expense.getNote());
            return abstractRepositoryHelper.saveAndRefresh(upExpenses);
        })
                .orElseThrow(
                        () -> new ResourceNotFoundException("Expense with id = " + expense.getId() + " not found."));
        return replaceExpenses;
    }

    @Override
    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
        LOG.info("Expense with id = {} deleted successfully.", id);
    }

    @Override
    @Transactional
    public Expense addNewExpense(Expense expense) {
        Expense savedExpense = abstractRepositoryHelper.saveAndRefresh(expense);
        LOG.info("Expense with id = " + expense.getId() + " created successfully.");
        return savedExpense;
    }

    @Override
    public List<Expense> findByDate(LocalDate date) {
        return expenseRepository.findByDate(date);
    }

    @Override
    public List<Expense> findByCategoryId(Long categoryId) {
        return expenseRepository.findByCategoryId(categoryId);
    }

    @Override
    public List<Expense> findByShopId(Long shopId) {
        return expenseRepository.findByShopId(shopId);
    }

    @Override
    public List<Expense> findByEmployeeId(Long employeeId) {
        return expenseRepository.findByEmployeeId(employeeId);
    }

}