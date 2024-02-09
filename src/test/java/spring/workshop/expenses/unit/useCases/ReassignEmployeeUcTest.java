package spring.workshop.expenses.unit.useCases;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import spring.workshop.expenses.services.EmployeeService;
import spring.workshop.expenses.services.SuperiorService;
import spring.workshop.expenses.useCases.ReassignEmployeeUc;
import spring.workshop.expenses.useCases.impl.ReassignEmployeeUcImpl;

// This class contains unit tests for the EmployeeController

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class ReassignEmployeeUcTest {

    @InjectMocks
    private ReassignEmployeeUc sut = new ReassignEmployeeUcImpl();

    @Mock
    private EmployeeService employeeServiceMock;

    @Mock
    private SuperiorService superiorServiceMock;

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
        Employee response = sut.reassignEmployee(1L, 2L);
        // Then
        verify(employeeServiceMock).getEmployeeById(1L);
        verify(superiorServiceMock).getSuperiorById(2L);
        verify(employeeServiceMock).updateEmployee(employee);
        assertEquals(superior, response.getSuperior());
    }

}