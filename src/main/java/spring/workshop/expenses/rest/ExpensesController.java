package spring.workshop.expenses.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import spring.workshop.expenses.entities.Expenses;
import spring.workshop.expenses.services.ExpensesService;


@RestController
@RequestMapping(path = "/expenses")
public class ExpensesController {

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
    


}
