package spring.workshop.expenses.unit.useCases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
import spring.workshop.expenses.exceptions.ResourceNotFoundException;
import spring.workshop.expenses.services.EmployeeService;
import spring.workshop.expenses.services.ExpenseService;
import spring.workshop.expenses.services.SuperiorService;
import spring.workshop.expenses.useCases.ApproveOrRejectExpenseUc;
import spring.workshop.expenses.useCases.impl.ApproveOrRejectExpenseUcImp;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class ApproveOrRejectExpenseUcTest {

    @InjectMocks
    private ApproveOrRejectExpenseUc sut = new ApproveOrRejectExpenseUcImp();

    @Mock
    private SuperiorService superiorServiceMock;

    @Mock
    private ExpenseService expenseServiceMock;

    @Mock
    private EmployeeService employeeServiceMock;

    @Test
    public void approveExpenseTest() {
        Superior superior = new Superior(2L, "Superior", new User("Test Superior", "test pass", "SUPERIOR"));
        Expense expense = new Expense(999L, "Test Expense", 999.99f, LocalDate.of(1994, 10, 1), new Category(100L),
                new Shop(100L),
                new Employee(100L, "Test", new User("Test user", "test_pass", "EMPLOYEE"), superior),
                ExpenseStatus.PENDING,
                "example_test_note_1");

        Employee employee = expense.getEmployee();

        when(expenseServiceMock.getExpenseById(any())).thenReturn(expense);
        when(employeeServiceMock.getEmployeeById(any())).thenReturn(employee);
        when(superiorServiceMock.getSuperiorById(any())).thenReturn(superior);
        when(expenseServiceMock.updateExpense(any())).thenReturn(expense);

        Expense approvedExpense = sut.approveOrRejectExpense(expense.getId(), superior.getId(), ExpenseStatus.APPROVED,
                "example_approve_note_1");

        assertNotNull(approvedExpense);
        assertEquals(ExpenseStatus.APPROVED, approvedExpense.getStatus());
        assertEquals("example_approve_note_1", approvedExpense.getNote());

    }

    @Test
    public void rejectExpenseTest() {
        Superior superior = new Superior(2L, "Superior", new User("Test Superior", "test pass", "SUPERIOR"));
        Expense expense = new Expense(999L, "Test_Expense", 999.99f, LocalDate.of(1994, 10, 1), new Category(100L),
                new Shop(100L),
                new Employee(100L, "Test", new User("Test user", "test_pass", "EMPLOYEE"), superior),
                ExpenseStatus.PENDING,
                "example_test_note_1");

        Employee employee = expense.getEmployee();

        when(expenseServiceMock.getExpenseById(any())).thenReturn(expense);
        when(employeeServiceMock.getEmployeeById(any())).thenReturn(employee);
        when(superiorServiceMock.getSuperiorById(any())).thenReturn(superior);
        when(expenseServiceMock.updateExpense(any())).thenReturn(expense);

        Expense approvedExpense = sut.approveOrRejectExpense(expense.getId(), superior.getId(), ExpenseStatus.REJECTED,
                "example_reject_note_1");

        assertNotNull(approvedExpense);
        assertEquals(ExpenseStatus.REJECTED, approvedExpense.getStatus());
        assertEquals("example_reject_note_1", approvedExpense.getNote());
    }

    @Test
    public void acceptExpenseNichtRichtigerSuperiorTest() {
        Superior superior = new Superior(2L, "Superior", new User("Test Superior", "test pass", "SUPERIOR"));

        Expense expense = new Expense(999L, "Expense Negative Test", 999.99f, LocalDate.of(1994, 10, 1),
                new Category(100L),
                new Shop(100L),
                new Employee(100L, "Test", new User("Test user", "test_pass", "EMPLOYEE"), superior),
                ExpenseStatus.PENDING,
                "example_test_note_1");

        Employee employee = expense.getEmployee();

        when(expenseServiceMock.getExpenseById(any())).thenReturn(expense);
        when(employeeServiceMock.getEmployeeById(any())).thenReturn(employee);
        when(superiorServiceMock.getSuperiorById(any())).thenReturn(superior);
        when(expenseServiceMock.updateExpense(any())).thenReturn(expense);

        Exception exception = assertThrows(ResourceNotFoundException.class,
                () -> sut.approveOrRejectExpense(expense.getId(), 3L, ExpenseStatus.REJECTED, "example_reject_note_1"));
        assertEquals("Superior cannot approve this Expense", exception.getMessage());
    }

    @Test
    public void approveExpenseNichtRichtigerParameterStatusTest(){
        Superior superior=new Superior(2L, "Superior", new User("Test Superior", "test pass", "SUPERIOR"));
        Expense expense=new Expense(999L,  "Test Expense", 999.99f, LocalDate.of(1994, 10, 1), new Category(100L),
                                new Shop(100L),
                                new Employee(100L, "Test", new User("Test user", "test_pass", "EMPLOYEE"), superior), ExpenseStatus.INITIAL,
                                "example_test_note_1");                  

        Employee employee= expense.getEmployee();

        when(expenseServiceMock.getExpenseById(any())).thenReturn(expense);
        when(employeeServiceMock.getEmployeeById(any())).thenReturn(employee);
        when(superiorServiceMock.getSuperiorById(any())).thenReturn(superior);
        when(expenseServiceMock.updateExpense(any())).thenReturn(expense);

        Exception exception = assertThrows(ForbiddenResourceException.class, ()-> sut.approveOrRejectExpense(expense.getId(), superior.getId(), ExpenseStatus.APPROVED,
        "example_approve_note_2"));

        assertEquals("Expense should have 'Pending' status", exception.getMessage());
    }

    @Test
    public void rejectExpenseNichtRichtigerAusgangsstatusInitialTest() {
        Superior superior = new Superior(2L, "Superior", new User("Test Superior", "test pass", "SUPERIOR"));
        Expense expense = new Expense(999L, "Test_Expense", 999.99f, LocalDate.of(1994, 10, 1), new Category(100L),
                new Shop(100L),
                new Employee(100L, "Test", new User("Test user", "test_pass", "EMPLOYEE"), superior),
                ExpenseStatus.PENDING,
                "example_test_note_3");

        Employee employee = expense.getEmployee();

        when(expenseServiceMock.getExpenseById(any())).thenReturn(expense);
        when(employeeServiceMock.getEmployeeById(any())).thenReturn(employee);
        when(superiorServiceMock.getSuperiorById(any())).thenReturn(superior);
        when(expenseServiceMock.updateExpense(any())).thenReturn(expense);

        Exception exception = assertThrows(IllegalArgumentException.class, ()-> sut.approveOrRejectExpense(expense.getId(), superior.getId(), ExpenseStatus.INITIAL,
        "example_reject_note_3"));

        assertEquals("Expense should have 'Approved' or 'Rejected' status", exception.getMessage());
    }

    @Test
    public void rejectExpenseNichtRichtigerAusgangsstatusPendingTest() {
        Superior superior = new Superior(2L, "Superior", new User("Test Superior", "test pass", "SUPERIOR"));
        Expense expense = new Expense(999L, "Test_Expense", 999.99f, LocalDate.of(1994, 10, 1), new Category(100L),
                new Shop(100L),
                new Employee(100L, "Test", new User("Test user", "test_pass", "EMPLOYEE"), superior),
                ExpenseStatus.PENDING,
                "example_test_note_3");

        Employee employee = expense.getEmployee();

        when(expenseServiceMock.getExpenseById(any())).thenReturn(expense);
        when(employeeServiceMock.getEmployeeById(any())).thenReturn(employee);
        when(superiorServiceMock.getSuperiorById(any())).thenReturn(superior);
        when(expenseServiceMock.updateExpense(any())).thenReturn(expense);

        Exception exception = assertThrows(IllegalArgumentException.class, ()-> sut.approveOrRejectExpense(expense.getId(), superior.getId(), ExpenseStatus.PENDING,
        "example_reject_note_3"));

        assertEquals("Expense should have 'Approved' or 'Rejected' status", exception.getMessage());
    }

    @Test
    public void approveNotExistingExpenseTest() {
        Superior superior = new Superior(2L, "Superior", new User("Test Superior", "test pass", "SUPERIOR"));
        Long expenseId=900L;

        when(expenseServiceMock.getExpenseById(any())).thenThrow(new ResourceNotFoundException("Expense with id = " + expenseId + " not found."));

        Exception exception = assertThrows(ResourceNotFoundException.class, ()-> sut.approveOrRejectExpense(expenseId, superior.getId(), ExpenseStatus.APPROVED,
        "example_not_approve_note_3"));

        assertEquals("Expense with id = " + expenseId + " not found.", exception.getMessage());
    }
}
