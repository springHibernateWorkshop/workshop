package spring.workshop.expenses.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import spring.workshop.expenses.entities.Expense;
import spring.workshop.expenses.entities.User;
import spring.workshop.expenses.services.ExpenseService;
import spring.workshop.expenses.services.UserService;
import spring.workshop.expenses.useCases.CreateExpenseUc;
import spring.workshop.expenses.useCases.DeleteExpenseUc;
import spring.workshop.expenses.useCases.SubmitExpenseUc;
import spring.workshop.expenses.useCases.ViewAllExpensesUc;
import spring.workshop.expenses.useCases.ViewOneExpenseUc;

@RestController
@RequestMapping(path = "/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private UserService userService;

    @Autowired
    private CreateExpenseUc createExpenseUc;

    @Autowired
    private ViewAllExpensesUc viewAllExpensesUc;

    @Autowired
    private DeleteExpenseUc deleteExpenseUc;

    @Autowired
    private SubmitExpenseUc submitExpenseUc;

    @Autowired
    private ViewOneExpenseUc viewOneExpenseUc;

    // Method for creating an Expense
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Expense createExpense(@RequestBody Expense expense, Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        return createExpenseUc.createExpense(user, expense);
    }

    // Method for deleting an Expense
    @DeleteMapping(path = "/{expense_id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteExpense(@PathVariable("expense_id") Long expenseId, Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        deleteExpenseUc.deleteExpense(user, expenseId);
    }

    // Method for getting all Expenses
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Expense> getAllExpenses(@RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month,
            @RequestParam(name = "category-id", required = false) Long categoryId,
            @RequestParam(name = "shop-id", required = false) Long shopId,
            Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        return viewAllExpensesUc.viewAllExpenses(user, year, month, categoryId, shopId);
    }

    // Method for getting an Expense by its ID
    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Expense getExpenseById(@PathVariable Long id, Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        Expense expense = viewOneExpenseUc.viewOneExpense(user, id);

        return expense;
    }

    // TODO Method for updating an Expense
    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public Expense updateExpense(@RequestBody Expense expense) {
        return expenseService.updateExpense(expense);
    }

    // Method for submitting an Expense
    @PutMapping(path = "/submit/{expenseId}")
    @ResponseStatus(HttpStatus.OK)
    public Expense submitExpense(@PathVariable Long expenseId, Principal principal) {

        User user = userService.getUserByUsername(principal.getName());

        return submitExpenseUc.submitExpense(expenseId, user);
    }

}
