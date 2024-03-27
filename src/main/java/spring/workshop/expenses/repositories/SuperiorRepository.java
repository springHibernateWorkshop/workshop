package spring.workshop.expenses.repositories;

import java.util.Optional;

import spring.workshop.expenses.entities.Superior;
import spring.workshop.expenses.entities.User;

public interface SuperiorRepository extends AbstractRepository<Superior> {

    Optional<Superior> findByUser(User user);

}
