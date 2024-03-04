package spring.workshop.expenses.unit.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

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
import spring.workshop.expenses.repositories.AbstractRepositoryHelper;
import spring.workshop.expenses.repositories.ExpenseRepository;
import spring.workshop.expenses.services.ExpenseService;
import spring.workshop.expenses.services.impl.ExpenseServiceImpl;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class ExpenseServiceTest {

    @Mock
    ExpenseRepository expenseRepositoryMock;

    @Mock
    AbstractRepositoryHelper<Expense> abstractRepositoryHelperMock;

    @InjectMocks
    private ExpenseService sut = new ExpenseServiceImpl();

    @Test
    public void testAddNewExpense() {
        // Given
        Expense expense = new Expense("Expense", 100.00F, LocalDate.of(2024, 2, 16), new Category("Category"),
                new Shop("Shop"), new Employee("Employee", new User(), new Superior()), ExpenseStatus.INITIAL);

        when(abstractRepositoryHelperMock.saveAndRefresh(any(Expense.class))).thenReturn(expense);
        // When
        Expense response = sut.addNewExpense(expense);
        // Then
        assertEquals("Expense", response.getName());
        assertEquals(100.00F, response.getTotal());
        assertEquals(LocalDate.of(2024, 2, 16), response.getDate());
        assertEquals("Category", response.getCategory().getName());
        assertEquals("Shop", response.getShop().getName());
        assertEquals("Employee", response.getEmployee().getName());
        assertEquals(ExpenseStatus.INITIAL, response.getStatus());
    }

    @Test
    public void testDeleteExpense() {
        // Given
        Expense expense = new Expense(1L, "Expense", 100.00F, LocalDate.of(2024, 2, 19), new Category(),
                new Shop(), new Employee("Employee", new User(), new Superior()), ExpenseStatus.INITIAL, null);

        when(expenseRepositoryMock.findById(any(Long.class))).thenReturn(Optional.of(expense));
        // When
        sut.deleteExpense(expense.getId());
        // Then
        verify(expenseRepositoryMock).deleteById(expense.getId());
    }

}
