package spring.workshop.expenses.services;

import spring.workshop.expenses.security.Role;

public interface RoleService {
    Role findById(Long id);
}
