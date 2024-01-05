package spring.workshop.expenses.unit.Category;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;
import spring.workshop.expenses.entities.Category;
import spring.workshop.expenses.repos.CategoryRepository;

/**
 * These tests are using the MockMvc class to test the CategoryController class.
 * MockMvc is a Spring class that allows us to test the controller without
 * starting the server.
 * Unlike the integration tests, we are not testing the entire application, but
 * only the controller. This is a unit test.
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@Rollback
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
        assertEquals(3, repo.findAll().size());

        // Act
        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(3));

    }

    /**
     * Test case for getting a category by its ID.
     * 
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testGetCategoryById() throws Exception {
        // Arrange
        assertEquals(3, repo.findAll().size());

        // Act
        mockMvc.perform(get(BASE_URL + "/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Category1"));

    }

    /**
     * Test case to verify the behavior of adding a new category.
     * 
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testAddNewCategory() throws Exception {
        // Arrange
        Category category = new Category("Test Category");

        assertEquals(3, repo.findAll().size());

        // Act
        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(category)))
                .andExpect(status().isCreated());

        // Assert
        assertEquals(4, repo.findAll().size());
    }

    /**
     * Test case for updating a category.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testUpdateCategory() throws Exception {
        // Arrange
        Category category = new Category("Update Test Category");
        assertEquals("Category1", repo.findById(1).get().getName());

        // Act
        mockMvc.perform(put(BASE_URL + "/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(category)))
                .andExpect(status().isNoContent());

        // Assert
        assertEquals(category.getName(), repo.findById(1).get().getName());
    }

    /**
     * Test case to verify the functionality of deleting a category.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testDeleteCategory() throws Exception {
        // Arrange
        assertEquals(3, repo.findAll().size());

        // Act
        mockMvc.perform(delete(BASE_URL + "/{id}", 1))
                .andExpect(status().isNoContent());

        // Assert
        assertEquals(2, repo.findAll().size());
    }

}