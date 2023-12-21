package spring.workshop.expenses.entities;
 
import org.springframework.lang.NonNull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="user_tab")
public class User {

@Id
  @Column(name="user_id")
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Integer id;

  @Column
  @NonNull
  private String name;

    public User() {
    }

    public User(String name) {
    this.name = name;
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

    public void setName(String name) {
        this.name = name;
    }
    
}
