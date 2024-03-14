package spring.workshop.expenses.unit.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
                                new Shop("Shop"), new Employee("Employee", new User(), new Superior()),
                                ExpenseStatus.INITIAL);

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
                                new Shop(), new Employee("Employee", new User(), new Superior()), ExpenseStatus.INITIAL,
                                null);

                doNothing().when(expenseRepositoryMock).deleteById(any(Long.class));
                // When
                sut.deleteExpense(expense.getId());
                // Then
                verify(expenseRepositoryMock).deleteById(expense.getId());
        }

        @Test
        public void testFilterWithNoCriteria() {
                // Given
                List<Expense> expenses = List
                                .of(new Expense(1L, "Expense", 100.00F, LocalDate.of(2023, 2, 19),
                                                new Category(100L, "Category"),
                                                new Shop(100L), new Employee("Employee", new User(), new Superior()),
                                                ExpenseStatus.INITIAL,
                                                null),
                                                new Expense(2L, "Expense", 100.00F, LocalDate.of(2024, 2, 19),
                                                                new Category(100L, "Category"),
                                                                new Shop(100L),
                                                                new Employee("Employee", new User(), new Superior()),
                                                                ExpenseStatus.INITIAL,
                                                                null));

                // When
                List<Expense> filteredExpenses = sut.filter(expenses, null, null, null, null);
                // Then
                assertEquals(2, filteredExpenses.size());

        }

        @Test
        public void testFilterWithAllCriteria() {
                // Given
                List<Expense> expenses = List
                                .of(new Expense(1L, "Expense", 100.00F, LocalDate.of(2023, 2, 19),
                                                new Category(1L, "Category"),
                                                new Shop(1L), new Employee("Employee", new User(), new Superior()),
                                                ExpenseStatus.INITIAL,
                                                null),
                                                new Expense(2L, "Expense", 100.00F, LocalDate.of(2024, 2, 19),
                                                                new Category(1L, "Category"),
                                                                new Shop(2L),
                                                                new Employee("Employee", new User(), new Superior()),
                                                                ExpenseStatus.INITIAL,
                                                                null));

                // When
                List<Expense> filteredExpenses = sut.filter(expenses, 2024, 2, 1L, 2L);
                // Then
                assertEquals(1, filteredExpenses.size());
        }

        @Test
        public void testFilterWithDateCriteria() {
                // Given
                List<Expense> expenses = List
                                .of(new Expense(1L, "Expense", 100.00F, LocalDate.of(2023, 2, 19),
                                                new Category(1L, "Category"),
                                                new Shop(1L), new Employee("Employee", new User(), new Superior()),
                                                ExpenseStatus.INITIAL,
                                                null),
                                                new Expense(2L, "Expense", 100.00F, LocalDate.of(2024, 2, 19),
                                                                new Category(1L, "Category"),
                                                                new Shop(2L),
                                                                new Employee("Employee", new User(), new Superior()),
                                                                ExpenseStatus.INITIAL,
                                                                null));

                // When
                List<Expense> filteredExpenses = sut.filter(expenses, null, 2, null, null);
                // Then
                assertEquals(1, filteredExpenses.size());
        }

        @Test
        public void testFilterWithYearCriteria() {
                // Given
                List<Expense> expenses = List
                                .of(new Expense(1L, "Expense", 100.00F, LocalDate.of(2023, 2, 19),
                                                new Category(1L, "Category"),
                                                new Shop(1L), new Employee("Employee", new User(), new Superior()),
                                                ExpenseStatus.INITIAL,
                                                null),
                                                new Expense(2L, "Expense", 100.00F, LocalDate.of(2024, 2, 19),
                                                                new Category(1L, "Category"),
                                                                new Shop(2L),
                                                                new Employee("Employee", new User(), new Superior()),
                                                                ExpenseStatus.INITIAL,
                                                                null));

                // When
                List<Expense> filteredExpenses = sut.filter(expenses, 2023, null, null, null);
                // Then
                assertEquals(1, filteredExpenses.size());
        }

        @Test
        public void testFilterWithEmptyExpensesList() {
                // Given
                List<Expense> expenses = new ArrayList<>();

                // When
                List<Expense> filteredExpenses = sut.filter(expenses, 2023, null, null, null);
                // Then
                assertEquals(0, filteredExpenses.size());
        }

}
