package spring.workshop.expenses.entities;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name = "shop_tab")
public class Shop {

    @Id
    @Column(name = "shop_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    @NonNull
    private String name;

    @Column
    private String address;

    @Version
    private java.sql.Timestamp version;

    public Shop() {
    }

    public Shop(Long id) {
        this.id = id;
    }

    public Shop(@NonNull String name) {
        this.name = name;
    }

    public Shop(Long id, @NonNull String name) {
        this.id = id;
        this.name = name;
    }

    public Shop(Long id, @NonNull String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public Long getId() {
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

    public java.sql.Timestamp getVersion() {
        return version;
    }

    public void setVersion(java.sql.Timestamp version) {
        this.version = version;
    }

}
