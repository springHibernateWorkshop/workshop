package spring.workshop.expenses.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import spring.workshop.expenses.entities.Shop;

public interface ShopRepository extends JpaRepository<Shop, Long>{
    
}
