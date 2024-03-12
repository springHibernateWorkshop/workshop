package spring.workshop.expenses.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import spring.workshop.expenses.security.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findById(Long id);

}
