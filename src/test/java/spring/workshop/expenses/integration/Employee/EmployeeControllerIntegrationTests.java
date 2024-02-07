package spring.workshop.expenses.integration.Employee;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import spring.workshop.expenses.entities.Employee;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class EmployeeControllerIntegrationTests {

    private static final String BASE_URL = "/employees/";

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testReassignEmployee() throws Exception {

        // Setting up request header and body for the PUT request
        // Constructing the request body with the employee_id and superior_id for
        // reassigning
        HttpHeaders requestHeader = new HttpHeaders();
        requestHeader.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        Long employeeId = 100L;
        Long superiorIdNew = 200L;
        String requestBody = "superior_id=" + superiorIdNew;
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, requestHeader);

        // URL for reassigning the employee
        String url = BASE_URL + employeeId;

        // Send a PUT request to reassign the employee
        ResponseEntity<Employee> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Employee.class);

        // Assert HTTP status code is OK
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Asserting that the response body contains the employee with updated superior
        // indicating successful reassigning
        assertEquals(200L, response.getBody().getSuperior().getId());
    }

}