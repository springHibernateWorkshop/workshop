package spring.workshop.expenses.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import spring.workshop.expenses.entities.Shop;
import spring.workshop.expenses.services.ShopService;

@RestController
@RequestMapping(path = "/shops")
public class ShopController {

    @Autowired
    private ShopService shopService;

    @GetMapping
    @Operation(summary = "Get all shops", description = "Fetches all shops from the database")
    public ResponseEntity<List<Shop>> getAllShops() {
        return new ResponseEntity<>(shopService.getAllShops(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a shop", description = "Fetche the shop with the given id from the database")
    public ResponseEntity<Shop> getShop(@PathVariable @Parameter(description = "ID of the shop") Long id) {
        return new ResponseEntity<>(shopService.getShop(id), HttpStatus.OK);
    }
}
