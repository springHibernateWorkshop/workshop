package spring.workshop.expenses.entities;

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
    private String username;
    // unique should be set at the datenbank level
    // https://stackoverflow.com/questions/3496028/columnunique-true-does-not-seem-to-work

    @Column(nullable = false, name = "password")
    private String passwd;

    @Column(nullable = false, name = "role")
    private String role;

    public User() {
    }

    public User(String username, String password, String role) {
        this.username = username;
        this.passwd = password;
        this.role = role;
    }

    public User(Long id, String username, String password, String role) {
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

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return passwd;
    }

    public void setPassword(String password) {
        this.passwd = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String roleId) {
        this.role = roleId;
    }

}
