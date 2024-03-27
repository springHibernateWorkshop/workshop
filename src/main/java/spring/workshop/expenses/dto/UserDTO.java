package spring.workshop.expenses.dto;

import spring.workshop.expenses.security.Role;

public class UserDTO {

    private Long id;

    private Role role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

}