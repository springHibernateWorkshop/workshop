package spring.workshop.expenses.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import spring.workshop.expenses.entities.Category;
import spring.workshop.expenses.services.CategoryService;

@RestController
@RequestMapping(path = "/categories")
public class CategoryController {

  @Autowired
  private CategoryService categoryService;

  @GetMapping()
  @Operation(summary = "Get all categories", description = "Fetches all categories from the database")
  public List<Category> getAllCategories() {
    return categoryService.findAll();
  }

  @GetMapping(path = "/{id}")
  @Operation(summary = "Get a category", description = "Fetche the category with the given id from the database")
  public ResponseEntity<Category> getCategoryById(
      @PathVariable @Parameter(description = "ID of the category") Long id) {
    return ResponseEntity.ok().body(categoryService.findById(id));
  }
}
