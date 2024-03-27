package spring.workshop.expenses.integration.shop;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import spring.workshop.expenses.controllers.ShopController;
import spring.workshop.expenses.entities.Shop;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class ShopIntegrationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    ShopController controller;

    private static final String BASE_URL = "/shops";

    @Test
    public void testGetAllShops() {
        ResponseEntity<List> response = restTemplate.getForEntity(BASE_URL,
                List.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(4, response.getBody().size());
    }

    /**
     * Test case to verify the positive scenario of getting a shop by its ID.
     */
    @Test
    public void testGetShopByIdPositive() {
        ResponseEntity<Shop> response = restTemplate.getForEntity(BASE_URL + "/{id}",
                Shop.class, 200);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Shop shop = response.getBody();
        assertEquals("shop_2", shop.getName());
        assertEquals("address_2", shop.getAddress());
    }

    /**
     * Test case to verify the behavior of getting a shop by ID when the
     * shop does not exist.
     */
    @Test
    public void testGetShopByIdNegative() {
        ResponseEntity<Shop> response = restTemplate.getForEntity(BASE_URL + "/{id}", Shop.class, 10);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
