package spring.workshop.expenses.repositories;

import spring.workshop.expenses.entities.Superior;
import spring.workshop.expenses.entities.User;

public interface SuperiorRepository extends AbstractRepository<Superior> {

    Superior findByUser(User user);

}
