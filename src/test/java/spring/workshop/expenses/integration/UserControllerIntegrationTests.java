package spring.workshop.expenses.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;

import jakarta.transaction.Transactional;
import spring.workshop.expenses.entities.User;
import spring.workshop.expenses.repos.UserRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@Rollback
public class UserControllerIntegrationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testAddNewUser() throws Exception {

        // Setting up request header and body for the POST request
        // Constructing the request body with the user's name to be added
        HttpHeaders requestHeader = new HttpHeaders();
        requestHeader.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        String requestBody = "name=Test";
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, requestHeader);

        // URL for adding an user
        String url = "http://localhost:" + port + "/user/add";
        
        // Send a POST request to add the user
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        
        // Assert HTTP status code is OK
        assertEquals(HttpStatus.OK, response.getStatusCode());
        
        // Asserting that the response body contains "Saved" indicating successful adding
        assertEquals("Saved", response.getBody());
    }

    @Test
    public void testDeleteUser() throws Exception {
        
        // Create and save a user using the userRepository
        User user = new User("Test");
        userRepository.save(user);
        
        // Setting up request header and body for the DELETE request
        // Constructing the request body with the user's name to be deleted
        HttpHeaders requestHeader = new HttpHeaders();
        requestHeader.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        String requestBody = "name=Test";
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, requestHeader);

        // URL for deleting an user
        String url = "http://localhost:" + port + "/user/delete";

        // Send a DELETE request to delete the user
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, String.class);
        
        // Assert HTTP status code is OK
        assertEquals(HttpStatus.OK, response.getStatusCode());
        
        // Asserting that the response body contains "Deleted" indicating successful deletion
        assertEquals("Deleted", response.getBody());
    }

    @Test
    public void testGetAllUsers() throws Exception {

        // Create and save a user using the userRepository
        User user = new User("Test");
        userRepository.save(user);

        // URL for retrieving all users
        String url = "http://localhost:" + port + "/user/all";
        
        // Send a GET request to retrieve all users
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        
        // Assert HTTP status code is OK
        assertEquals(HttpStatus.OK, response.getStatusCode());
        
        // Convert response body to a JSONArray and validate its length
        // Assuming the response body is in JSON format representing an array of users
        assertEquals(1, new JSONArray(response.getBody()).length());
    }
}