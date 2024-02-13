package spring.workshop.expenses.controllers;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import spring.workshop.expenses.entities.Expense;
import spring.workshop.expenses.enums.Right;
import spring.workshop.expenses.enums.Role;
import spring.workshop.expenses.security.SecureMethod;
import spring.workshop.expenses.services.ExpenseService;
import spring.workshop.expenses.services.UserService;

@RestController
@RequestMapping(path = "/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private UserService userService;

    @GetMapping
    @SecureMethod(Rights = Right.VIEW_EXPENSES)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Expense>> getAllExpenses(Principal principal) {
        // Collection<? extends GrantedAuthority> authorities =
        // SecurityContextHolder.getContext().getAuthentication()
        // .getAuthorities();
        // if (authorities.contains(new SimpleGrantedAuthority("ROLE_SUPERIOR"))) {
        // return new ResponseEntity<>(expenseService.getAllExpenses(), HttpStatus.OK);
        // } else if (authorities.contains(new SimpleGrantedAuthority("ROLE_EMPLOYEE")))
        // {
        // return new
        // ResponseEntity<>(expenseService.getExpensesByUsername(principal.getName()),
        // HttpStatus.OK);
        // } else {
        // return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
        // }

        Role role = userService.getUserByUsername(principal.getName()).getRole();
        if (Role.SUPERIOR.equals(role)) {
            return new ResponseEntity<>(expenseService.getAllExpenses(), HttpStatus.OK);
        } else if (Role.EMPLOYEE.equals(role)) {
            return new ResponseEntity<>(expenseService.getExpensesByUsername(principal.getName()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
        }
    }

    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Expense getExpenseById(@PathVariable Long id, Principal principal) {
        return expenseService.getExpenseByIdAndUsername(id, principal.getName());
    }

    // @GetMapping(path = "/{id}")
    // @ResponseStatus(HttpStatus.OK)
    // public Expense getExpenseById(@PathVariable Long id) {
    // return expenseService.getExpenseById(id);
    // }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public Expense updateExpense(@RequestBody Expense expense) {
        return expenseService.updateExpense(expense);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Boolean deleteExpense(@PathVariable Long id) {
        return expenseService.deleteExpense(id);
    }

    @GetMapping(path = "/date/{date}")
    @ResponseStatus(HttpStatus.OK)
    public List<Expense> findByDate(@PathVariable LocalDate date) {
        return expenseService.findByDate(date);
    }

    @GetMapping(path = "/category/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Expense> findByCategoryId(@PathVariable Long categoryId) {
        return expenseService.findByCategoryId(categoryId);
    }

    @GetMapping(path = "/shop/{shopId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Expense> findByShopId(@PathVariable Long shopId) {
        return expenseService.findByShopId(shopId);
    }

    @GetMapping(path = "/user/{employeeId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Expense> getExpensesByEmployeeId(@PathVariable Long employeeId) {
        return expenseService.findByEmployeeId(employeeId);
    }

}
