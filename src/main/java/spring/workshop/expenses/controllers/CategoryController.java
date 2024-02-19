package spring.workshop.expenses.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping(path = "/categories")
public class CategoryController {

  private CategoryService categoryService;
  private static final Logger LOG = LoggerFactory.getLogger(CategoryController.class);

  @Autowired
  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @PostMapping()
  public ResponseEntity<Category> addNewCategory(@RequestBody Category category) {
    Category newCategory = categoryService.addCategory(category);

    return ResponseEntity
        .created(
            ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(newCategory.getId())
                .toUri())
        .body(newCategory);
  }

  @PutMapping()
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Category updateCategory(@RequestBody Category category) {
    return categoryService.updateCategory(category);
  }

  @DeleteMapping(path = "/{id}")
  public Boolean deleteCategory(@PathVariable Long id) {
    return categoryService.deleteCategory(id);

  }

  @GetMapping()
  public List<Category> getAllCategories() {
    return categoryService.findAll();
  }

  @GetMapping(path = "/{id}")
  public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
    return ResponseEntity.ok().body(categoryService.findById(id));
  }
}
