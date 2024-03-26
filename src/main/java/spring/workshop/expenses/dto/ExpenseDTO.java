package spring.workshop.expenses.dto;

import java.time.LocalDate;

import spring.workshop.expenses.entities.Category;
import spring.workshop.expenses.entities.Shop;
import spring.workshop.expenses.enums.ExpenseStatus;

public class ExpenseDTO {

    private Long id;

    private String name;

    private Float total;

    private LocalDate date;

    private ExpenseStatus status;

    private String note;

    private CategoryDTO category;

    private ShopDTO shop;

    private EmployeeDTO employee;

    public ExpenseDTO(String name, float total, LocalDate date, Category category, Shop shop) {
        this.name = name;
        this.total = total;
        this.date = date;
        this.category = new CategoryDTO(category.getId());
        this.shop = new ShopDTO(shop.getId());
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

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO categoryId) {
        this.category = categoryId;
    }

    public ShopDTO getShop() {
        return shop;
    }

    public void setShop(ShopDTO shop) {
        this.shop = shop;
    }

    public EmployeeDTO getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeDTO employee) {
        this.employee = employee;
    }

    public ExpenseStatus getStatus() {
        return status;
    }

    public void setStatus(ExpenseStatus status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}