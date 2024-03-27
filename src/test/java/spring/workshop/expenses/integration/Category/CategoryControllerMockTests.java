package spring.workshop.expenses.integration.Category;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import jakarta.transaction.Transactional;
import spring.workshop.expenses.repositories.CategoryRepository;

/**
 * These tests are using the MockMvc class to test the CategoryController class.
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
public class CategoryControllerMockTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoryRepository repo;

    private final String BASE_URL = "/categories";

    /**
     * Test case to verify the functionality of retrieving all categories.
     * 
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void TestGetAllCategories() throws Exception {
        // Arrange
        assertEquals(4, repo.findAll().size());

        // Act & Assert
        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(4));

    }

    /**
     * Test case for getting a category by its ID.
     * 
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testGetCategoryById() throws Exception {
        // Arrange

        // Act & Assert
        mockMvc.perform(get(BASE_URL + "/{id}", 100))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Category1"));

    }

}