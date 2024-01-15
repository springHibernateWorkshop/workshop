package spring.workshop.expenses.integration.shop;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import spring.workshop.expenses.entities.Shop;
import spring.workshop.expenses.rest.ShopController;

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
        ResponseEntity<List> response = restTemplate.getForEntity(BASE_URL, List.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    /**
     * Test case to verify the positive scenario of getting a shop by its ID.
     */
    @Test
    public void testGetSHopByIdPositive() {
        ResponseEntity<Shop> response = restTemplate.getForEntity(BASE_URL + "/{id}", Shop.class, 200);
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

    /**
     * Test case to verify the positive scenario of adding a category.
     */
    @Test
    public void testAddShopPositive() {

        Shop newShop = new Shop();
        newShop.setName("shop_name_5");
        newShop.setAddress("shop_address_5");
        ResponseEntity<Shop> response = restTemplate.postForEntity(BASE_URL, newShop, Shop.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Shop shop = response.getBody();
        assertEquals("shop_name_5", shop.getName());
        assertEquals("shop_address_5", shop.getAddress());
    }

    /**
     * Test case to verify the positive scenario of deleting a shop.
     */
    @Test
    public void testDeleteShopPositive() {

        Shop newShop = new Shop();
        newShop.setName("shop_name_6");
        newShop.setAddress("shop_address_6");
        ResponseEntity<Shop> res = restTemplate.postForEntity(BASE_URL, newShop, Shop.class);

        Shop shop = res.getBody();

        ResponseEntity<Shop> response = restTemplate.getForEntity(BASE_URL + "/{id}", Shop.class, shop.getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        restTemplate.delete("/shops/{id}", shop.getId());
        ResponseEntity<Shop> responseAfterDelete = restTemplate.getForEntity(BASE_URL + "/{id}", Shop.class,
                shop.getId());
        assertEquals(HttpStatus.NOT_FOUND, responseAfterDelete.getStatusCode());
    }

    /**
     * Test case to verify the negative scenario of deleting a shop.
     * It sends a GET request to retrieve a category with the specified ID,
     * and expects a NOT_FOUND status code in the response.
     * Then, it sends a DELETE request to delete the category with the same ID,
     * and again expects a NOT_FOUND status code in the response.
     */
    @Test
    public void testDeleteCategoryNegative() {
        ResponseEntity<Shop> response = restTemplate.getForEntity(BASE_URL + "/{id}", Shop.class, 7);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        ResponseEntity<Shop> responseAfterDelete = restTemplate.exchange(BASE_URL + "/{id}", HttpMethod.DELETE,
                null, Shop.class, 7);
        assertEquals(HttpStatus.NOT_FOUND, responseAfterDelete.getStatusCode());
    }

}
