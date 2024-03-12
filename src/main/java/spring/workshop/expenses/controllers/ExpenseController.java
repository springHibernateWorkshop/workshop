package spring.workshop.expenses.controllers;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import spring.workshop.expenses.entities.Expense;
import spring.workshop.expenses.entities.User;
import spring.workshop.expenses.security.Role;
import spring.workshop.expenses.services.ExpenseService;
import spring.workshop.expenses.services.UserService;
import spring.workshop.expenses.useCases.CreateExpenseUc;
import spring.workshop.expenses.useCases.DeleteExpenseUc;
import spring.workshop.expenses.useCases.SubmitExpenseUc;

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
    private DeleteExpenseUc deleteExpenseUc;

    @Autowired
    private SubmitExpenseUc submitExpenseUc;

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

    @GetMapping
    @PreAuthorize("hasAuthority('VIEW_EXPENSES')")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Expense>> getAllExpenses(Principal principal) {// TODO move to UC

        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities();
        if (authorities.contains(Role.ROLE_SUPERIOR)) {
            return new ResponseEntity<>(expenseService.getAllExpenses(), HttpStatus.OK);
        } else if (authorities.contains(Role.ROLE_EMPLOYEE)) {
            return new ResponseEntity<>(expenseService.getExpensesByUsername(principal.getName()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
        }
    }

    @GetMapping(path = "/{id}")
    @PreAuthorize("hasAuthority('VIEW_EXPENSES')")
    @ResponseStatus(HttpStatus.OK)
    public Expense getExpenseById(@PathVariable Long id, Principal principal) {
        return expenseService.getExpenseById(id);
    }

    @PutMapping()
    @PreAuthorize("hasAuthority('EDIT_EXPENSES')")
    @ResponseStatus(HttpStatus.OK)
    public Expense updateExpense(@RequestBody Expense expense) {
        return expenseService.updateExpense(expense);
    }

    @GetMapping(path = "/date/{date}")
    @ResponseStatus(HttpStatus.OK)
    public List<Expense> findByDate(@PathVariable LocalDate date) {
        return expenseService.findByDate(date);
    }

    @GetMapping(path = "/category/{categoryId}")
    @PreAuthorize("hasAuthority('VIEW_EXPENSES')")
    @ResponseStatus(HttpStatus.OK)
    public List<Expense> findByCategoryId(@PathVariable Long categoryId) {
        return expenseService.findByCategoryId(categoryId);
    }

    @GetMapping(path = "/shop/{shopId}")
    @PreAuthorize("hasAuthority('VIEW_EXPENSES')")
    @ResponseStatus(HttpStatus.OK)
    public List<Expense> findByShopId(@PathVariable Long shopId) {
        return expenseService.findByShopId(shopId);
    }

    @GetMapping(path = "/user/{employeeId}")
    @PreAuthorize("hasAuthority('VIEW_EXPENSES')")
    @ResponseStatus(HttpStatus.OK)
    public List<Expense> getExpensesByEmployeeId(@PathVariable Long employeeId) {
        return expenseService.findByEmployeeId(employeeId);
    }

    @PutMapping(path = "/submit/{expenseId}")
    @ResponseStatus(HttpStatus.OK)
    public Expense submitExpense(@PathVariable Long expenseId, Principal principal) {

        User user = userService.getUserByUsername(principal.getName());

        return submitExpenseUc.submitExpense(expenseId, user);
    }

}
