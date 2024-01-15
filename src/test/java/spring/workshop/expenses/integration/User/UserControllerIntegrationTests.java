package spring.workshop.expenses.integration.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import com.fasterxml.jackson.databind.ObjectMapper;

import spring.workshop.expenses.entities.User;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class UserControllerIntegrationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testAddNewUser() throws Exception {

        // Setting up request header and body for the POST request
        // Constructing the request body with the user's name to be added
        HttpHeaders requestHeader = new HttpHeaders();
        requestHeader.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        String requestBody = "name=Test1";
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, requestHeader);

        // URL for adding an user
        String url = "/user/add";

        // Send a POST request to add the user
        ResponseEntity<User> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, User.class);

        // Assert HTTP status code is OK
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // Asserting that the response body contains the new user indicating
        // successful adding
        assertEquals("Test1", response.getBody().getName());
    }

    @Test
    public void testDeleteUser() throws Exception {

        // Setting up request header and body for the DELETE request
        // Constructing the request body with the user's name to be deleted
        HttpHeaders requestHeader = new HttpHeaders();
        requestHeader.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        String requestBody = "name=Bartosz";
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, requestHeader);

        // URL for deleting an user
        String url = "/user/delete";

        // Send a DELETE request to delete the user
        ResponseEntity<Boolean> response = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, Boolean.class);

        // Assert HTTP status code is OK
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Asserting that the response body contains true indicating successful deletion
        assertEquals(true, response.getBody());
    }

    @Test
    public void testUpdateUser() throws Exception {

        // Setting up request header and body for the PUT request
        // Constructing the request body with the user to be updated
        HttpHeaders requestHeader = new HttpHeaders();
        requestHeader.setContentType(MediaType.APPLICATION_JSON);
        String requestBody = new ObjectMapper().writeValueAsString(new User(300, "Test2"));
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, requestHeader);

        String url = "/user/update";

        // Send a DELETE request to delete the user
        ResponseEntity<User> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, User.class);

        // Assert HTTP status code is OK
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Asserting that the response body contains the updated user
        assertEquals(300, response.getBody().getId());
        assertEquals("Test2", response.getBody().getName());
    }

    @Test
    public void testGetAllUsers() throws Exception {

        // URL for retrieving all users
        String url = "/user/get_all";

        // Send a GET request to retrieve all users
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        // Assert HTTP status code is OK
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verify that the response body is not null
        assertNotNull(response.getBody());
    }

    @Test
    public void testGetUserById() throws Exception {

        // URL for retrieving the user by ID
        String url = "/user/get_by_id?id=100";

        // Send a GET request to retrieve the user by ID
        ResponseEntity<User> response = restTemplate.getForEntity(url, User.class);

        // Assert HTTP status code is OK
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Asserting that the response body contains the user
        assertEquals(100, response.getBody().getId());
    }

    @Test
    public void testGetUserByName() throws Exception {

        // URL for retrieving the user by name
        String url = "/user/get_by_name?name=Victoria";

        // Send a GET request to retrieve the user by name
        ResponseEntity<User> response = restTemplate.getForEntity(url, User.class);

        // Assert HTTP status code is OK
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Asserting that the response body contains the user
        assertEquals("Victoria", response.getBody().getName());
    }
}