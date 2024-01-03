package spring.workshop.expenses.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import spring.workshop.expenses.entities.Category;
import spring.workshop.expenses.services.CategoryService;

@RestController
@RequestMapping(path = "/category")
public class CategoryController {

  private CategoryService categoryService;
  private static final Logger LOG = LoggerFactory.getLogger(CategoryController.class);

  @Autowired
  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @PostMapping()
  public ResponseEntity<Void> addNewCategory(@RequestBody @NonNull Category category) {
    Category newCategory = categoryService.addCategory(category);

    return ResponseEntity
        .created(
            ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(newCategory.getId())
                .toUri())
        .build();
  }

  @PutMapping(path = "/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void updateCategory(@PathVariable Integer id, @RequestBody Category category) {
    categoryService.updateCategory(id, category);
  }

  @DeleteMapping(path = "/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT) // 204
  public void deleteCategory(@PathVariable Integer id) {
    categoryService.deleteCategory(id);

  }

  @GetMapping(path = "/all")
  public List<Category> getAllCategories() {
    return categoryService.findAll();
  }

  @GetMapping(path = "/{id}")
  public ResponseEntity<Category> getCategoryById(@PathVariable Integer id) {
    return ResponseEntity.ok().body(categoryService.findById(id));
  }

  /**
   * Maps UnsupportedOperationException to a 501 Not Implemented HTTP status
   * code.
   */
  @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
  @ExceptionHandler({ UnsupportedOperationException.class })
  public void handleUnabletoReallocate(Exception ex) {
    LOG.error("Exception is: ", ex);
    // just return empty 501
  }

  /**
   * Maps IllegalArgumentExceptions to a 404 Not Found HTTP status code.
   */
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(IllegalArgumentException.class)
  public void handleNotFound(Exception ex) {
    LOG.error("Exception is: ", ex);
    // return empty 404
  }

  /**
   * Maps DataIntegrityViolationException to a 409 Conflict HTTP status code.
   */
  @ResponseStatus(HttpStatus.CONFLICT)
  @ExceptionHandler({ DataIntegrityViolationException.class })
  public void handleAlreadyExists(Exception ex) {
    LOG.error("Exception is: ", ex);
    // return empty 409
  }
}
