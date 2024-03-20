package spring.workshop.expenses.integration.Expense;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

import org.json.JSONObject;
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

                Category category = new Category(100L);
                category.setVersion(new Timestamp(new java.util.Date().getTime()));

                Shop shop = new Shop(100L);
                shop.setVersion(new Timestamp(new java.util.Date().getTime()));

                Expense expense = new Expense("Expense", 100.00F, LocalDate.of(2024, 02, 16), category,
                                shop);
                String requestBody = objectMapper.writeValueAsString(expense);
                HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, requestHeader);

                // URL for creating an Expense
                String url = BASE_URL;

                // Send a POST request to create the expense
                ResponseEntity<Expense> response = restTemplate.withBasicAuth("victoria", "password").exchange(url,
                                HttpMethod.POST, requestEntity,
                                Expense.class);

                // Assert HTTP status code is CREATED
                assertEquals(HttpStatus.CREATED, response.getStatusCode());

                // Asserting that the response body contains the created expense
                // with assigned employee and status INITIAL indicating successful creating
                assertEquals("Expense", response.getBody().getName());
                assertEquals(100L, response.getBody().getEmployee().getId());
                assertEquals(ExpenseStatus.INITIAL, response.getBody().getStatus());
        }

        @Test
        public void testCreateExpenseNegativNonAuthorizedUser() throws Exception {

                // Setting up request header and body for the POST request
                // Constructing the request body with the expense (valid) for
                // creating the expense
                HttpHeaders requestHeader = new HttpHeaders();
                requestHeader.setContentType(MediaType.APPLICATION_JSON);
                Expense expense = new Expense(null, 100.00F, LocalDate.of(2024, 02, 16), new Category(100L),
                                new Shop(100L));
                String requestBody = objectMapper.writeValueAsString(expense);
                HttpEntity<String> requestEntity = new HttpEntity<>(requestBody,
                                requestHeader);

                // Send a POST request to create the expense with a user that is not authorized
                ResponseEntity<String> response = restTemplate.withBasicAuth("manhton", "password").exchange(BASE_URL,
                                HttpMethod.POST,
                                requestEntity,
                                String.class);

                // Assert HTTP status code is BAD_REQUEST
                assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());

        }

        @Test
        public void testCreateExpenseNegativeMissingName() throws Exception {

                // Setting up request header and body for the POST request
                // Constructing the request body with the expense (corrupt, missing name) for
                // creating the expense
                HttpHeaders requestHeader = new HttpHeaders();
                requestHeader.setContentType(MediaType.APPLICATION_JSON);

                Category category = new Category(100L);
                category.setVersion(new Timestamp(new java.util.Date().getTime()));

                Shop shop = new Shop(100L);
                shop.setVersion(new Timestamp(new java.util.Date().getTime()));

                Expense expense = new Expense(null, 100.00F, LocalDate.of(2024, 02, 16), category,
                                shop);
                String requestBody = objectMapper.writeValueAsString(expense);
                HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, requestHeader);

                // URL for creating an Expense for Employee
                String url = BASE_URL;

                // Send a POST request to create the expense
                ResponseEntity<String> response = restTemplate.withBasicAuth("victoria", "password").exchange(url,
                                HttpMethod.POST, requestEntity,
                                String.class);

                // Assert HTTP status code is BAD_REQUEST
                assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
                assert new JSONObject(response.getBody()).getString("message")
                                .contains("not-null property references a null or transient value");
        }

        @Test
        public void testCreateExpenseNegativeNonExistingCategory() throws Exception {

                // Setting up request header and body for the POST request
                // Constructing the request body with the expense (corrupt, non-existing
                // category) for creating the expense
                HttpHeaders requestHeader = new HttpHeaders();
                requestHeader.setContentType(MediaType.APPLICATION_JSON);

                Category category = new Category(900L);

                Shop shop = new Shop(100L);

                Expense expense = new Expense("Expense", 100.00F, LocalDate.of(2024, 02, 16), category,
                                shop);
                String requestBody = objectMapper.writeValueAsString(expense);
                HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, requestHeader);

                // URL for creating an Expense
                String url = BASE_URL;

                // Send a POST request to create the expense
                ResponseEntity<String> response = restTemplate.withBasicAuth("victoria", "password").exchange(url,
                                HttpMethod.POST, requestEntity,
                                String.class);

                // Assert HTTP status code is BAD_REQUEST
                assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
                assert new JSONObject(response.getBody()).getString("message")
                                .contains("Unable to find spring.workshop.expenses.entities.Category with id 900");
        }

        @Test
        public void testDeleteExpensePositive() throws Exception {

                // Defining the parameters for the DELETE request
                Long expenseId = 100L;

                // URL for deleting an Expense with expense_id = expenseId (existing)
                String url = BASE_URL + "/{expenseId}";

                // Send a DELETE request to create the expense
                ResponseEntity<Void> response = restTemplate.withBasicAuth("victoria", "password").exchange(url,
                                HttpMethod.DELETE, HttpEntity.EMPTY,
                                void.class, expenseId);

                // Assert HTTP status code is OK
                assertEquals(HttpStatus.OK, response.getStatusCode());
        }

        @Test
        public void testDeleteExpenseNonExistingEmployee() throws Exception {

                // Defining the parameters for the DELETE request
                Long expenseId = 100L;

                // URL for deleting an Expense with expense_id = expenseId (existing)
                String url = BASE_URL + "/{expenseId}";

                // Send a DELETE request to create the expense
                ResponseEntity<String> response = restTemplate.withBasicAuth("raffael", "password").exchange(url,
                                HttpMethod.DELETE, HttpEntity.EMPTY,
                                String.class, expenseId);

                // Assert HTTP status code is OK
                assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
                assertEquals("Employee for user with id = 500 not found.",
                                new JSONObject(response.getBody()).getString("message"));
        }

        @Test
        public void testDeleteExpenseNegativeNonExistingExpense() throws Exception {

                // Defining the parameters for the DELETE request
                Long expenseId = 900L;

                // URL for deleting an Expense with expense_id = expenseId (non-existing)
                String url = BASE_URL + "/{expenseId}";

                // Send a DELETE request to create the expense
                ResponseEntity<String> response = restTemplate.withBasicAuth("victoria", "password").exchange(url,
                                HttpMethod.DELETE, HttpEntity.EMPTY,
                                String.class, expenseId);

                // Assert HTTP status code is OK
                assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
                assertEquals("Expense with id = " + expenseId + " not found.",
                                new JSONObject(response.getBody()).getString("message"));
        }

        @Test
        public void testDeleteExpenseNegativeExpenseNotAssignedToEmployee() throws Exception {

                // Defining the parameters for the DELETE request
                Long expenseId = 300L;

                // URL for deleting an Expense with expense_id = expenseId (existing, not
                // assigned to Employee)
                String url = BASE_URL + "/{expenseId}";

                // Send a DELETE request to create the expense
                ResponseEntity<String> response = restTemplate.withBasicAuth("victoria", "password").exchange(url,
                                HttpMethod.DELETE, HttpEntity.EMPTY,
                                String.class, expenseId);

                // Assert HTTP status code is OK
                assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
                assertEquals("No expense with given id found for this employee.",
                                new JSONObject(response.getBody()).getString("message"));
        }

        @Test
        public void testDeleteExpenseNegativeExpenseWrongStatus() throws Exception {

                // Defining the parameters for the DELETE request
                Long expenseId = 400L;

                // URL for deleting an Expense with expense_id = expenseId (existing, wrong
                // status)
                String url = BASE_URL + "/{expenseId}";

                // Send a DELETE request to create the expense
                ResponseEntity<String> response = restTemplate.withBasicAuth("victoria", "password")
                                .withBasicAuth("victoria", "password").exchange(url,
                                                HttpMethod.DELETE, HttpEntity.EMPTY,
                                                String.class, expenseId);

                // Assert HTTP status code is OK
                assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
                assertEquals("Expense status needs to be INITIAL or REJECTED (Current status: PENDING).",
                                new JSONObject(response.getBody()).getString("message"));
        }

        /**
         * Test Case - functionality for getting an expense by ID.
         */
        @Test
        public void testGetExpenseByIdPositiveEmployee() {
                Long expenseId = 100L;

                // Send a GET request for receive a Expense object
                ResponseEntity<Expense> response = restTemplate.withBasicAuth("victoria", "password")
                                .getForEntity(BASE_URL + "/{id}", Expense.class, expenseId);

                assertEquals(HttpStatus.OK, response.getStatusCode());
                Expense expense = response.getBody();
                Assertions.assertNotNull(expense);
                assertEquals(expenseId, expense.getId());
        }

        /**
         * Test Case - functionality for getting an expense by ID - expense does not
         * exist.
         */
        @Test
        public void testGetExpenseByIdNegativeEmployee() {
                Long expenseId = 123L;

                // Send a GET request for receive a Expense object
                ResponseEntity<Expense> response = restTemplate.withBasicAuth("victoria", "password")
                                .getForEntity(BASE_URL + "/{id}", Expense.class, expenseId);

                assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

                Expense expense = response.getBody();
                Assertions.assertNull(expense.getId());
        }

        /**
         * Test Case - functionality for getting all expenseses.
         */
        @Test
        public void testGetAllExpensesPositive() {
                ParameterizedTypeReference<List<Expense>> responseType = new ParameterizedTypeReference<List<Expense>>() {
                };
                ResponseEntity<List<Expense>> response = restTemplate.withBasicAuth("victoria", "password").exchange(
                                BASE_URL, HttpMethod.GET,
                                HttpEntity.EMPTY, responseType);
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
                ResponseEntity<List<Expense>> response = restTemplate.withBasicAuth("victoria", "password").exchange(
                                BASE_URL + "/shop/{id}", HttpMethod.GET,
                                HttpEntity.EMPTY, responseType, 200L);
                assertEquals(HttpStatus.OK, response.getStatusCode());
                List<Expense> expenses = response.getBody();
                assertNotNull(expenses);
                assertEquals(200L, expenses.get(0).getShop().getId());
        }

}
