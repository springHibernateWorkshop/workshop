package spring.workshop.expenses.repositories;

import java.util.Optional;

import spring.workshop.expenses.entities.User;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserRepository extends AbstractRepository<User> {

    Optional<User> findByUsername(String username);

}
