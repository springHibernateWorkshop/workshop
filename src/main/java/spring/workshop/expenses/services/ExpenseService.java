package spring.workshop.expenses.services;

import java.time.LocalDate;
import java.util.List;

import spring.workshop.expenses.entities.Expense;
import spring.workshop.expenses.entities.Shop;
import spring.workshop.expenses.entities.User;

public interface ExpenseService {
    public List<Expense> getAllExpenses();

    public Expense getExpenseById(Long id);

    public Expense updateExpense(Expense expense);

    public Boolean deleteExpense(Long id);

    public Expense addNewExpense(Expense expense);

    public List<Expense> findByDate(LocalDate date);

    public List<Expense> findByCategoryId(int id);

    public List<Expense> findByShop(Shop shop);

    public List<Expense> findByUser(User user);
}
