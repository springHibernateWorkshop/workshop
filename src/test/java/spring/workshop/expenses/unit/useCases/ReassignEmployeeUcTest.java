package spring.workshop.expenses.unit.useCases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import spring.workshop.expenses.entities.Employee;
import spring.workshop.expenses.entities.Superior;
import spring.workshop.expenses.entities.User;
import spring.workshop.expenses.exceptions.ForbiddenResourceException;
import spring.workshop.expenses.exceptions.ResourceNotFoundException;
import spring.workshop.expenses.services.EmployeeService;
import spring.workshop.expenses.services.SuperiorService;
import spring.workshop.expenses.useCases.ReassignEmployeeUc;
import spring.workshop.expenses.useCases.impl.ReassignEmployeeUcImpl;

// This class contains unit tests for the ReassignEmployeeUc

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class ReassignEmployeeUcTest {

    @Mock
    private EmployeeService employeeServiceMock;

    @Mock
    private SuperiorService superiorServiceMock;

    @InjectMocks
    private ReassignEmployeeUc sut = new ReassignEmployeeUcImpl();

    @Test
    public void testReassignEmployeePositive() {
        // Given
        Superior superior = new Superior(2L, "Superior", new User(2L, "User"));
        Employee employee = new Employee(1L, "Employee", new User(), new Superior(1L, "Superior"));
        Employee reassignedEmployee = new Employee(employee.getId(), employee.getName(), employee.getUser(), superior);

        when(superiorServiceMock.getSuperiorById(any(Long.class))).thenReturn(superior);
        when(employeeServiceMock.getEmployeeById(any(Long.class)))
                .thenReturn(employee);
        when(employeeServiceMock.updateEmployee(any(Employee.class)))
                .thenReturn(reassignedEmployee);
        // When
        Employee response = sut.reassignEmployee(employee.getId(), superior.getId());
        // Then
        verify(superiorServiceMock).getSuperiorById(superior.getId());
        verify(employeeServiceMock).getEmployeeById(employee.getId());
        verify(employeeServiceMock).updateEmployee(employee);
        assertEquals(superior, response.getSuperior());
    }

    @Test
    public void testReassignEmployeeNegativeNonExistingSuperior() {
        // Given
        Long superiorId = 2L;
        Employee employee = new Employee(1L, "Employee", new User(), new Superior(1L, "Superior"));

        when(superiorServiceMock.getSuperiorById(any(Long.class)))
                .thenThrow(new ResourceNotFoundException("No Superior with id: " + superiorId));
        // When
        Exception exception = assertThrows(ResourceNotFoundException.class,
                () -> sut.reassignEmployee(employee.getId(), superiorId));
        assertEquals("No Superior with id: " + superiorId, exception.getMessage());
        // Then
        verify(superiorServiceMock).getSuperiorById(superiorId);
    }

    @Test
    public void testReassignEmployeeNegativeInactivegSuperior() {
        // Given
        Superior superior = new Superior(2L, "Superior");
        Employee employee = new Employee(1L, "Employee", new User(), new Superior(1L, "Superior"));

        when(superiorServiceMock.getSuperiorById(any(Long.class))).thenReturn(superior);
        // When
        Exception exception = assertThrows(ForbiddenResourceException.class,
                () -> sut.reassignEmployee(employee.getId(), superior.getId()));
        assertEquals("User for Superior with id = " + superior.getId() + "does not exist.", exception.getMessage());
        // Then
        verify(superiorServiceMock).getSuperiorById(superior.getId());
    }

    @Test
    public void testReassignEmployeeNegativeNonExistingEmployee() {
        // Given
        Superior superior = new Superior(2L, "Superior", new User(2L, "User"));
        Long employeeId = 1L;

        when(superiorServiceMock.getSuperiorById(any(Long.class))).thenReturn(superior);
        when(employeeServiceMock.getEmployeeById(any(Long.class)))
                .thenThrow(new ResourceNotFoundException("Employee with id = " + employeeId + " not found."));
        // When
        Exception exception = assertThrows(ResourceNotFoundException.class,
                () -> sut.reassignEmployee(employeeId, superior.getId()));
        assertEquals("Employee with id = " + employeeId + " not found.", exception.getMessage());
        // Then
        verify(superiorServiceMock).getSuperiorById(superior.getId());
        verify(employeeServiceMock).getEmployeeById(employeeId);
    }

}