package spring.workshop.expenses.security;

import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import spring.workshop.expenses.entities.User;

@Entity
@Table(name = "role_tab")
public class Role implements GrantedAuthority {

    public static final String ROLE_EMPLOYEE = "ROLE_EMPLOYEE";
    public static final String ROLE_SUPERIOR = "ROLE_SUPERIOR";

    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "role")
    private Set<User> users;

    @ManyToMany
    @JoinTable(name = "permission_tab", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "right_id"))
    private Set<Right> rights;

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    public Role(Long id) {
        this.id = id;
    }

    public Role(String name, Set<Right> rights) {
        this.name = name;
        this.rights = rights;
    }

    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name;
    }

    public Set<Right> getRights() {
        return rights;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRights(Set<Right> rights) {
        this.rights = rights;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
