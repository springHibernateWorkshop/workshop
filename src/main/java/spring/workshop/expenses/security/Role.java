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

    public static SimpleGrantedAuthority ROLE_EMPLOYEE = new SimpleGrantedAuthority("ROLE_EMPLOYEE");
    public static SimpleGrantedAuthority ROLE_SUPERIOR = new SimpleGrantedAuthority("ROLE_SUPERIOR");
    public static SimpleGrantedAuthority ROLE_ACCOUNTANT = new SimpleGrantedAuthority("ROLE_ACCOUNTANT");
    public static SimpleGrantedAuthority ROLE_ADMINISTRATOR = new SimpleGrantedAuthority("ROLE_ADMINISTRATOR");
    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;
    @OneToMany(mappedBy = "role")
    private Set<User> users;

    @ManyToMany
    @JoinTable(name = "permission_tab", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "right_id"))
    private Set<Right> rights;

    @Override
    public String getAuthority() {
        return name;
    }

    public Set<Right> getRights() {
        return rights;
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
