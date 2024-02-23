package spring.workshop.expenses.entities;

import java.time.LocalDate;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import spring.workshop.expenses.enums.ExpenseStatus;

@Entity
@Table(name = "expense_tab")
public class Expense extends AbstractEntity {

    @Id
    @Column(name = "expense_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "total", nullable = false)
    private Float total;

    @Column(name = "expense_date", nullable = false)
    private LocalDate date;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ExpenseStatus status;

    @Column(name = "note")
    private String note;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private Category category;

    @ManyToOne(optional = false)
    @JoinColumn(name = "shop_id", referencedColumnName = "shop_id")
    private Shop shop;

    @ManyToOne(optional = false)
    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id")
    private Employee employee;

    public Expense() {
    }

    public Expense(@NonNull String name, @NonNull Float total, @NonNull LocalDate date,
            @NonNull Category category, @NonNull Shop shop) {
        this.name = name;
        this.total = total;
        this.date = date;
        this.category = category;
        this.shop = shop;
    }

    public Expense(@NonNull String name, @NonNull Float total, @NonNull LocalDate date,
            @NonNull Category category, @NonNull Shop shop, @NonNull Employee employee,
            @NonNull ExpenseStatus status) {
        this.name = name;
        this.total = total;
        this.date = date;
        this.category = category;
        this.shop = shop;
        this.employee = employee;
        this.status = status;
    }

    public Expense(Long id, @NonNull String name, @NonNull Float total, @NonNull LocalDate date,
            @NonNull Category category, @NonNull Shop shop, @NonNull Employee employee,
            @NonNull ExpenseStatus status, String note) {
        this.id = id;
        this.name = name;
        this.total = total;
        this.date = date;
        this.category = category;
        this.shop = shop;
        this.employee = employee;
        this.status = status;
        this.note = note;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(@NonNull Category categoryId) {
        this.category = categoryId;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(@NonNull Shop shop) {
        this.shop = shop;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(@NonNull Employee employee) {
        this.employee = employee;
    }

    public ExpenseStatus getStatus() {
        return status;
    }

    public void setStatus(@NonNull ExpenseStatus status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}