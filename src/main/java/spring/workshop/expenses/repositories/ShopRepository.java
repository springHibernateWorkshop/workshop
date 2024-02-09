package spring.workshop.expenses.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import spring.workshop.expenses.entities.Shop;

public interface ShopRepository extends JpaRepository<Shop, Long> {

}
