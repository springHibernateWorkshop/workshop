package spring.workshop.expenses.unit.Category;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
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

import spring.workshop.expenses.controllers.CategoryController;
import spring.workshop.expenses.entities.Category;
import spring.workshop.expenses.services.CategoryService;

/**
 * This class contains unit tests for the CategoryController class.
 */
@WebMvcTest(CategoryController.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class CategoryControllerSliceTest {

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private CategoryController sut;

    @BeforeEach
    public void setUp() throws Exception {
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

}
