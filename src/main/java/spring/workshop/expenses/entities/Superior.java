package spring.workshop.expenses.entities;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "superior_tab")
public class Superior implements Person {

    @Id
    @Column(name = "superior_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    @Column
    private String name;

    @OneToOne(optional = true)
    @JoinColumn(name = "user_id")
    private User user;

    public Superior() {
    }

    public Superior(Long id, @NonNull String name, User user) {
        this.id = id;
        this.name = name;
        this.user = user;
    }

    public Superior(Long id, @NonNull String name) {
        this.id = id;
        this.name = name;
    }

    public Superior(@NonNull String name, @NonNull User user) {
        this.name = name;
        this.user = user;
    }

    public Superior(@NonNull String name) {
        this.name = name;
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

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
