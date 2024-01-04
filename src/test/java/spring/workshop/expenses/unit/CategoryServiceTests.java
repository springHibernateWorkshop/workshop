package spring.workshop.expenses.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import spring.workshop.expenses.entities.Category;
import spring.workshop.expenses.services.CategoryService;

/**
 * This class contains unit tests for the CategoryController class.
 */
@SpringBootTest
@ActiveProfiles("test")
public class CategoryServiceTests {

    @Autowired
    private CategoryService sut;

    @Test
    public void testGetAllCategories() {
        // Given
        // When
        List<Category> allCategories = sut.findAll();
        // Then
        assertEquals(3, allCategories.size());
    }

    @Test
    public void testGetCategoryById() {
        // Given
        // When
        Category category = sut.findById(2);
        // Then
        assertEquals("Category2", category.getName());
    }

    @Test
    public void testCreateCategory() {
        // Given
        // Category"));
        // When
        sut.addCategory(new Category("Test Category"));
        // Then
        assertEquals(4, sut.findAll().size());
    }

    @Test
    public void testUpdateCategory() {
        // Given
        Category c = new Category("Test");
        // When
        sut.updateCategory(1, c);
        // Then
        assertEquals("Test", sut.findById(1).getName());
    }

    @Test
    public void testDeleteCategory() {
        // Arrange
        Integer categoryId = 1;

        // Act
        sut.deleteCategory(categoryId);

        // Assert
        assertThrows(IllegalArgumentException.class, () -> sut.findById(1));
    }

}
