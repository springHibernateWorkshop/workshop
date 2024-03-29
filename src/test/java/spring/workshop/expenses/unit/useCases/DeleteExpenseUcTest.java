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
                User user = new User();
                Employee employee = new Employee(1L, "Employee_Mock", user, new Superior());
                Expense expense = new Expense(1L, "Expense", 100.00F, LocalDate.of(2024, 02, 19),
                                new Category(), new Shop(), employee, ExpenseStatus.INITIAL, null);

                when(employeeServiceMock.getEmployeeByUser(any(User.class)))
                                .thenReturn(employee);
                when(expenseServiceMock.getExpenseById(any(Long.class)))
                                .thenReturn(expense);
                // When
                sut.deleteExpense(user, expense.getId());
                // Then
                verify(employeeServiceMock).getEmployeeByUser(user);
                verify(expenseServiceMock).getExpenseById(expense.getId());
                verify(expenseServiceMock).deleteExpense(expense.getId());

        }

        @Test
        public void testDeleteExpenseUcNegativeNonExistingEmployee() {
                // Given
                User user = new User();
                Employee employee = new Employee(2L, "Employee_Mock", new User(), new Superior());
                Expense expense = new Expense(1L, "Expense", 100.00F, LocalDate.of(2024, 02, 16),
                                new Category(), new Shop(), employee, ExpenseStatus.INITIAL, null);

                when(employeeServiceMock.getEmployeeByUser(any(User.class)))
                                .thenThrow(new ResourceNotFoundException(
                                                "Employee for user with id = " + user.getId() + " not found."));
                // When
                Exception exception = assertThrows(ResourceNotFoundException.class,
                                () -> sut.deleteExpense(user, expense.getId()));
                assertEquals("Employee for user with id = " + user.getId() + " not found.", exception.getMessage());
                // Then
                verify(employeeServiceMock).getEmployeeByUser(user);

        }

        @Test
        public void testDeleteExpenseUcNegativeNonExistingExpense() {
                // Given
                User user = new User();
                Employee employee = new Employee(1L, "Employee_Mock", user, new Superior());
                Long expenseId = 1L;

                when(employeeServiceMock.getEmployeeByUser(any(User.class)))
                                .thenReturn(employee);
                when(expenseServiceMock.getExpenseById(any(Long.class)))
                                .thenThrow(new ResourceNotFoundException(
                                                "Expense with id = " + expenseId + " not found."));
                // When
                Exception exception = assertThrows(ResourceNotFoundException.class,
                                () -> sut.deleteExpense(user, expenseId));
                assertEquals("Expense with id = " + expenseId + " not found.", exception.getMessage());
                // Then
                verify(employeeServiceMock).getEmployeeByUser(user);
                verify(expenseServiceMock).getExpenseById(expenseId);

        }

        @Test
        public void testDeleteExpenseUcNegativeExpenseNotAssignedToEmployee() {
                // Given
                User user = new User();
                Employee employee = new Employee(1L, "Employee_Mock", user, new Superior());
                Expense expense = new Expense(1L, "Expense", 100.00F, LocalDate.of(2024, 02, 16),
                                new Category("Category"), new Shop("Shop"),
                                new Employee(2L, "Employee", new User(), new Superior()),
                                ExpenseStatus.INITIAL, null);

                when(employeeServiceMock.getEmployeeByUser(any(User.class)))
                                .thenReturn(employee);
                when(expenseServiceMock.getExpenseById(any(Long.class)))
                                .thenReturn(expense);
                // When
                Exception exception = assertThrows(ResourceNotFoundException.class,
                                () -> sut.deleteExpense(user, expense.getId()));
                assertEquals("No expense with given id found for this employee.", exception.getMessage());
                // Then
                verify(employeeServiceMock).getEmployeeByUser(user);
                verify(expenseServiceMock).getExpenseById(expense.getId());

        }

        @Test
        public void testDeleteExpenseUcNegativeWrongStatus() {
                // Given
                User user = new User();
                Employee employee = new Employee(1L, "Employee_Mock", user, new Superior());
                Expense expense = new Expense(1L, "Expense", 100.00F, LocalDate.of(2024, 02, 19),
                                new Category(), new Shop(), employee, ExpenseStatus.PENDING,
                                null);

                when(employeeServiceMock.getEmployeeByUser(any(User.class)))
                                .thenReturn(employee);
                when(expenseServiceMock.getExpenseById(any(Long.class)))
                                .thenReturn(expense);
                // When
                Exception exception = assertThrows(ForbiddenResourceException.class,
                                () -> sut.deleteExpense(user, expense.getId()));
                assertEquals(
                                "Expense status needs to be INITIAL or REJECTED (Current status: " + expense.getStatus()
                                                + ").",
                                exception.getMessage());
                // Then
                verify(employeeServiceMock).getEmployeeByUser(user);
                verify(expenseServiceMock).getExpenseById(expense.getId());

        }

}
