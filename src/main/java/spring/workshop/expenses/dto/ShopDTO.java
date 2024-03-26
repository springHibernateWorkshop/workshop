package spring.workshop.expenses.dto;

public class ShopDTO {

    private Long id;

    public ShopDTO() {
    }

    public ShopDTO(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}