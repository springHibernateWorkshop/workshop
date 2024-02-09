package spring.workshop.expenses.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.workshop.expenses.entities.Superior;

public interface SuperiorRepository extends JpaRepository<Superior, Long> {
  Optional<Superior> findById(Long id);

}
