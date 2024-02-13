package spring.workshop.expenses.integration.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.net.URI;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import spring.workshop.expenses.entities.User;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class UserControllerIntegrationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String BASE_URL = "/users";

    @Test
    public void testAddNewUser() throws Exception {

        User createdUser = new User("newUsername", "passw", 2L);
        URI newUserLocation = restTemplate.postForLocation(BASE_URL, createdUser);
        ResponseEntity<User> response = restTemplate.getForEntity(newUserLocation, User.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        User user = response.getBody();
        assertEquals("newUsername", user.getUsername());
    }

    @Test
    public void testDeleteUser() {
        ResponseEntity<User> response = restTemplate.getForEntity(BASE_URL + "/{id}", User.class, 100L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        restTemplate.delete(BASE_URL + "/{id}", 100L);

        ResponseEntity<User> responseAfterDelete = restTemplate.getForEntity(BASE_URL + "/{id}", User.class, 100L);
        assertEquals(HttpStatus.BAD_REQUEST, responseAfterDelete.getStatusCode());
    }

    @Test
    public void testUpdateUser() throws Exception {
        User user = new User(100L, "username", "pass", 3L);
        User response = restTemplate.getForObject(BASE_URL + "/{id}", User.class, 100L);
        assertEquals("usr1", response.getUsername());
        assertEquals("pass1", response.getPassword());
        assertEquals(2, response.getRole());

        restTemplate.put("/users", user);
        User responseAfterUpdate = restTemplate.getForObject(BASE_URL + "/{id}", User.class, 100L);
        assertEquals("username", responseAfterUpdate.getUsername());
        assertEquals("pass", responseAfterUpdate.getPassword());
        assertEquals(3, responseAfterUpdate.getRole());
    }

    @Test
    public void testGetAllUsers() throws Exception {
        // Send a GET request to retrieve all users
        ResponseEntity<String> response = restTemplate.getForEntity(BASE_URL, String.class);

        // Assert HTTP status code is OK
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verify that the response body is not null
        assertNotNull(response.getBody());
    }

    @Test
    public void testGetUserById() throws Exception {

        // Send a GET request to retrieve the user by ID
        ResponseEntity<User> response = restTemplate.getForEntity(BASE_URL + "/{id}", User.class, 100);

        // Assert HTTP status code is OK
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Asserting that the response body contains the user
        assertEquals(100, response.getBody().getId());
    }

    @Test
    public void testGetUserByIdNegative() throws Exception {

        // Send a GET request to retrieve the user by ID, which does not exist
        ResponseEntity<User> response = restTemplate.getForEntity(BASE_URL + "/{id}", User.class, 1000);

        // Assert HTTP status code is OK
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}