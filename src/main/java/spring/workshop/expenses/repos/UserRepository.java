package spring.workshop.expenses.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.transaction.Transactional;
import spring.workshop.expenses.entities.User;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByName(String name);

    Optional<User> findById(Integer id);

    @Transactional
    void deleteByName(String name);

}
