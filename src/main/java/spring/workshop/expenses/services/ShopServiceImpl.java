package spring.workshop.expenses.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import spring.workshop.expenses.entities.Shop;
import spring.workshop.expenses.repos.ShopRepository;

@Service
public class ShopServiceImpl implements ShopService{

    @Autowired
    private ShopRepository shopRepository;

    public ShopServiceImpl() {
        super();
    }

    @Override
    public List<Shop> getAllShops() {
        List<Shop> shops = shopRepository.findAll();
        return shops;
    }

    @Override
    public Shop getShop(Long id) {
        Shop shop = shopRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return shop;
    }

    @Override
    public Shop replaceShop(Shop shop, Long id) {
        Shop replaceShop = shopRepository.findById(id).map(upShop -> {
            upShop.setName(shop.getName());
            upShop.setAddress(shop.getAddress());
            return shopRepository.save(upShop);
        })
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return replaceShop;
    }

    @Override
    public Shop deleteShop(Long id) {
        Shop shop = shopRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        shopRepository.deleteById(id);
        return shop;
    }
    
}
