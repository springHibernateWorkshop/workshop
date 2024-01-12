package spring.workshop.expenses.rest;

import java.sql.Date;
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

import spring.workshop.expenses.entities.Expenses;
import spring.workshop.expenses.repos.ExpensesRepository;
import spring.workshop.expenses.repos.UserRepository;
import spring.workshop.expenses.services.ExpensesService;


@RestController
@RequestMapping(path = "/expenses")
public class ExpensesController {

    private ExpensesRepository expensesRepository;

    @Autowired
    private ExpensesService expensesService;

    @PostMapping
    public ResponseEntity<Expenses> addNewExpenses(@RequestBody Expenses expenses){

        return new ResponseEntity<>(expensesService.addNewExpenses(expenses), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Expenses>> getAllExpenses() {
        return new ResponseEntity<>(expensesService.getAllExpenses(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Expenses> getExpensesById(@PathVariable Long id) {
        return new ResponseEntity<>(expensesService.getExpensesById(id), HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Expenses> updateExpenses(@RequestBody Expenses expenses, @PathVariable Long id) {
        return new ResponseEntity<>(expensesService.updateExpenses(expenses,id), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Expenses> deleteExpenses(@PathVariable Long id) {
        return new ResponseEntity<>(expensesService.deleteExpenses(id), HttpStatus.OK);
    }
    
    @GetMapping(path = "/findByDate")
    public ResponseEntity<List<Expenses>> getExpensesByDate(@PathVariable Date date) {
        return new ResponseEntity<>(expensesRepository.getExpensesByDate(date), HttpStatus.OK);
    }

    @GetMapping(path = "/findByCategory")
    public ResponseEntity<List<Expenses>> getExpensesByCategoryId(@PathVariable Long categoryId) {
        return new ResponseEntity<>(expensesRepository.getExpensesByCategoryId(categoryId), HttpStatus.OK);
    }

    @GetMapping(path = "/findByShop")
    public ResponseEntity<List<Expenses>> getExpensesByShopId(@PathVariable Long shopId) {
        return new ResponseEntity<>(expensesRepository.getExpensesByShopId(shopId), HttpStatus.OK);
    }

    @GetMapping(path = "/findByUser")
    public ResponseEntity<List<Expenses>> getExpensesByUserId(@PathVariable Long userId) {
        return new ResponseEntity<>(expensesRepository.getExpensesByUserId(userId), HttpStatus.OK);
    }

}
