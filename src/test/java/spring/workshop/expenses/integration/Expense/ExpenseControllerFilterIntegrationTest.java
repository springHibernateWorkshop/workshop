package spring.workshop.expenses.integration.Expense;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import spring.workshop.expenses.controllers.ExpenseController;
import spring.workshop.expenses.dto.ExpenseDTO;
import spring.workshop.expenses.services.ExpenseService;
import spring.workshop.expenses.services.UserService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class ExpenseControllerFilterIntegrationTest {

        private static final String BASE_URL = "/expenses";

        @Autowired
        private TestRestTemplate restTemplate;

        @Autowired
        ExpenseController controller;

        @Autowired
        UserService userService;

        @Autowired
        ExpenseService expenseService;

        private static ObjectMapper objectMapper;

        @BeforeAll
        public static void setUp() {
                objectMapper = new ObjectMapper();
                objectMapper.registerModule(new JavaTimeModule());
        }

        @Test
        public void testGetAllExpensesNoFilter() {
                ParameterizedTypeReference<List<ExpenseDTO>> responseType = new ParameterizedTypeReference<List<ExpenseDTO>>() {
                };
                ResponseEntity<List<ExpenseDTO>> response = restTemplate.withBasicAuth("victoria", "password").exchange(
                                BASE_URL, HttpMethod.GET,
                                HttpEntity.EMPTY, responseType);
                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertEquals(response.getBody().size(), 3);
        }

        @Test
        public void testGetAllExpensesWithAllFilters() {
                // given
                String categoryId = "200";
                String shopId = "200";
                int year = 2024;
                int month = 2;
                UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(BASE_URL)
                                .queryParam("category-id", categoryId)
                                .queryParam("shop-id", shopId)
                                .queryParam("year", year)
                                .queryParam("month", month);
                // when
                ResponseEntity<List> response = restTemplate.withBasicAuth("victoria", "password")
                                .getForEntity(builder.toUriString(), List.class);
                // then
                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertEquals(2, response.getBody().size());
        }

        @Test
        public void testGetAllExpensesWithMonth() {
                // given
                int month = 2;
                UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(BASE_URL)
                                .queryParam("month", month);
                // when
                ResponseEntity<List> response = restTemplate.withBasicAuth("victoria", "password")
                                .getForEntity(builder.toUriString(), List.class);
                // then
                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertEquals(2, response.getBody().size());
        }

        @Test
        public void testGetAllExpensesForCategory() {
                // given
                String categoryId = "200";
                UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(BASE_URL)
                                .queryParam("category-id", categoryId);
                // when
                ResponseEntity<List> response = restTemplate.withBasicAuth("victoria", "password")
                                .getForEntity(builder.toUriString(), List.class);
                // then
                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertEquals(2, response.getBody().size());
        }

        @Test
        public void testGetAllExpensesForShop() {
                // given
                String shopId = "100";
                UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(BASE_URL)
                                .queryParam("shop-id", shopId);
                // when
                ResponseEntity<List> response = restTemplate.withBasicAuth("victoria", "password")
                                .getForEntity(builder.toUriString(), List.class);
                // then
                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertEquals(1, response.getBody().size());
        }

}
