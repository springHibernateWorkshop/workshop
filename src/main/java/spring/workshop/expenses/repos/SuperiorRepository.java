package spring.workshop.expenses.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.workshop.expenses.entities.Superior;

public interface SuperiorRepository extends JpaRepository<Superior, Long>{
    
    
}
