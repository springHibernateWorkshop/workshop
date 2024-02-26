package spring.workshop.expenses.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import spring.workshop.expenses.entities.Shop;
import spring.workshop.expenses.services.ShopService;

@Controller
@RequestMapping(path = "/shops")
@EnableMethodSecurity(prePostEnabled = true)
public class ShopController {

    @Autowired
    private ShopService shopService;

    @PostMapping
    public ResponseEntity<Shop> addNewShop(@RequestBody Shop shop) {

        return new ResponseEntity<>(shopService.addNewShop(shop), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Shop>> getAllShops() {
        return new ResponseEntity<>(shopService.getAllShops(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Shop> getShop(@PathVariable Long id) {
        return new ResponseEntity<>(shopService.getShop(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Shop> updateShop(@RequestBody Shop room, @PathVariable Long id) {
        return new ResponseEntity<>(shopService.updateShop(room, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Shop> deleteShop(@PathVariable Long id) {
        return new ResponseEntity<>(shopService.deleteShop(id), HttpStatus.OK);
    }

}
