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
import spring.workshop.expenses.exceptions.ForbiddenResourceException;
import spring.workshop.expenses.exceptions.ResourceNotFoundException;
import spring.workshop.expenses.services.EmployeeService;
import spring.workshop.expenses.services.ExpenseService;
import spring.workshop.expenses.useCases.DeleteExpenseUc;
import spring.workshop.expenses.useCases.impl.DeleteExpenseUcImpl;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class DeleteExpenseUcTest {

        @Mock
        private ExpenseService expenseServiceMock;

        @Mock
        private EmployeeService employeeServiceMock;

        @InjectMocks
        private DeleteExpenseUc sut = new DeleteExpenseUcImpl();

        @Test
        public void testDeleteExpenseUcPositive() {
                // Given
                Employee employee = new Employee(1L, "Employee", new User(), new Superior());
                Expense expense = new Expense(1L, "Expense", 100.00F, LocalDate.of(2024, 02, 19),
                                new Category(), new Shop(), employee, ExpenseStatus.INITIAL, null);

                when(employeeServiceMock.getEmployeeById(any(Long.class)))
                                .thenReturn(employee);
                when(expenseServiceMock.getExpenseById(any(Long.class)))
                                .thenReturn(expense);
                // When
                sut.deleteExpense(employee.getId(), expense.getId());
                // Then
                verify(employeeServiceMock).getEmployeeById(employee.getId());
                verify(expenseServiceMock).getExpenseById(expense.getId());
                verify(expenseServiceMock).deleteExpense(expense.getId());

        }

        @Test
        public void testDeleteExpenseUcNegativeNonExistingEmployee() {
                // Given
                Long employeeId = 1L;
                Expense expense = new Expense(1L, "Expense", 100.00F, LocalDate.of(2024, 02, 16),
                                new Category("Category"),
                                new Shop("Shop"), new Employee(1L, "Employee", new User(), new Superior()),
                                ExpenseStatus.INITIAL,
                                null);

                when(employeeServiceMock.getEmployeeById(any(Long.class)))
                                .thenThrow(new ResourceNotFoundException(
                                                "Employee with id = " + employeeId + " not found."));
                // When
                Exception exception = assertThrows(ResourceNotFoundException.class,
                                () -> sut.deleteExpense(employeeId, expense.getId()));
                assertEquals("Employee with id = " + employeeId + " not found.", exception.getMessage());
                // Then
                verify(employeeServiceMock).getEmployeeById(employeeId);

        }

        @Test
        public void testDeleteExpenseUcNegativeNonExistingExpense() {
                // Given
                Employee employee = employee = new Employee(1L, "Employee", new User(), new Superior());
                Long expenseId = 1L;

                when(employeeServiceMock.getEmployeeById(any(Long.class)))
                                .thenReturn(employee);
                when(expenseServiceMock.getExpenseById(any(Long.class)))
                                .thenThrow(new ResourceNotFoundException(
                                                "Expense with id = " + expenseId + " not found."));
                // When
                Exception exception = assertThrows(ResourceNotFoundException.class,
                                () -> sut.deleteExpense(employee.getId(), expenseId));
                assertEquals("Expense with id = " + expenseId + " not found.", exception.getMessage());
                // Then
                verify(employeeServiceMock).getEmployeeById(employee.getId());
                verify(expenseServiceMock).getExpenseById(expenseId);

        }

        @Test
        public void testDeleteExpenseUcNegativeExpenseNotAssignedToEmployee() {
                // Given
                Employee employee = new Employee(1L, "Employee", new User(), new Superior());
                Expense expense = new Expense(1L, "Expense", 100.00F, LocalDate.of(2024, 02, 16),
                                new Category("Category"),
                                new Shop("Shop"), new Employee(2L, "Employee", new User(), new Superior()),
                                ExpenseStatus.INITIAL,
                                null);

                when(employeeServiceMock.getEmployeeById(any(Long.class)))
                                .thenReturn(employee);
                when(expenseServiceMock.getExpenseById(any(Long.class)))
                                .thenReturn(expense);
                // When
                Exception exception = assertThrows(ResourceNotFoundException.class,
                                () -> sut.deleteExpense(employee.getId(), expense.getId()));
                assertEquals("No expense with given id found for this employee.", exception.getMessage());
                // Then
                verify(employeeServiceMock).getEmployeeById(employee.getId());
                verify(expenseServiceMock).getExpenseById(expense.getId());

        }

        @Test
        public void testDeleteExpenseUcNegativeWrongStatus() {
                // Given
                Employee employee = new Employee(1L, "Employee", new User(), new Superior());
                Expense expense = new Expense(1L, "Expense", 100.00F, LocalDate.of(2024, 02, 19),
                                new Category(), new Shop(), employee, ExpenseStatus.PENDING, null);

                when(employeeServiceMock.getEmployeeById(any(Long.class)))
                                .thenReturn(employee);
                when(expenseServiceMock.getExpenseById(any(Long.class)))
                                .thenReturn(expense);
                // When
                Exception exception = assertThrows(ForbiddenResourceException.class,
                                () -> sut.deleteExpense(employee.getId(), expense.getId()));
                assertEquals(
                                "Expense status needs to be INITIAL or REJECTED (Current status: " + expense.getStatus()
                                                + ").",
                                exception.getMessage());
                // Then
                verify(employeeServiceMock).getEmployeeById(employee.getId());
                verify(expenseServiceMock).getExpenseById(expense.getId());

        }

}
