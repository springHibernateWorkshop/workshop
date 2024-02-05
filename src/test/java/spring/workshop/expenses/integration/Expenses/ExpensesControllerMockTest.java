package spring.workshop.expenses.integration.Expenses;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.transaction.Transactional;
import spring.workshop.expenses.entities.Category;
import spring.workshop.expenses.entities.Expense;
import spring.workshop.expenses.entities.Shop;
import spring.workshop.expenses.entities.User;
import spring.workshop.expenses.repos.ExpenseRepository;

/**
 * These tests are using the MockMvc class to test the CExpensesController
 * class.
 * MockMvc is a Spring class that allows us to test the controller without
 * starting the server.
 * Unlike the integration tests, we are not testing the entire application, but
 * only the controller.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@Rollback
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class ExpensesControllerMockTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ExpenseRepository repo;

        private final String BASE_URL = "/expenses";

        private ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

        /**
         * Test Case - retrieving all expenses.
         * 
         * @throws Exception if an error occurs during the test
         */
        @Test
        public void TestGetAllExpenses() throws Exception {
                // Arrange
                assertEquals(3, repo.findAll().size());

                // Act & Assert
                mockMvc.perform(get(BASE_URL))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$.length()").value(3));

        }

        /**
         * Test Case - getting an expenses by its ID.
         * 
         * @throws Exception if an error occurs during the test
         */
        @Test
        public void testGetExpenseById() throws Exception {
                // Arrange

                // Act & Assert
                mockMvc.perform(get(BASE_URL + "/{id}", 100))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.note").value("Note 1"));

        }

        /**
         * Test Case - behavior of adding a new expenses.
         * 
         * @throws Exception if an error occurs during the test
         */
        @Test
        public void testAddNewExpense() throws Exception {
                // Arrange
                Expense expenses = new Expense(999L, 999.99f, LocalDate.of(1994, 10, 1), new Category(100L),
                                new Shop(100L),
                                new User(100L, "username", "pass", 2L),
                                "example_note_1");

                assertEquals(3, repo.findAll().size());

                // Act
                mockMvc.perform(post(BASE_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(expenses)))
                                .andExpect(status().isCreated());

                // Assert
                assertEquals(4, repo.findAll().size());
        }

        /**
         * Test Case - updating an expense.
         *
         * @throws Exception if an error occurs during the test
         */
        @Test
        public void testUpdateExpense() throws Exception {
                // Arrange

                Expense expense = new Expense(100L, 999.99f, LocalDate.of(1994, 10, 1), new Category(100L),
                                new Shop(100L),
                                new User(100L, "username", "pass", 2L),
                                "Expense 1");
                assertEquals("Note 1", repo.findById(100L).get().getNote());

                // Act
                mockMvc.perform(put(BASE_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(expense)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.note").value("Expense 1"));

                // Assert
                assertEquals(expense.getNote(), repo.findById(100L).get().getNote());
        }

        /**
         * Test Case - functionality of deleting an expanse.
         *
         * @throws Exception if an error occurs during the test
         */
        @Test
        public void testDeleteExpense() throws Exception {
                // Arrange
                assertEquals(3, repo.findAll().size());

                // Act
                mockMvc.perform(delete(BASE_URL + "/{id}", 100))
                                .andExpect(status().isOk())
                                .andExpect(content().string("true"));

                // Assert
                assertEquals(2, repo.findAll().size());
        }

}
