package spring.workshop.expenses.integration.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import spring.workshop.expenses.entities.Superior;
import spring.workshop.expenses.entities.User;
import spring.workshop.expenses.security.Role;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class UserControllerIntegrationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String BASE_URL = "/users";

    @Test
    public void testAddNewSuperior() throws Exception {
        User user = new User("superior", "pass", new Role(2L));
        ResponseEntity<Superior> response = restTemplate.postForEntity(BASE_URL + "?name={name}", user, Superior.class,
                "Kowalski",
                null);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        Superior createdSuperior = response.getBody();
        assertEquals("superior", createdSuperior.getUser().getUsername());
    }

    @Test
    public void testDeleteUser() {
        ResponseEntity<User> response = restTemplate.getForEntity(BASE_URL + "/{id}", User.class, 500L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ResponseEntity<Void> deleteResponse = restTemplate.exchange(BASE_URL + "/{id}", HttpMethod.DELETE, null,
                Void.class, 500L);
        assertEquals(HttpStatus.OK, deleteResponse.getStatusCode());

        ResponseEntity<User> responseAfterDelete = restTemplate.getForEntity(BASE_URL
                + "/{id}", User.class, 500L);
        assertEquals(HttpStatus.NOT_FOUND, responseAfterDelete.getStatusCode());
    }

    @Test
    public void testUpdateUser() throws Exception {
        User user = new User(100L, "username", "pass", new Role(1L, "ROLE_EMPLOYEE"));
        User response = restTemplate.getForObject(BASE_URL + "/{id}", User.class, 100L);
        assertEquals("victoria", response.getUsername());
        assertEquals("ROLE_EMPLOYEE", response.getRole().getAuthority());

        restTemplate.put("/users", user);
        User responseAfterUpdate = restTemplate.getForObject(BASE_URL + "/{id}", User.class, 100L);
        assertEquals("username", responseAfterUpdate.getUsername());
        assertEquals("pass", responseAfterUpdate.getPassword());
        assertEquals("ROLE_EMPLOYEE", responseAfterUpdate.getRole().getAuthority());
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
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}