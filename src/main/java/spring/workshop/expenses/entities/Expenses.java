package spring.workshop.expenses.entities;

import java.sql.Date;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

  @Column(name="category_id")
  private Integer categoryId;

  @Column(name="shop_id")
  private Integer shopId;

  @Column(name="user_id")
  private Integer userId;

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


    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(@NonNull Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(@NonNull Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(@NonNull Integer userId) {
        this.userId = userId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
    
}