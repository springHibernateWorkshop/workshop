package spring.workshop.expenses.repositories;

import java.util.List;
import java.util.Optional;

import spring.workshop.expenses.entities.Category;

public interface CategoryRepository extends AbstractRepository<Category> {

    Optional<Category> findByName(String name);

    List<Category> findByOrderById();

}
