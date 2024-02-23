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
public class User extends AbstractEntity {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    @NonNull
    private String username;
    // unique should be set at the datenbank level
    // https://stackoverflow.com/questions/3496028/columnunique-true-does-not-seem-to-work

    @Column(nullable = false, name = "password")
    @NonNull
    private String passwd;

    @Column(nullable = false, name = "role")
    @NonNull
    private String role;

    public User() {
    }

    public User(@NonNull String username, @NonNull String password, @NonNull String role) {
        this.username = username;
        this.passwd = password;
        this.role = role;
    }

    public User(Long id, @NonNull String username, @NonNull String password, @NonNull String role) {
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

    public String getRole() {
        return role;
    }

    public void setRole(@NonNull String roleId) {
        this.role = roleId;
    }

}
