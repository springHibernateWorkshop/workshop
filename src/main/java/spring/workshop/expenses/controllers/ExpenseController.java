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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import spring.workshop.expenses.dto.ExpenseDTO;
import spring.workshop.expenses.entities.Expense;
import spring.workshop.expenses.entities.User;
import spring.workshop.expenses.mapper.ExpenseMapper;
import spring.workshop.expenses.services.UserService;
import spring.workshop.expenses.useCases.CreateExpenseUc;
import spring.workshop.expenses.useCases.DeleteExpenseUc;
import spring.workshop.expenses.useCases.EditExpenseUc;
import spring.workshop.expenses.useCases.SubmitExpenseUc;
import spring.workshop.expenses.useCases.ViewAllExpensesUc;
import spring.workshop.expenses.useCases.ViewOneExpenseUc;

@RestController
@RequestMapping(path = "/expenses")
public class ExpenseController {

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
    private EditExpenseUc editExpenseUc;

    @Autowired
    private ViewOneExpenseUc viewOneExpenseUc;

    @Autowired
    private ExpenseMapper expenseMapper;

    // Method for creating an Expense
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create an expense", description = "Creates a new expense in the database")
    public ExpenseDTO createExpense(@RequestBody Expense expense,
            Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        return expenseMapper.toDto(createExpenseUc.createExpense(user, expense));
    }

    // Method for deleting an Expense
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete an expense", description = "Deletes an expense from the database")
    public void deleteExpense(
            @PathVariable("id") @Parameter(description = "ID of the expense to be deleted") Long expenseId,
            Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        deleteExpenseUc.deleteExpense(user, expenseId);
    }

    // Method for getting all Expenses
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all expenses", description = "Fetches all expenses from the database")
    public List<ExpenseDTO> getAllExpenses(
            @RequestParam(required = false) @Parameter(description = "Year of the expenses") Integer year,
            @RequestParam(required = false) @Parameter(description = "Month of the expenses") Integer month,
            @RequestParam(name = "category-id", required = false) @Parameter(description = "Category ID of the expenses") Long categoryId,
            @RequestParam(name = "shop-id", required = false) @Parameter(description = "Shop ID of the expenses") Long shopId,
            @RequestParam(name = "employee-id", required = false) @Parameter(description = "Only expenses of this employee") Long employeeId,
            Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        return expenseMapper
                .toDto(viewAllExpensesUc.viewAllExpenses(user, year, month, categoryId, shopId, employeeId));
    }

    // Method for getting an Expense by its ID
    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get an expense", description = "Fetches the expense with the given id from the database")
    public ExpenseDTO getExpenseById(@PathVariable @Parameter(description = "ID of the expense") Long id,
            Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        return expenseMapper.toDto(viewOneExpenseUc.viewOneExpense(user, id));
    }

    // Method for updating an Expense
    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update an expense", description = "Updates an expense in the database")
    public ExpenseDTO updateExpense(@RequestBody Expense expense,
            Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        return expenseMapper.toDto(editExpenseUc.editExpense(user, expense));
    }

    // Method for submitting an Expense
    @PutMapping(path = "/submit/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Submit an expense", description = "Submits an expense for approval")
    public ExpenseDTO submitExpense(
            @PathVariable @Parameter(description = "ID of the expense to be submitted") Long id,
            Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        return expenseMapper.toDto(submitExpenseUc.submitExpense(id, user));
    }

}
