package spring.workshop.expenses.integration.Category;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import spring.workshop.expenses.controllers.CategoryController;
import spring.workshop.expenses.entities.Category;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class CategoryControllerIntegrationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    CategoryController controller;
    private static final String BASE_URL = "/categories";

    /**
     * Test case to verify the functionality of getting all categories.
     */
    @Test
    public void testGetAllCategories() {
        ResponseEntity<List> response = restTemplate.getForEntity(BASE_URL, List.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    /**
     * Test case to verify the positive scenario of getting a category by its ID.
     */
    @Test
    public void testGetCategoryByIdPositive() {
        ResponseEntity<Category> response = restTemplate.getForEntity(BASE_URL + "/{id}", Category.class, 200);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Category category = response.getBody();
        Assertions.assertNotNull(category);
        assertEquals("Category2", category.getName());
    }

    /**
     * Test case to verify the behavior of getting a category by ID when the
     * category does not exist.
     */
    @Test
    public void testGetCategoryByIdNegative() {
        ResponseEntity<Category> response = restTemplate.getForEntity(BASE_URL + "/{id}", Category.class, 10);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

}
