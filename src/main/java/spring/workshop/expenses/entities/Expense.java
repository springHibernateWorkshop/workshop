package spring.workshop.expenses.entities;


import java.time.LocalDate;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="expense_tab")
public class Expense {

  @Id
  @Column(name="expense_id")
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Integer id;

  @Column(name="total")
  private Float total;

  @Column(name="expense_date")
  private LocalDate date;

  @Column(name = "category_id")
  private Integer categoryId;

  @OneToOne(cascade = CascadeType.REFRESH)
  @JoinColumn(name = "shop_id", referencedColumnName = "shop_id")
  private Shop shop;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "user_id", referencedColumnName = "user_id")
  private User user;

  @Column
  private String note;

    public Integer getId() {
        return id;
    }

    public void setId( Integer id) {
        this.id = id;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(@NonNull Float total) {
        this.total = total;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(@NonNull LocalDate date) {
        this.date = date;
    }


    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(@NonNull Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(@NonNull Shop shop) {
        this.shop = shop;
    }

    public User getUser() {
        return user;
    }

    public void setUser(@NonNull User user) {
        this.user = user;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
    
}