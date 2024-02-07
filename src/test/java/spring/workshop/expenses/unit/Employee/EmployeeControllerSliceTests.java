package spring.workshop.expenses.unit.Employee;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import spring.workshop.expenses.controllers.EmployeeController;
import spring.workshop.expenses.entities.Employee;
import spring.workshop.expenses.entities.Superior;
import spring.workshop.expenses.entities.User;
import spring.workshop.expenses.services.EmployeeService;
import spring.workshop.expenses.services.SuperiorService;

// This class contains unit tests for the EmployeeController

@WebMvcTest(EmployeeController.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class EmployeeControllerSliceTests {

    private EmployeeController sut;

    @MockBean
    private EmployeeService employeeServiceMock;

    @MockBean
    private SuperiorService superiorServiceMock;

    @BeforeEach
    public void setUp() throws Exception {
        sut = new EmployeeController(employeeServiceMock, superiorServiceMock);
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    public void testReassignEmployee() {
        // Given
        Employee employee = new Employee(1L, "Employee", new User(), new Superior(1L, "Superior"));
        Superior superior = new Superior(2L, "Superior", new User(2L, "User"));
        Employee updatedEmployee = new Employee(1L, "Employee", new User(), superior);

        when(employeeServiceMock.getEmployeeById(any(Long.class)))
                .thenReturn(employee);
        when(superiorServiceMock.getSuperiorById(any(Long.class))).thenReturn(superior);
        when(employeeServiceMock.updateEmployee(any(Employee.class)))
                .thenReturn(updatedEmployee);
        // When
        ResponseEntity<Employee> response = sut.reassignEmployee(1L, 2L);
        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2L, response.getBody().getSuperior().getId());
    }

}