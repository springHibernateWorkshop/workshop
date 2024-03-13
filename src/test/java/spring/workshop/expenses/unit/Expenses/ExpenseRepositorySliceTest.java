package spring.workshop.expenses.unit.Expenses;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import spring.workshop.expenses.entities.Expense;
import spring.workshop.expenses.repositories.ExpenseRepository;

@DataJpaTest
public class ExpenseRepositorySliceTest {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Test
    public void testExpenseRepository() {
        Expense expense = expenseRepository.findByIdAndUsernameWithEmployee(100l, "victoria").orElseThrow();

        assertEquals(0.99f, expense.getTotal());
    }

}
