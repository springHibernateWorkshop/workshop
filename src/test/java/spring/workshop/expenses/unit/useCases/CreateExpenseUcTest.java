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
                User user = new User();
                Employee employee = new Employee(1L, "Employee_Mock", user, new Superior());
                Expense expense = new Expense("Expense", 100.00F, LocalDate.of(2024, 02, 16),
                                new Category(1L, "Category"), new Shop(1L, "Shop"));

                when(employeeServiceMock.getEmployeeByUser(any(User.class)))
                                .thenReturn(employee);
                when(expenseServiceMock.addNewExpense(any(Expense.class)))
                                .thenReturn(expense);
                // When
                Expense response = sut.createExpense(user, expense);
                // Then
                assertEquals("Expense", response.getName());
                assertEquals(100.00F, response.getTotal());
                assertEquals(LocalDate.of(2024, 2, 16), response.getDate());
                assertEquals(1L, response.getCategory().getId());
                assertEquals(1L, response.getShop().getId());
                assertEquals(ExpenseStatus.INITIAL, response.getStatus());
                assertEquals(employee.getId(), response.getEmployee().getId());

        }

        @Test
        public void testCreateExpenseUcNegativeMissingName() {
                // Given
                User user = new User();
                Employee employee = new Employee(1L, "Employee_Mock", user, new Superior());
                Expense expense = new Expense(null, 100.00F, LocalDate.of(2024, 02, 16), new Category("Category"),
                                new Shop("Shop"));

                when(employeeServiceMock.getEmployeeByUser(any(User.class)))
                                .thenReturn(employee);
                when(expenseServiceMock.addNewExpense(any(Expense.class)))
                                .thenThrow(new PropertyValueException(
                                                "not-null property references a null or transient value"));
                // When
                Exception exception = assertThrows(PropertyValueException.class,
                                () -> sut.createExpense(user, expense));
                assertEquals(
                                "not-null property references a null or transient value",
                                exception.getMessage());
                // Then
                verify(employeeServiceMock).getEmployeeByUser(user);
                verify(expenseServiceMock).addNewExpense(expense);

        }

}
