package spring.workshop.expenses.rest;

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
import org.springframework.web.bind.annotation.RestController;

import spring.workshop.expenses.entities.Expense;
import spring.workshop.expenses.entities.Shop;
import spring.workshop.expenses.entities.User;
import spring.workshop.expenses.repos.ExpenseRepository;
import spring.workshop.expenses.services.CategoryService;
import spring.workshop.expenses.services.ExpenseService;


@RestController
@RequestMapping(path = "/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseRepository expenseRepository;
    
    @Autowired
    private ExpenseService expensesService;

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Expense> addNewExpenses(@RequestBody Expense expenses){
        return new ResponseEntity<>(expensesService.addNewExpenses(expenses), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Expense>> getAllExpenses() {
        return new ResponseEntity<>(expensesService.getAllExpenses(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Expense> getExpensesById(@PathVariable Long id) {
        return new ResponseEntity<>(expensesService.getExpensesById(id), HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Expense> updateExpenses(@RequestBody Expense expenses, @PathVariable Long id) {
        return new ResponseEntity<>(expensesService.updateExpenses(expenses,id), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Expense> deleteExpenses(@PathVariable Long id) {
        return new ResponseEntity<>(expensesService.deleteExpenses(id), HttpStatus.OK);
    }
    
    /**
     * @param date
     * @return
     */
    @GetMapping(path = "/findByDate/{date}")
    public ResponseEntity<List<Expense>> findByDate(@PathVariable LocalDate date) {
        return new ResponseEntity<>(expenseRepository.findByDate(date), HttpStatus.OK);
    }

    @GetMapping(path = "/findByCategory/{id}")
    public ResponseEntity<List<Expense>> findByCategory(@PathVariable int id) {
        return new ResponseEntity<>(expenseRepository.findByCategoryId(id), HttpStatus.OK);
    }

    /**
     * @param shopId
     * @return
     */
    @GetMapping(path = "/findByShop")
    public ResponseEntity<List<Expense>> findByShop(@PathVariable Shop shop) {
        return new ResponseEntity<>(expenseRepository.findByShop(shop), HttpStatus.OK);
    }

    /**
     * @param userId
     * @return
     */
    @GetMapping(path = "/findByUser")
    public ResponseEntity<List<Expense>> getExpensesByUser(@PathVariable User user) {
        return new ResponseEntity<>(expenseRepository.findByUser(user), HttpStatus.OK);
    }

}
