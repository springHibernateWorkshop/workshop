package spring.workshop.expenses.entities;

import org.springframework.lang.NonNull;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_tab")
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    @NonNull
    private String username;

    @Column(nullable = false, name = "password")
    @NonNull
    private String passwd;

    @Column(nullable = false, name = "role_id")
    @NonNull
    private Long role;

    public User() {
    }

    public User(@NonNull String username, @NonNull String password, @NonNull Long role) {
        this.username = username;
        this.passwd = password;
        this.role = role;
    }

    public User(Long id, @NonNull String username, @NonNull String password, @NonNull Long role) {
        this.id = id;
        this.username = username;
        this.passwd = password;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    public String getPassword() {
        return passwd;
    }

    public void setPassword(@NonNull String password) {
        this.passwd = password;
    }

    public Long getRole() {
        return role;
    }

    public void setRole(@NonNull Long roleId) {
        this.role = roleId;
    }

}
