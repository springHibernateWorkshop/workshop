package spring.workshop.expenses.services;

import java.util.List;

import spring.workshop.expenses.entities.Shop;

public interface ShopService {
    List<Shop> getAllShops();
    Shop getShop(Long id);
    Shop replaceShop(Shop shop, Long id);
    Shop deleteShop(Long id);
}
