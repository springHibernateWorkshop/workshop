package spring.workshop.expenses.services;

import java.util.List;

import spring.workshop.expenses.entities.Category;

public interface CategoryService {

    void updateCategory(Integer id, Category category);

    void deleteCategory(Integer id);

    Category addCategory(Category category);

    List<Category> findAll();

    Category findById(Integer id);

}