package spring.workshop.expenses.unit.shop;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import spring.workshop.expenses.controllers.ShopController;
import spring.workshop.expenses.entities.Shop;
import spring.workshop.expenses.services.ShopService;

@WebMvcTest(ShopController.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class ShopControllerSliceTest {

    @Autowired
    private ShopController shopController;

    @MockBean
    private ShopService shopService;

    public void setUp() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

    }

    @Test
    public void testGetAllShops() {
        // Given
        when(shopService.getAllShops()).thenReturn(List.of(new Shop(), new Shop(), new Shop()));
        // When
        ResponseEntity<List<Shop>> shops = shopController.getAllShops();
        // Then
        assertEquals(3, shops.getBody().size());
    }

    @Test
    public void testGetShopById() {
        // Given
        Shop shop = new Shop();
        shop.setName("shop_name_1");
        shop.setAddress("shop_address_1");

        when(shopService.getShop(any())).thenReturn(shop);
        // When
        ResponseEntity<Shop> resShop = shopController.getShop(Long.valueOf(1));
        // Then
        assertEquals("shop_name_1", resShop.getBody().getName());
        assertEquals("shop_address_1", resShop.getBody().getAddress());
        assertEquals(HttpStatus.OK, resShop.getStatusCode());
    }
}
