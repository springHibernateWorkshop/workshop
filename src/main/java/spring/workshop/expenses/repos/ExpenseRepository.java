package spring.workshop.expenses.repos;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import spring.workshop.expenses.entities.Expense;
import spring.workshop.expenses.entities.Shop;
import spring.workshop.expenses.entities.User;

public interface ExpenseRepository extends JpaRepository<Expense, Long>{

    List<Expense> findByDate(LocalDate date);

    List<Expense> findByCategoryId(Integer categoryId);

    List<Expense> findByShop(Shop shop);

    List<Expense> findByUser(User user);
    
}
