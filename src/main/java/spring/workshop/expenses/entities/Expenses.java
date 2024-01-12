package spring.workshop.expenses.entities;

import java.sql.Date;

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
public class Expenses {

  @Id
  @Column(name="expense_id")
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Integer id;

  @Column(name="Total")
  private Float total;

  @Column(name="expense_date")
  private Date date;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "category_id", referencedColumnName = "category_id")
  private Category category;

  @OneToOne(cascade = CascadeType.ALL)
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

    public Date getDate() {
        return date;
    }

    public void setDate(@NonNull Date date) {
        this.date = date;
    }


    public Category getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(@NonNull Category categoryId) {
        this.categoryId = categoryId;
    }

    public Shop getShopId() {
        return shopId;
    }

    public void setShopId(@NonNull Shop shopId) {
        this.shopId = shopId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(@NonNull User userId) {
        this.userId = userId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
    
}