package spring.workshop.expenses.unit.Category;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import spring.workshop.expenses.entities.Category;
import spring.workshop.expenses.services.CategoryService;

/**
 * This class contains unit tests for the CategoryController class.
 */
@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class CategoryServiceTests {

    @Autowired
    private CategoryService sut;

    @Test
    @Order(1)
    public void testGetAllCategories() {
        // Given
        // When
        List<Category> allCategories = sut.findAll();
        // Then
        assertEquals(3, allCategories.size());
    }

    @Test
    @Order(2)
    public void testGetCategoryById() {
        // Given
        // When
        Category category = sut.findById(200);
        // Then
        assertEquals("Category2", category.getName());
    }

    @Test
    @Order(3)
    public void testCreateCategory() {
        // Given
        Category c = new Category("TestCreat");
        // When
        sut.addCategory(c);
        // Then
        assertEquals(4, sut.findAll().size());
    }

    @Test
    @Order(4)
    public void testUpdateCategory() {
        // Given
        Category category = new Category(100, "Test");
        // When
        Category updatedCategory = sut.updateCategory(category);
        // Then
        assertEquals("Test", sut.findById(updatedCategory.getId()).getName());
    }

    @Test
    @Order(5)
    public void testDeleteCategory() {
        // Arrange
        Integer categoryId = 100;

        // Act
        sut.deleteCategory(categoryId);

        // Assert
        assertThrows(IllegalArgumentException.class, () -> sut.findById(100));
    }

}
