package spring.workshop.expenses.unit.Category;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import spring.workshop.expenses.controllers.CategoryController;
import spring.workshop.expenses.entities.Category;
import spring.workshop.expenses.services.CategoryService;

/**
 * This class contains unit tests for the CategoryController class.
 */
@WebMvcTest(CategoryController.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class CategoryControllerSliceTest {

    private CategoryController sut;

    @MockBean
    private CategoryService categoryService;

    @BeforeEach
    public void setUp() throws Exception {
        sut = new CategoryController(categoryService);
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

    }

    @Test
    public void testGetAllCategories() {
        // Given
        when(categoryService.findAll()).thenReturn(List.of(new Category(), new Category(), new Category()));
        // When
        List<Category> allCategories = sut.getAllCategories();
        // Then
        assertEquals(3, allCategories.size());
    }

    @Test
    public void testGetCategoryById() {
        // Given
        when(categoryService.findById(1l)).thenReturn(new Category("Test Category"));
        // When
        ResponseEntity<Category> category = sut.getCategoryById(1l);
        // Then
        assertEquals("Test Category", category.getBody().getName());
        assertEquals(HttpStatus.OK, category.getStatusCode());
    }

    @Test
    public void testCreateCategory() {
        // Given
        when(categoryService.addCategory(any())).thenReturn(new Category(1l, "Test Category"));
        // When
        ResponseEntity<Category> response = sut.addNewCategory(new Category("Test Category"));
        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Test Category", response.getBody().getName());
    }

    @Test
    public void testUpdateCategory() {
        // Given
        Category c = new Category(100l, "Test");
        // When
        sut.updateCategory(c);
        // Then
        verify(categoryService, times(1)).updateCategory(c);
    }

    @Test
    public void testDeleteCategoryPositive() {
        // Arrange
        Long categoryId = 1L;

        when(categoryService.deleteCategory(categoryId)).thenReturn(Boolean.TRUE);

        // Act
        Boolean result = sut.deleteCategory(categoryId);

        // Assert
        verify(categoryService, times(1)).deleteCategory(categoryId);
        assertEquals(Boolean.TRUE, result);
    }

    @Test
    public void testDeleteCategoryNegative() {
        // Arrange
        Long categoryId = 1l;
        when(categoryService.deleteCategory(categoryId)).thenReturn(Boolean.FALSE);

        // Act
        Boolean result = sut.deleteCategory(categoryId);

        // Assert
        verify(categoryService, times(1)).deleteCategory(categoryId);
        assertEquals(Boolean.FALSE, result);
    }

}
