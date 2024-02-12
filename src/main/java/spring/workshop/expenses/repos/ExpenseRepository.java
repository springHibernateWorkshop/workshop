package spring.workshop.expenses.repos;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import spring.workshop.expenses.entities.Expense;
import spring.workshop.expenses.entities.User;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByDate(LocalDate date);

    List<Expense> findByCategoryId(Long categoryId);

    List<Expense> findByShopId(Long shopId);

    List<Expense> findByUserId(Long userId);

    @Query("SELECT e FROM Expense e WHERE e.user.username = ?2 AND e.id = ?1")
    Optional<Expense> findByIdAndUsername(Long expenseId, String username);

    List<Expense> findByUser(User user);

}
