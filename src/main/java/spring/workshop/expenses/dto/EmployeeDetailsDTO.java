package spring.workshop.expenses.dto;

public class EmployeeDetailsDTO {

    private Long id;

    private String name;

    private UserDTO user;

    private SuperiorDTO superior;

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public SuperiorDTO getSuperior() {
        return superior;
    }

    public void setSuperior(SuperiorDTO superior) {
        this.superior = superior;
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

}