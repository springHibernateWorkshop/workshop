package spring.workshop.expenses.unit.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import spring.workshop.expenses.entities.Employee;
import spring.workshop.expenses.entities.Superior;
import spring.workshop.expenses.entities.User;
import spring.workshop.expenses.repositories.AbstractRepositoryHelper;
import spring.workshop.expenses.repositories.EmployeeRepository;
import spring.workshop.expenses.security.Role;
import spring.workshop.expenses.services.EmployeeService;
import spring.workshop.expenses.services.impl.EmployeeServiceImpl;

// This class contains unit tests for the UserController class

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class EmployeeServiceTest {

        @Mock
        EmployeeRepository employeeRepositoryMock;

        @Mock
        AbstractRepositoryHelper<Employee> abstractRepositoryHelperMock;

        @InjectMocks
        private EmployeeService sut = new EmployeeServiceImpl();

        @Test
        public void testAddEmployee() {
                // Given
                Employee employee = new Employee("Employee", new User("username", "passw", new Role()),
                                new Superior("Superior"));

                when(abstractRepositoryHelperMock.saveAndRefresh(any(Employee.class)))
                                .thenReturn(employee);

                // When
                Employee response = sut.addEmployee(employee);
                // Then
                assertEquals("Employee", response.getName());
                assertEquals("username", response.getUser().getUsername());
                assertEquals("Superior", response.getSuperior().getName());
        }

        @Test
        public void testDeleteEmployee() {
                // Given
                Employee employee = new Employee(1L, "Employee", new User(), new Superior());

                when(employeeRepositoryMock.findById(any(Long.class)))
                                .thenReturn(Optional.of(employee));
                // When
                sut.deleteEmployee(1L);
                // Then
                verify(employeeRepositoryMock).deleteById(1L);
        }

        @Test
        public void testUpdateEmployee() {
                // Given
                Employee employee = new Employee(1L, "Employee", new User(1L, "username", "passw", new Role()),
                                new Superior(1L, "Superior"));
                Employee updatedEmployee = new Employee(1L, "updatedEmployee",
                                new User(2L, "username", "passw", new Role()),

                                new Superior(2L, "Superior"));

                when(employeeRepositoryMock.findById(any(Long.class)))
                                .thenReturn(Optional.of(employee));
                when(abstractRepositoryHelperMock.saveAndRefresh(any(Employee.class)))
                                .thenReturn(updatedEmployee);

                // When
                Employee response = sut.updateEmployee(updatedEmployee);
                // Then
                assertEquals(1L, response.getId());
                assertEquals("updatedEmployee", response.getName());
                assertEquals(2L, response.getUser().getId());
                assertEquals(2L, response.getSuperior().getId());
        }

        @Test
        public void testGetAllEmployees() {
                // Given
                when(employeeRepositoryMock.findAll())
                                .thenReturn(List.of(new Employee(), new Employee(), new Employee()));
                // When
                List<Employee> response = sut.getAllEmployees();
                // Then
                assertEquals(3, response.size());
        }

        @Test
        public void testGetEmployeeById() {
                // Given
                Employee employee = new Employee(1L, "Employee", new User(), new Superior());

                when(employeeRepositoryMock.findById(any(Long.class)))
                                .thenReturn(Optional.of(employee));
                // When
                Employee response = sut.getEmployeeById(1L);
                // Then
                assertEquals(1L, response.getId());
        }

        @Test
        public void testGetEmployeeByUser() {
                // Given
                User user = new User("User", null, new Role(1L, "ROLE_EMPLOYEE"));

                Employee employee = new Employee(1L, "Employee", user, new Superior());

                when(employeeRepositoryMock.findByUser(any(User.class)))
                                .thenReturn(Optional.of(employee));
                // When
                Employee response = sut.getEmployeeByUser(user);
                // Then
                assertEquals(1L, response.getId());
                assertEquals("User", response.getUser().getUsername());
        }

}