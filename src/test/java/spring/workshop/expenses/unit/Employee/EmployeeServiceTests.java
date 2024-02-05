package spring.workshop.expenses.unit.Employee;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import spring.workshop.expenses.entities.Employee;
import spring.workshop.expenses.entities.Superior;
import spring.workshop.expenses.entities.User;
import spring.workshop.expenses.repos.EmployeeRepository;
import spring.workshop.expenses.serviceImpl.EmployeeServiceImpl;
import spring.workshop.expenses.services.EmployeeService;

// This class contains unit tests for the UserController class

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class EmployeeServiceTests {

    private EmployeeService sut;

    @Mock
    EmployeeRepository employeeRepositoryMock;

    @BeforeEach
    public void setup() {
        sut = new EmployeeServiceImpl(employeeRepositoryMock);
    }

    @Test
    public void testAddEmployee() {
        // Arrange
        Employee employee = new Employee("Employee", new User("User"), new Superior("Superior"));
        // Given
        when(employeeRepositoryMock.save(any(Employee.class))).thenReturn(employee);
        // When
        Employee response = sut.addEmployee(employee);
        // Then
        assertEquals("Employee", response.getName());
        assertEquals("User", response.getUser().getName());
        assertEquals("Superior", response.getSuperior().getName());
    }

    @Test
    public void testDeleteEmployee() {
        // Arrange
        Employee employee = new Employee(1L, "Employee", new User(), new Superior());
        // Given
        when(employeeRepositoryMock.findById(any(Long.class)))
                .thenReturn(Optional.of(employee));
        // When
        sut.deleteEmployee(1L);
        // Then
        verify(employeeRepositoryMock).deleteById(1L);
    }

    @Test
    public void testUpdateEmployee() {
        // Arrange
        Employee employee = new Employee(1L, "Employee", new User(1L, "User"), new Superior(1L, "Superior"));
        Employee updatedEmployee = new Employee(1L, "updatedEmployee", new User(2L, "User"),
                new Superior(2L, "Superior"));
        // Given
        when(employeeRepositoryMock.findById(any(Long.class)))
                .thenReturn(Optional.of(employee));
        when(employeeRepositoryMock.save(any(Employee.class))).thenReturn(updatedEmployee);
        // When
        Employee response = sut.updateEmployee(updatedEmployee);
        // Then
        assertEquals(1L, response.getId());
        assertEquals("updatedEmployee", response.getName());
        assertEquals(2L, response.getUser().getId());
        assertEquals(2L, response.getSuperior().getId());
    }

    @Test
    public void testGetAllUsers() {
        // Given
        when(employeeRepositoryMock.findAll()).thenReturn(List.of(new Employee(), new Employee(), new Employee()));
        // When
        List<Employee> response = sut.getAllEmployees();
        // Then
        assertEquals(3, response.size());
    }

    @Test
    public void testGetEmployeeById() {
        // Arrange
        Employee employee = new Employee(1L, "Employee", new User(), new Superior());
        // Given
        when(employeeRepositoryMock.findById(any(Long.class)))
                .thenReturn(Optional.of(employee));
        // When
        Employee response = sut.getEmployeeById(1L);
        // Then
        assertEquals(1L, response.getId());
    }
}