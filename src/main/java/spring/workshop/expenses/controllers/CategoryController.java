package spring.workshop.expenses.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import spring.workshop.expenses.entities.Category;
import spring.workshop.expenses.services.CategoryService;

@RestController
@RequestMapping(path = "/categories")
public class CategoryController {

  @Autowired
  private CategoryService categoryService;

  @GetMapping()
  public List<Category> getAllCategories() {
    return categoryService.findAll();
  }

  @GetMapping(path = "/{id}")
  public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
    return ResponseEntity.ok().body(categoryService.findById(id));
  }
}
