package spring.workshop.expenses.integration.shop;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;
import spring.workshop.expenses.entities.Shop;
import spring.workshop.expenses.repos.ShopRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@Rollback
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class ShopControllerMockTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ShopRepository repo;

    private final String BASE_URL = "/shops";

    /**
     * Test case to verify the functionality of retrieving all categories.
     * 
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void TestGetAllShops() throws Exception {
        // Arrange
        assertEquals(3, repo.findAll().size());

        // Act
        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(3));

    }

    /**
     * Test case for getting a shop by its ID.
     * 
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testGetShopById() throws Exception {
        // Arrange
        assertEquals(3, repo.findAll().size());

        // Act
        mockMvc.perform(get(BASE_URL + "/{id}", 100))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("shop_1"))
                .andExpect(jsonPath("$.address").value("address_1"));

    }

    /**
     * Test case to verify the behavior of adding a new shop.
     * 
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testAddNewShop() throws Exception {
        // Arrange
        Shop shop = new Shop();
        shop.setName("shop_name_4");
        shop.setAddress("shop_address_4");

        assertEquals(3, repo.findAll().size());

        // Act
        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(shop)))
                .andExpect(status().isCreated());

        // Assert
        assertEquals(4, repo.findAll().size());
    }

    /**
     * Test case to verify the functionality of deleting a shop.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testDeleteShop() throws Exception {
        // Arrange
        assertEquals(3, repo.findAll().size());

        // Act
        mockMvc.perform(delete(BASE_URL + "/{id}", 300))
                .andExpect(status().isOk());

        // Assert
        assertEquals(2, repo.findAll().size());
    }
}
