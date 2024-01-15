package spring.workshop.expenses.integration.Expenses;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.net.URI;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import spring.workshop.expenses.entities.Expense;
import spring.workshop.expenses.entities.Shop;
import spring.workshop.expenses.entities.User;
import spring.workshop.expenses.rest.ExpenseController;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class ExpensesControllerIntegrationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    ExpenseController controller;
    private static final String BASE_URL = "/expenses";

    /**
     * Test Case - functionality of getting all expenseses.
     */

    @Test
    public void testGetAllExpenses() {
        ResponseEntity<List> response = restTemplate.getForEntity(BASE_URL, List.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    /**
     * Test Case - positive scenario of getting a expenses by its ID.
     */
    @Test
    public void testGetExpensesByIdPositive() {
        ResponseEntity<Expense> response = restTemplate.getForEntity(BASE_URL + "/{id}", Expense.class, 200);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Expense expenses = response.getBody();
        Assertions.assertNotNull(expenses);
        assertEquals("Note 2", expenses.getNote());
    }

    /**
     * Test Case - negative scenario getting a expenses by ID - expenses does not
     * exist.
     */
    @Test
    public void testGetExpensesByIdNegative() {
        ResponseEntity<Expense> response = restTemplate.getForEntity(BASE_URL + "/{id}", Expense.class, 10);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * Test Case - positive scenario of adding a expenses.
     */
    @Test
    public void testAddExpensesPositive() {
        Expense expensetoAdd = new Expense(500L, 1.99f, LocalDate.of(1994, 10, 1), 100L, new Shop(100L),
                new User(100L, "Test"),
                "Expense 4");
        URI newExpensesLocation = restTemplate.postForLocation(BASE_URL, expensetoAdd);
        ResponseEntity<Expense> response = restTemplate.getForEntity(newExpensesLocation, Expense.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Expense expense = response.getBody();
        assertEquals("Expense 4", expense.getNote());
    }

    /**
     * Test Case - positive scenario of deleting a expenses.
     */
    @Test
    public void testDeleteExpensesPositive() {
        ResponseEntity<Expense> response = restTemplate.getForEntity(BASE_URL + "/{id}", Expense.class, 100);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        restTemplate.delete("/expenses/{id}", 100);
        ResponseEntity<Expense> responseAfterDelete = restTemplate.getForEntity(BASE_URL + "/{id}", Expense.class,
                100);
        assertEquals(HttpStatus.NOT_FOUND, responseAfterDelete.getStatusCode());
    }

    /**
     * Test Case - negative scenario of deleting a expenses.
     * It sends a GET request to retrieve a expenses with the specified ID,
     * and expects a NOT_FOUND status code in the response.
     * Then, it sends a DELETE request to delete the expenses with the same ID,
     * and again expects a NOT_FOUND status code in the response.
     */
    @Test
    public void testDeleteExpensesNegative() {
        ResponseEntity<Expense> response = restTemplate.getForEntity(BASE_URL + "/{id}", Expense.class, 6);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        ResponseEntity<Boolean> responseAfterDelete = restTemplate.exchange(BASE_URL + "/{id}", HttpMethod.DELETE,
                null, Boolean.class, 6);
        assertEquals(Boolean.FALSE, responseAfterDelete.getBody());
    }

    @Test
    public void testUpdateExpensesPositive() throws ParseException {
        Expense expense = new Expense(300L, 1.99f, LocalDate.of(1994, 10, 1), 100L, new Shop(100L),
                new User(100L, "Test"),
                "Expenses3");
        Expense response = restTemplate.getForObject(BASE_URL + "/{id}", Expense.class, 300);
        assertEquals("Note 3", response.getNote());
        restTemplate.put("/expenses", expense);
        Expense responseAfterUpdate = restTemplate.getForObject(BASE_URL + "/{id}", Expense.class, 300);
        assertEquals("Expenses3", responseAfterUpdate.getNote());
    }

    @Test
    public void testUpdateExpensesNegative() throws ParseException {
        Expense expense = new Expense(9999L, 1.99f, LocalDate.of(1994, 10, 1), 300L, new Shop(300L),
                new User(300L, "Test"),
                "Expenses3");
        ResponseEntity<Expense> response = restTemplate.exchange(BASE_URL, HttpMethod.PUT,
                new HttpEntity<>(expense), Expense.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

}
