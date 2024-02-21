package spring.workshop.expenses.integration.Expense;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import spring.workshop.expenses.controllers.ExpenseController;
import spring.workshop.expenses.entities.Category;
import spring.workshop.expenses.entities.Expense;
import spring.workshop.expenses.entities.Shop;
import spring.workshop.expenses.enums.ExpenseStatus;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class ExpenseControllerIntegrationTest {

    private static final String BASE_URL = "/expenses";

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    ExpenseController controller;

    private static ObjectMapper objectMapper;

    @BeforeAll
    public static void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void testCreateExpensePositive() throws Exception {

        // Setting up request header and body for the POST request
        // Constructing the request body with the expense (valid) for creating the
        // expense
        HttpHeaders requestHeader = new HttpHeaders();
        requestHeader.setContentType(MediaType.APPLICATION_JSON);
        Expense expense = new Expense("Expense", 100.00F, LocalDate.of(2024, 02, 16), new Category(100L),
                new Shop(100L));
        String requestBody = objectMapper.writeValueAsString(expense);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, requestHeader);

        // URL for creating an Expense for Employee with
        // employee_id = employeeId (existing)
        Long employeeId = 100L;
        String url = BASE_URL + "/?employee_id=" + employeeId;

        // Send a POST request to create the expense
        ResponseEntity<Expense> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity,
                Expense.class);

        // Assert HTTP status code is OK
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Asserting that the response body contains the created expense
        // with assigned employee and status INITIAL indicating successful creating
        assertEquals("Expense", response.getBody().getName());
        assertEquals(employeeId, response.getBody().getEmployee().getId());
        assertEquals(ExpenseStatus.INITIAL, response.getBody().getStatus());
    }

    @Test
    public void testCreateExpenseNegativeNonExistingEmployee() throws Exception {

        // Setting up request header and body for the POST request
        // Constructing the request body with the expense (valid) for creating the
        // expense
        HttpHeaders requestHeader = new HttpHeaders();
        requestHeader.setContentType(MediaType.APPLICATION_JSON);
        Expense expense = new Expense("Expense", 100.00F, LocalDate.of(2024, 02, 16), new Category(100L),
                new Shop(100L));
        String requestBody = objectMapper.writeValueAsString(expense);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, requestHeader);

        // URL for creating an Expense for Employee with
        // employee_id = employeeId (non-existing)
        Long employeeId = 900L;
        String url = BASE_URL + "/?employee_id=" + employeeId;

        // Send a POST request to create the expense
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity,
                String.class);

        // Assert HTTP status code is NOT_FOUND
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testCreateExpenseNegativeMissingName() throws Exception {

        // Setting up request header and body for the POST request
        // Constructing the request body with the expense (corrupt) for creating the
        // expense
        HttpHeaders requestHeader = new HttpHeaders();
        requestHeader.setContentType(MediaType.APPLICATION_JSON);
        Expense expense = new Expense(null, 100.00F, LocalDate.of(2024, 02, 16), new Category(100L),
                new Shop(100L));
        String requestBody = objectMapper.writeValueAsString(expense);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, requestHeader);

        // URL for creating an Expense for Employee with
        // employee_id = employeeId (non-existing)
        Long employeeId = 100L;
        String url = BASE_URL + "/?employee_id=" + employeeId;

        // Send a POST request to create the expense
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity,
                String.class);

        // Assert HTTP status code is BAD_REQUEST
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testCreateExpenseNegativeNonExistingCategory() throws Exception {

        // Setting up request header and body for the POST request
        // Constructing the request body with the expense (corrupt) for creating the
        // expense
        HttpHeaders requestHeader = new HttpHeaders();
        requestHeader.setContentType(MediaType.APPLICATION_JSON);
        Expense expense = new Expense("Expense", 100.00F, LocalDate.of(2024, 02, 16), new Category(900L),
                new Shop(100L));
        String requestBody = objectMapper.writeValueAsString(expense);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, requestHeader);

        // URL for creating an Expense for Employee with
        // employee_id = employeeId (non-existing)
        Long employeeId = 100L;
        String url = BASE_URL + "/?employee_id=" + employeeId;

        // Send a POST request to create the expense
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity,
                String.class);

        // Assert HTTP status code is BAD_REQUEST
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testDeleteExpensePositive() throws Exception {

        // Defining the parameters for the DELETE request
        Long employeeId = 100L;
        Long expenseId = 100L;

        // URL for deleting an Expense with expense_id = expenseId (existing) for
        // Employee with employee_id = employeeId (existing)
        String url = BASE_URL + "/?employee_id={employeeId}&expense_id={expenseId}";

        // Send a DELETE request to create the expense
        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.DELETE, HttpEntity.EMPTY, void.class,
                employeeId, expenseId);

        // Assert HTTP status code is OK
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteExpenseNegativeNonExistingEmployee() throws Exception {

        // Defining the parameters for the DELETE request
        Long employeeId = 900L;
        Long expenseId = 100L;

        // URL for deleting an Expense with expense_id = expenseId (existing) for
        // Employee with employee_id = employeeId (non-existing)
        String url = BASE_URL + "/?employee_id={employeeId}&expense_id={expenseId}";

        // Send a DELETE request to create the expense
        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.DELETE, HttpEntity.EMPTY, void.class,
                employeeId, expenseId);

        // Assert HTTP status code is OK
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteExpenseNegativeNonExistingExpense() throws Exception {

        // Defining the parameters for the DELETE request
        Long employeeId = 100L;
        Long expenseId = 900L;

        // URL for deleting an Expense with expense_id = expenseId (non-existing) for
        // Employee with employee_id = employeeId (existing)
        String url = BASE_URL + "/?employee_id={employeeId}&expense_id={expenseId}";

        // Send a DELETE request to create the expense
        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.DELETE, HttpEntity.EMPTY, void.class,
                employeeId, expenseId);

        // Assert HTTP status code is OK
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteExpenseNegativeExpenseNotAssignedToEmployee() throws Exception {

        // Defining the parameters for the DELETE request
        Long employeeId = 100L;
        Long expenseId = 300L;

        // URL for deleting an Expense with expense_id = expenseId (non-existing) for
        // Employee with employee_id = employeeId (existing)
        String url = BASE_URL + "/?employee_id={employeeId}&expense_id={expenseId}";

        // Send a DELETE request to create the expense
        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.DELETE, HttpEntity.EMPTY, void.class,
                employeeId, expenseId);

        // Assert HTTP status code is OK
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteExpenseNegativeExpenseWrongStatus() throws Exception {

        // Defining the parameters for the DELETE request
        Long employeeId = 100L;
        Long expenseId = 200L;

        // URL for deleting an Expense with expense_id = expenseId (non-existing) for
        // Employee with employee_id = employeeId (existing)
        String url = BASE_URL + "/?employee_id={employeeId}&expense_id={expenseId}";

        // Send a DELETE request to create the expense
        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.DELETE, HttpEntity.EMPTY, void.class,
                employeeId, expenseId);

        // Assert HTTP status code is OK
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    /**
     * Test Case - functionality for getting an expense by ID.
     */
    @Test
    public void testGetExpensesByIdPositive() {
        ResponseEntity<Expense> response = restTemplate.getForEntity(BASE_URL + "/{id}", Expense.class, 200);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Expense expense = response.getBody();
        Assertions.assertNotNull(expense);
        assertEquals(200L, expense.getId());
    }

    /**
     * Test Case - functionality for getting an expense by ID - expense does not
     * exist.
     */
    @Test
    public void testGetExpensesByIdNegative() {
        ResponseEntity<String> response = restTemplate.exchange(BASE_URL + "/{id}", HttpMethod.GET, HttpEntity.EMPTY,
                String.class, 10);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * Test Case - functionality for getting all expenseses.
     */
    @Test
    public void testGetAllExpensesPositive() {
        ResponseEntity<List> response = restTemplate.getForEntity(BASE_URL, List.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    /**
     * Test Case - functionality for getting all expenseses of one shop.
     */
    @Test
    public void testGetExpensesByShopPositive() {
        ParameterizedTypeReference<List<Expense>> responseType = new ParameterizedTypeReference<List<Expense>>() {
        };
        ResponseEntity<List<Expense>> response = restTemplate.exchange(BASE_URL + "/shop/{id}", HttpMethod.GET,
                HttpEntity.EMPTY, responseType, 200L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<Expense> expenses = response.getBody();
        assertNotNull(expenses);
        assertEquals(200L, expenses.get(0).getShop().getId());
    }

}
