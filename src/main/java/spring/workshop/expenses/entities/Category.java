package spring.workshop.expenses.entities;

import org.springframework.lang.NonNull;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name = "category_tab")
public class Category {

    @Id
    @Column(name = "category_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    @NonNull
    private String name;

    @Version
    private java.sql.Timestamp version;

    public Category() {
        this.name = "";
    }

    public Category(Long id) {
        this.id = id;
    }

    public Category(@NonNull String name) {
        this.name = name;
    }

    public Category(Long id, @NonNull String name) {
        this.name = name;
        this.id = id;
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

    public java.sql.Timestamp getVersion() {
        return version;
    }

    public void setVersion(java.sql.Timestamp version) {
        this.version = version;
    }
}
