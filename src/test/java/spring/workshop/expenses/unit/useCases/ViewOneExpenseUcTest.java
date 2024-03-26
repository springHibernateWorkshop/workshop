package spring.workshop.expenses.unit.useCases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.ZoneId;

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
import spring.workshop.expenses.exceptions.ResourceNotFoundException;
import spring.workshop.expenses.security.Role;
import spring.workshop.expenses.services.EmployeeService;
import spring.workshop.expenses.services.ExpenseService;
import spring.workshop.expenses.services.SuperiorService;
import spring.workshop.expenses.useCases.ViewOneExpenseUc;
import spring.workshop.expenses.useCases.impl.ViewOneExpenseUcImpl;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class ViewOneExpenseUcTest {

    @Mock
    private SuperiorService superiorServiceMock;

    @Mock
    private EmployeeService employeeServiceMock;

    @Mock
    private ExpenseService expenseServiceMock;

    @InjectMocks
    private ViewOneExpenseUc sut = new ViewOneExpenseUcImpl();

    @Test
    public void testViewOneExpenseUcPositiveForEmployee() {
        // Given
        Role roleEmployee = new Role(1L);
        roleEmployee.setName(Role.ROLE_EMPLOYEE);

        User user = new User(100L, "victoria", "password", roleEmployee);
        Superior superior = new Superior(100L, "superior_1", user);
        Employee userEmployee = new Employee(100L, "Victoria", user, superior);
        Employee expenseEmployee = new Employee(100L, "Victoria", user, superior);

        Expense expense = new Expense();
        expense.setId(400L);
        expense.setName("expense_4");
        expense.setTotal(13.0F);
        expense.setDate(LocalDate.now(ZoneId.systemDefault()));
        expense.setCategory(new Category());
        expense.setShop(new Shop());
        expense.setEmployee(expenseEmployee);
        expense.setStatus(ExpenseStatus.PENDING);
        expense.setNote("Note 4");

        when(expenseServiceMock.getExpenseById(any(Long.class))).thenReturn(expense);
        when(employeeServiceMock.getEmployeeByUser(any(User.class))).thenReturn(userEmployee);

        // When
        Expense expenseSut = sut.viewOneExpense(user, expense.getId());

        // Then
        assertEquals(expense.getName(), expenseSut.getName());
        assertEquals(expense.getTotal(), expenseSut.getTotal());
        assertEquals(expense.getStatus(), expenseSut.getStatus());
        assertEquals(expense.getNote(), expenseSut.getNote());
    }

    @Test
    public void testViewOneExpenseUcPositiveForSuperior() {
        // Given
        Role roleSuperior = new Role(2L);
        roleSuperior.setName(Role.ROLE_SUPERIOR);

        User user = new User(100L, "victoria", "password", roleSuperior);
        Superior userSuperior = new Superior(123L, "Thomas", user);
        Superior expenseSuperior = new Superior(123L, "Thomas", user);
        Employee expenseEmployee = new Employee(100L, "Victoria", user, expenseSuperior);

        Expense expense = new Expense();
        expense.setId(400L);
        expense.setName("expense_4");
        expense.setTotal(13.0F);
        expense.setDate(LocalDate.now(ZoneId.systemDefault()));
        expense.setCategory(new Category());
        expense.setShop(new Shop());
        expense.setEmployee(expenseEmployee);
        expense.setStatus(ExpenseStatus.PENDING);
        expense.setNote("Note 4");

        when(expenseServiceMock.getExpenseById(any(Long.class))).thenReturn(expense);
        when(superiorServiceMock.getSuperiorByUser(any(User.class))).thenReturn(userSuperior);

        // When
        Expense expenseSut = sut.viewOneExpense(user, expense.getId());

        // Then
        assertEquals(expense.getName(), expenseSut.getName());
        assertEquals(expense.getTotal(), expenseSut.getTotal());
        assertEquals(expense.getStatus(), expenseSut.getStatus());
        assertEquals(expense.getNote(), expenseSut.getNote());
    }

    @Test
    public void testViewOneExpenseUcNegaiveExpsenseDontExists() {
        // Given
        Long expenseId = 123L;
        when(expenseServiceMock.getExpenseById(any(Long.class)))
                .thenThrow(new ResourceNotFoundException(
                        "Expense with id = " + expenseId + " not found."));

        // When
        Exception exception = assertThrows(ResourceNotFoundException.class,
                () -> sut.viewOneExpense(new User(), expenseId));
        assertEquals("Expense with id = " + expenseId + " not found.", exception.getMessage());

        // Then
        verify(expenseServiceMock, times(1)).getExpenseById(expenseId);
    }

    @Test
    public void testViewOneExpenseUcNegativeEmployee() {
        // Given
        Role roleEmployee = new Role(1L);
        roleEmployee.setName(Role.ROLE_EMPLOYEE);

        User user = new User();
        user.setRole(roleEmployee);

        Employee userEmployee = new Employee(111L, "Victoria", user, new Superior());
        Employee expenseEmployee = new Employee(222L, "Victoria", user, new Superior());

        Expense expense = new Expense();
        expense.setId(456L);
        expense.setEmployee(expenseEmployee);

        when(expenseServiceMock.getExpenseById(any(Long.class))).thenReturn(expense);
        when(employeeServiceMock.getEmployeeByUser(any(User.class))).thenReturn(userEmployee);

        // When
        Exception exception = assertThrows(ResourceNotFoundException.class,
                () -> sut.viewOneExpense(user, expense.getId()));

        // Then
        assertEquals("No expense with given id found for this employee.", exception.getMessage());
    }

    @Test
    public void testViewOneExpenseUcNegativeSuperior() {
        // Given
        Role roleEmployee = new Role(1L);
        roleEmployee.setName(Role.ROLE_SUPERIOR);

        User user = new User();
        user.setRole(roleEmployee);

        Superior userSuperior = new Superior(111L, null, user);
        Superior expenseSuperior = new Superior(222L, null, user);

        Employee expenseEmployee = new Employee(100L, "Victoria", user, expenseSuperior);

        Expense expense = new Expense();
        expense.setId(456L);
        expense.setEmployee(expenseEmployee);

        when(expenseServiceMock.getExpenseById(any(Long.class))).thenReturn(expense);
        when(superiorServiceMock.getSuperiorByUser(any(User.class))).thenReturn(userSuperior);

        // When
        Exception exception = assertThrows(ResourceNotFoundException.class,
                () -> sut.viewOneExpense(user, expense.getId()));

        // Then
        assertEquals("No expense with given id found for this superior.", exception.getMessage());
    }
}
