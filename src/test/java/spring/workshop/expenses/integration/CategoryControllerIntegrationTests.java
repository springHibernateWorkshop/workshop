package spring.workshop.expenses.integration;

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
import org.springframework.test.context.ActiveProfiles;

import spring.workshop.expenses.entities.Category;
import spring.workshop.expenses.rest.CategoryController;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
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
        ResponseEntity<Category> response = restTemplate.getForEntity(BASE_URL + "/{id}", Category.class, 2);
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
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * Test case to verify the positive scenario of adding a category.
     */
    @Test
    public void testAddCategoryPositive() {
        Category category = new Category(4, "Category4");
        URI newCategoryLocation = restTemplate.postForLocation(BASE_URL, category);
        ResponseEntity<Category> response = restTemplate.getForEntity(newCategoryLocation, Category.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        category = response.getBody();
        assertEquals(category.getName(), "Category4");
    }

    /**
     * Test case to verify the negative scenario of adding a category.
     * It sends a POST request to the "/categories" endpoint with a category that
     * already exists.
     * Expects a response with HTTP status code 409 (CONFLICT) because there is an
     * exception handler for this case.
     */
    @Test
    public void testAddCategoryNegative() {
        restTemplate.postForEntity(BASE_URL, new Category(4, "Category5"),
                Category.class);
        ResponseEntity<Category> response = restTemplate.postForEntity(BASE_URL, new Category(4, "Category5"),
                Category.class);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    /**
     * Test case to verify the positive scenario of deleting a category.
     */
    @Test
    public void testDeleteCategoryPositive() {
        ResponseEntity<Category> response = restTemplate.getForEntity(BASE_URL+"/{id}", Category.class, 1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        restTemplate.delete("/categories/{id}", 1);
        ResponseEntity<Category> responseAfterDelete = restTemplate.getForEntity(BASE_URL+"/{id}", Category.class, 1);
        assertEquals(HttpStatus.NOT_FOUND, responseAfterDelete.getStatusCode());
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
        ResponseEntity<Category> response = restTemplate.getForEntity(BASE_URL+"/{id}", Category.class, 6);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        ResponseEntity<Void> responseAfterDelete = restTemplate.exchange(BASE_URL+"/{id}", HttpMethod.DELETE,
                null, Void.class, 6);
        assertEquals(HttpStatus.NOT_FOUND, responseAfterDelete.getStatusCode());
    }

    @Test
    public void testUpdateCategoryPositive() {
        Category category = new Category(3, "Category8");
        Category response = restTemplate.getForObject(BASE_URL+"/{id}", Category.class, 3);
        assertEquals("Category3", response.getName());
        restTemplate.put("/categories/{id}", category, 3);
        Category responseAfterUpdate = restTemplate.getForObject(BASE_URL+"/{id}", Category.class, 3);
        assertEquals("Category8", responseAfterUpdate.getName());
    }

    @Test
    public void testUpdateCategoryNegative() {
        Category category = new Category(3, "Category9");
        ResponseEntity<Category> response = restTemplate.exchange(BASE_URL+"/{id}", HttpMethod.PUT,
                new HttpEntity<>(category), Category.class, 9);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
