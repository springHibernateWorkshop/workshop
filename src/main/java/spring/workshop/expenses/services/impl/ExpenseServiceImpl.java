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

    @Autowired
    private ExpenseRepository expensesRepository;

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

}