package spring.workshop.expenses.integration.Category;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.net.URI;
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

    /**
     * Test case to verify the positive scenario of adding a category.
     */
    @Test
    public void testAddCategoryPositive() {
        URI newCategoryLocation = restTemplate.postForLocation(BASE_URL, new Category("Category4"));
        ResponseEntity<Category> response = restTemplate.getForEntity(newCategoryLocation, Category.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Category category = response.getBody();
        assertEquals("Category4", category.getName());
    }

    /**
     * Test case to verify the negative scenario of adding a category.
     * It sends a POST request to the "/categories" endpoint with a category that
     * already exists.
     * Expects a response with HTTP status code 400 (BAD_REQUEST) because there is
     * an exception handler for this case.
     */
    @Test
    public void testAddCategoryNegative() {
        restTemplate.postForEntity(BASE_URL, new Category(4L, "Category5"),
                Category.class);
        ResponseEntity<Category> response = restTemplate.postForEntity(BASE_URL, new Category(4L, "Category5"),
                Category.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    /**
     * Test case to verify the positive scenario of deleting a category.
     */
    @Test
    public void testDeleteCategoryPositive() {
        ResponseEntity<Category> response = restTemplate.getForEntity(BASE_URL + "/{id}", Category.class, 300L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        restTemplate.delete("/categories/{id}", 300L);
        ResponseEntity<Category> responseAfterDelete = restTemplate.getForEntity(BASE_URL + "/{id}", Category.class,
                300L);
        assertEquals(HttpStatus.BAD_REQUEST, responseAfterDelete.getStatusCode());
    }

    /**
     * Test case to verify the negative scenario of deleting a category.
     * It sends a GET request to retrieve a category with the specified ID,
     * and expects a NOT_FOUND status code in the response.
     * Then, it sends a DELETE request to delete the category with the same ID,
     * and again expects a NOT_FOUND status code in the response.
     */
    @Test
    public void testDeleteCategoryNegative() {
        ResponseEntity<Category> response = restTemplate.getForEntity(BASE_URL + "/{id}", Category.class, 6);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ResponseEntity<Boolean> responseAfterDelete = restTemplate.exchange(BASE_URL + "/{id}", HttpMethod.DELETE,
                null, Boolean.class, 6);
        assertEquals(Boolean.FALSE, responseAfterDelete.getBody());
    }

    @Test
    public void testUpdateCategoryPositive() {
        Category category = new Category(100L, "Category8");
        Category response = restTemplate.getForObject(BASE_URL + "/{id}", Category.class, 100L);
        assertEquals("Category1", response.getName());
        restTemplate.put("/categories", category);
        Category responseAfterUpdate = restTemplate.getForObject(BASE_URL + "/{id}", Category.class, 100);
        assertEquals("Category8", responseAfterUpdate.getName());
    }

    @Test
    public void testUpdateCategoryNegative() {
        Category category = new Category(3L, "Category9");
        ResponseEntity<Category> response = restTemplate.exchange(BASE_URL, HttpMethod.PUT,
                new HttpEntity<>(category), Category.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
