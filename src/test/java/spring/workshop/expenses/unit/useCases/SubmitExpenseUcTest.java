package spring.workshop.expenses.unit.useCases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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
import spring.workshop.expenses.security.Role;
import spring.workshop.expenses.services.EmployeeService;
import spring.workshop.expenses.services.ExpenseService;
import spring.workshop.expenses.useCases.impl.SubmitExpenseUcImpl;

// Test class for checking submit expense UC

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class SubmitExpenseUcTest {

        @Mock
        ExpenseService expenseServiceMock;

        @Mock
        EmployeeService employeeServiceMock;

        @InjectMocks
        SubmitExpenseUcImpl submitExpenseUc;

        @Test
        public void submitExpenseTest() {
                // Given
                Employee employee = new Employee(200L, "Employee", new User(100L, "Victoria", "password", new Role(1L)),
                                new Superior());
                Expense expense = new Expense(100L, "Expense", 100.00F, LocalDate.of(2024, 02, 16),
                                new Category(1L, "Category"), new Shop(1L, "Shop"),
                                employee, ExpenseStatus.INITIAL,
                                "Expense for submit");

                when(expenseServiceMock.getExpenseById(any())).thenReturn(expense);
                when(employeeServiceMock.getEmployeeByUser(any())).thenReturn(employee);

                // When
                Expense submittedExpense = submitExpenseUc.submitExpense(100L, employee.getUser());

                // Then
                assertEquals(ExpenseStatus.PENDING, submittedExpense.getStatus());
        }

        @Test
        public void submitExpenseNegativExpenseStatusIncorrectTest() {
                // Given
                Employee employee = new Employee(200L, "Employee", new User(100L, "Victoria", "password", new Role(1L)),
                                new Superior());
                Expense expense = new Expense(100L, "Expense", 100.00F, LocalDate.of(2024, 02, 16),
                                new Category(1L, "Category"), new Shop(1L, "Shop"),
                                employee, ExpenseStatus.PENDING,
                                "Pendig expense for submit");

                when(expenseServiceMock.getExpenseById(any())).thenReturn(expense);
                when(employeeServiceMock.getEmployeeByUser(any())).thenReturn(employee);

                // When
                // Then
                Exception exception = assertThrows(ForbiddenResourceException.class,
                                () -> submitExpenseUc.submitExpense(100L, employee.getUser()));
                assertEquals("Expense should have 'Initial' status", exception.getMessage());
        }

        @Test
        public void submitExpenseNegativEmployeeIdIncorrectTest() {
                // Given
                Employee employee = new Employee(200L, "Employee", new User(100L, "Victoria", "password", new Role(1L)),
                                new Superior());
                Employee notAuthorizedEmployee = new Employee(300L, "Employee", new User(), new Superior());
                Expense expense = new Expense(100L, "Expense", 100.00F, LocalDate.of(2024, 02, 16),
                                new Category(1L, "Category"), new Shop(1L, "Shop"),
                                employee, ExpenseStatus.INITIAL,
                                "Expense for submit by incorrect employee");

                when(expenseServiceMock.getExpenseById(any())).thenReturn(expense);
                when(employeeServiceMock.getEmployeeByUser(any())).thenReturn(notAuthorizedEmployee);

                // When
                // Then
                Exception exception = assertThrows(ForbiddenResourceException.class,
                                () -> submitExpenseUc.submitExpense(100L, employee.getUser()));
                assertEquals("Employee with ID = 300 is not authorized to submit this expense", exception.getMessage());
        }

}
