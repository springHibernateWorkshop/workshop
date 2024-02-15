package spring.workshop.expenses.entities;

import org.springframework.lang.NonNull;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "employee_tab")
public class Employee implements Person {

    @Id
    @Column(name = "employee_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    @NonNull
    private String name;

    @OneToOne(optional = true)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "superior_id", referencedColumnName = "superior_id")
    private Superior superior;

    public Employee() {
    }

    public Employee(@NonNull String name, User user, Superior superior) {
        this.name = name;
        this.user = user;
        this.superior = superior;
    }

    public Employee(Long id, @NonNull String name, User user, Superior superior) {
        this.id = id;
        this.name = name;
        this.user = user;
        this.superior = superior;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Superior getSuperior() {
        return superior;
    }

    public void setSuperior(Superior superior) {
        this.superior = superior;
    }

}
