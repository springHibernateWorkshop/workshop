package spring.workshop.expenses.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import spring.workshop.expenses.services.ExpenseService;
import spring.workshop.expenses.useCases.CreateExpenseUc;
import spring.workshop.expenses.useCases.DeleteExpenseUc;

@RestController
@RequestMapping(path = "/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private CreateExpenseUc createExpenseUc;

    @Autowired
    private DeleteExpenseUc deleteExpenseUc;

    // Method for creating an Expense
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Expense createExpense(@RequestBody Expense expense) {
        User user = new User(100L, "Victoria", null, "EMPLOYEE");
        return createExpenseUc.createExpense(user, expense);
    }

    // Method for deleting an Expense
    @DeleteMapping(path = "/{expense_id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteExpense(@PathVariable("expense_id") Long expenseId) {
        User user = new User(100L, "Victoria", null, "EMPLOYEE");
        deleteExpenseUc.deleteExpense(user, expenseId);
    }

    @GetMapping
    public ResponseEntity<List<Expense>> getAllExpenses() {
        return new ResponseEntity<>(expenseService.getAllExpenses(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Expense> getExpenseById(@PathVariable Long id) {
        return new ResponseEntity<>(expenseService.getExpenseById(id), HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<Expense> updateExpense(@RequestBody Expense expense) {
        return new ResponseEntity<>(expenseService.updateExpense(expense), HttpStatus.OK);
    }

    @GetMapping(path = "/date/{date}")
    public ResponseEntity<List<Expense>> findByDate(@PathVariable LocalDate date) {
        return new ResponseEntity<>(expenseService.findByDate(date), HttpStatus.OK);
    }

    @GetMapping(path = "/category/{categoryId}")
    public ResponseEntity<List<Expense>> findByCategoryId(@PathVariable Long categoryId) {
        return new ResponseEntity<>(expenseService.findByCategoryId(categoryId), HttpStatus.OK);
    }

    @GetMapping(path = "/shop/{shopId}")
    public ResponseEntity<List<Expense>> findByShopId(@PathVariable Long shopId) {
        return new ResponseEntity<>(expenseService.findByShopId(shopId), HttpStatus.OK);
    }

    @GetMapping(path = "/user/{employeeId}")
    public ResponseEntity<List<Expense>> getExpensesByEmployeeId(@PathVariable Long employeeId) {
        return new ResponseEntity<>(expenseService.findByEmployeeId(employeeId), HttpStatus.OK);
    }

}
