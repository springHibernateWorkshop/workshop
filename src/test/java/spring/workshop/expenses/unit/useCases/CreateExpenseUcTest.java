package spring.workshop.expenses.unit.useCases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import spring.workshop.expenses.entities.Category;
import spring.workshop.expenses.entities.Employee;
import spring.workshop.expenses.entities.Expense;
import spring.workshop.expenses.entities.Shop;
import spring.workshop.expenses.entities.Superior;
import spring.workshop.expenses.entities.User;
import spring.workshop.expenses.enums.ExpenseStatus;
import spring.workshop.expenses.exceptions.PropertyValueException;
import spring.workshop.expenses.exceptions.ResourceNotFoundException;
import spring.workshop.expenses.services.EmployeeService;
import spring.workshop.expenses.services.ExpenseService;
import spring.workshop.expenses.useCases.CreateExpenseUc;
import spring.workshop.expenses.useCases.impl.CreateExpenseUcImpl;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class CreateExpenseUcTest {

        @Mock
        private ExpenseService expenseServiceMock;

        @Mock
        private EmployeeService employeeServiceMock;

        @InjectMocks
        private CreateExpenseUc sut = new CreateExpenseUcImpl();

        @Test
        public void testCreateExpenseUcPositive() {
                // Given
                Employee employee = new Employee(1L, "Employee", new User(), new Superior());
                Expense expense = new Expense("Expense", 100.00F, LocalDate.of(2024, 02, 16),
                                new Category(1L, "Category"),
                                new Shop(1L, "Shop"));

                when(employeeServiceMock.getEmployeeById(any(Long.class)))
                                .thenReturn(employee);
                when(expenseServiceMock.addNewExpense(any(Expense.class)))
                                .thenReturn(expense);
                // When
                Expense response = sut.createExpense(employee.getId(), expense);
                // Then
                assertEquals("Expense", response.getName());
                assertEquals(100.00F, response.getTotal());
                assertEquals(LocalDate.of(2024, 2, 16), response.getDate());
                assertEquals(1L, response.getCategory().getId());
                assertEquals(1L, response.getShop().getId());
                assertEquals(ExpenseStatus.INITIAL, response.getStatus());
                assertEquals(1L, response.getEmployee().getId());

        }

        @Test
        public void testCreateExpenseUcNegativeNonExistingEmployee() {
                // Given
                Long employeeId = 1L;
                Expense expense = new Expense("Expense", 100.00F, LocalDate.of(2024, 02, 16), new Category("Category"),
                                new Shop("Shop"));

                when(employeeServiceMock.getEmployeeById(any(Long.class)))
                                .thenThrow(new ResourceNotFoundException(
                                                "Employee with id = " + employeeId + " not found."));
                // When
                Exception exception = assertThrows(ResourceNotFoundException.class,
                                () -> sut.createExpense(employeeId, expense));
                assertEquals("Employee with id = " + employeeId + " not found.", exception.getMessage());
                // Then
                verify(employeeServiceMock).getEmployeeById(employeeId);

        }

        @Test
        public void testCreateExpenseUcNegativeMissingName() {
                // Given
                Employee employee = new Employee(1L, "Employee", new User(), new Superior());
                Expense expense = new Expense(null, 100.00F, LocalDate.of(2024, 02, 16), new Category("Category"),
                                new Shop("Shop"));

                when(employeeServiceMock.getEmployeeById(any(Long.class)))
                                .thenReturn(employee);
                when(expenseServiceMock.addNewExpense(any(Expense.class)))
                                .thenThrow(new PropertyValueException(
                                                "not-null property references a null or transient value : spring.workshop.expenses.entities.Expense.name"));
                // When
                Exception exception = assertThrows(PropertyValueException.class,
                                () -> sut.createExpense(employee.getId(), expense));
                assertEquals(
                                "not-null property references a null or transient value : spring.workshop.expenses.entities.Expense.name",
                                exception.getMessage());
                // Then
                verify(employeeServiceMock).getEmployeeById(employee.getId());
                verify(expenseServiceMock).addNewExpense(expense);

        }

}
