package spring.workshop.expenses.entities;

import org.springframework.lang.NonNull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "category_tab")
@SequenceGenerator(name = "categorySeq", sequenceName = "CATEGORY_SEQ", allocationSize = 1, initialValue = 100)
public class Category {

    @Id
    @Column(name = "category_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "categorySeq")
    private Integer id;

    @Column
    @NonNull
    private String name;

    public Category() {
        this.name = "";
    }

    public Category(@NonNull String name) {
        this.name = name;
    }

    public Category(Integer id, @NonNull String name) {
        this.name = name;
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

}
