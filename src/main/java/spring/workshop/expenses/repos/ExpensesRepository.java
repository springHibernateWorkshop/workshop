package spring.workshop.expenses.repos;
import org.springframework.data.jpa.repository.JpaRepository;

import spring.workshop.expenses.entities.Expenses;

public interface ExpensesRepository extends JpaRepository<Expenses, Long>{
    
}
