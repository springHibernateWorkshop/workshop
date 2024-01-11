package spring.workshop.expenses.integration.Expenses;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.text.SimpleDateFormat;

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

import jakarta.transaction.Transactional;
import spring.workshop.expenses.entities.Expenses;
import spring.workshop.expenses.repos.ExpensesRepository;

/**
 * These tests are using the MockMvc class to test the CExpensesController class.
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
    private ExpensesRepository repo;

    private final String BASE_URL = "/expenses";

    /**
     * Test Case - retrieving all categories.
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
     * Test Case - getting a expenses by its ID.
     * 
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testGetExpensesById() throws Exception {
        // Arrange
        assertEquals(3, repo.findAll().size());

        // Act & Assert
        mockMvc.perform(get(BASE_URL + "/{id}", 100))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Expenses1"));

    }

    /**
     * Test Case - behavior of adding a new expenses.
     * 
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testAddNewExpenses() throws Exception {
        // Arrange
        Expenses expenses = new Expenses();
        expenses.setId(999);
        expenses.setTotal((float) 99.99);
        expenses.setDate((java.sql.Date) new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2024"));
        expenses.setCategoryId(1);
        expenses.setShopId(1);
        expenses.setUserId(1);
        expenses.setNote("example_note_1");

        assertEquals(3, repo.findAll().size());

        // Act
        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(expenses)))
                .andExpect(status().isCreated());

        // Assert
        assertEquals(4, repo.findAll().size());
    }

    /**
     * Test Case - updating a expenses.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testUpdateExpenses() throws Exception {
        // Arrange
        Expenses expenses = new Expenses();
        expenses.setId(999);
        expenses.setTotal((float) 999.99);
        expenses.setDate((java.sql.Date) new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2000"));
        expenses.setCategoryId(12);
        expenses.setShopId(12);
        expenses.setUserId(12);
        expenses.setNote("example_note_11");
        assertEquals("example_note_11", repo.findById((long) 999).get().getNote());

        // Act
        mockMvc.perform(put(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(expenses)))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.name").value("example_note_11"));

        // Assert
        assertEquals(expenses.getNote(), repo.findById((long) 999).get().getNote());
    }

    /**
     * Test Case - functionality of deleting a category.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testDeleteCategory() throws Exception {
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
