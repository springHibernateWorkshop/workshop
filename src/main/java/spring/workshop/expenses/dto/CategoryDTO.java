package spring.workshop.expenses.dto;

public class CategoryDTO {

    private Long id;

    public CategoryDTO() {
    }

    public CategoryDTO(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}