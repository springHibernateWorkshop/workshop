package spring.workshop.expenses.unit.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import spring.workshop.expenses.entities.Category;
import spring.workshop.expenses.repositories.CategoryRepository;
import spring.workshop.expenses.services.CategoryService;
import spring.workshop.expenses.services.impl.CategoryServiceImpl;

/**
 * This class contains unit tests for the CategoryController class.
 */
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class CategoryServiceTest {

    private CategoryService sut;

    @Mock
    CategoryRepository categoryRepositoryMock;

    @BeforeEach
    public void setup() {
        sut = new CategoryServiceImpl(categoryRepositoryMock);
    }

    @Test
    public void testGetAllCategories() {
        // Given
        when(categoryRepositoryMock.findByOrderById())
                .thenReturn(List.of(new Category(), new Category(), new Category()));
        // When
        List<Category> allCategories = sut.findAll();
        // Then
        assertEquals(3, allCategories.size());
    }

    @Test
    public void testGetCategoryById() {
        // Given
        when(categoryRepositoryMock.findById(any(Long.class)))
                .thenReturn(Optional.of(new Category("Category1")));
        // When
        Category category = sut.findById(1l);
        // Then
        assertEquals("Category1", category.getName());
    }

    @Test
    public void testCreateCategory() {
        // Given
        Category category = new Category("TestCreat");
        when(categoryRepositoryMock.save(any()))
                .thenReturn(category);
        // When
        Category newCategory = sut.addCategory(category);
        // Then
        assertEquals(category.getName(), newCategory.getName());
    }

    @Test
    public void testUpdateCategory() {
        // Given
        Category category = new Category(1l, "Test");
        when(categoryRepositoryMock.save(any()))
                .thenReturn(category);
        when(categoryRepositoryMock.findById(any(Long.class)))
                .thenReturn(Optional.of(new Category("Category1")));
        // When
        Category updatedCategory = sut.updateCategory(category);
        // Then
        assertEquals("Test", sut.findById(updatedCategory.getId()).getName());
    }

    @Test
    public void testDeleteCategory() {
        // Arrange
        Long categoryId = 1l;
        when(categoryRepositoryMock.findById(categoryId))
                .thenReturn(Optional.of(new Category("Category1")));
        // Act
        Boolean result = sut.deleteCategory(categoryId);

        // Assert
        assertEquals(Boolean.TRUE, result);
    }

}
