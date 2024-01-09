package spring.workshop.expenses.entities;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table( name = "shop_tab")
public class Shop {

    @Id
  @Column(name="shop_id")
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Integer id;

  @Column
  @NonNull
  private String name;

  @Column
  private String address;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    
    
}
