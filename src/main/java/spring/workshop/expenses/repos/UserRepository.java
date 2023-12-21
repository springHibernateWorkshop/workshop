package spring.workshop.expenses.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import spring.workshop.expenses.entities.User;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserRepository  extends JpaRepository<User, Integer> {

    User findByName(String name);

}
