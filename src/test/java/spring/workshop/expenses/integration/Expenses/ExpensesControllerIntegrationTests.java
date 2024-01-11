package spring.workshop.expenses.integration.Expenses;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import spring.workshop.expenses.entities.Expenses;
import spring.workshop.expenses.rest.ExpensesController;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)



public class ExpensesControllerIntegrationTests {
    

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    ExpensesController controller;
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
        ResponseEntity<Expenses> response = restTemplate.getForEntity(BASE_URL + "/{id}", Expenses.class, 200);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Expenses expenses = response.getBody();
        Assertions.assertNotNull(expenses);
        assertEquals("Expenses2", expenses.getNote());
    }

    /**
     * Test Case - negative scenario getting a expenses by ID - expenses does not exist.
     */
    @Test
    public void testGetExpensesByIdNegative() {
        ResponseEntity<Expenses> response = restTemplate.getForEntity(BASE_URL + "/{id}", Expenses.class, 10);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * Test Case - positive scenario of adding a expenses.
     */
    @Test
    public void testAddExpensesPositive() {
        URI newExpensesLocation = restTemplate.postForLocation(BASE_URL, "Expenses4");
        ResponseEntity<Expenses> response = restTemplate.getForEntity(newExpensesLocation, Expenses.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Expenses expenses = response.getBody();
        assertEquals("Expenses4", expenses.getNote());
    }


    /**
     * Test Case - positive scenario of deleting a expenses.
     */
    @Test
    public void testDeleteExpensesPositive() {
        ResponseEntity<Expenses> response = restTemplate.getForEntity(BASE_URL + "/{id}", Expenses.class, 100);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        restTemplate.delete("/expenses/{id}", 100);
        ResponseEntity<Expenses> responseAfterDelete = restTemplate.getForEntity(BASE_URL + "/{id}", Expenses.class,
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
        ResponseEntity<Expenses> response = restTemplate.getForEntity(BASE_URL + "/{id}", Expenses.class, 6);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        ResponseEntity<Boolean> responseAfterDelete = restTemplate.exchange(BASE_URL + "/{id}", HttpMethod.DELETE,
                null, Boolean.class, 6);
        assertEquals(Boolean.FALSE, responseAfterDelete.getBody());
    }

    @Test
    public void testUpdateExpensesPositive() throws ParseException {
        Expenses expenses = new Expenses();
        expenses.setId(9999);
        expenses.setTotal((float) 1.99);
        expenses.setDate((java.sql.Date) new SimpleDateFormat("dd/MM/yyyy").parse("01/01/1994"));
        expenses.setCategoryId(1);
        expenses.setShopId(1);
        expenses.setUserId(1);
        expenses.setNote("Expenses3");
        Expenses response = restTemplate.getForObject(BASE_URL + "/{id}", Expenses.class, 300);
        assertEquals("Expenses3", response.getNote());
        restTemplate.put("/expenses", expenses);
        Expenses responseAfterUpdate = restTemplate.getForObject(BASE_URL + "/{id}", Expenses.class, 300);
        assertEquals("Expenses3", responseAfterUpdate.getNote());
    }

    @Test
    public void testUpdateExpensesNegative() throws ParseException {
        Expenses expenses = new Expenses();
        expenses.setId(9999);
        expenses.setTotal((float) 1.99);
        expenses.setDate((java.sql.Date) new SimpleDateFormat("dd/MM/yyyy").parse("01/01/1904"));
        expenses.setCategoryId(1);
        expenses.setShopId(1);
        expenses.setUserId(1);
        expenses.setNote("Expenses3");
        ResponseEntity<Expenses> response = restTemplate.exchange(BASE_URL, HttpMethod.PUT,
                new HttpEntity<>(expenses), Expenses.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

}
