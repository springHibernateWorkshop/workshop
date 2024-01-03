package spring.workshop.expenses.services;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import spring.workshop.expenses.entities.Category;
import spring.workshop.expenses.repos.CategoryRepository;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;
    private static final Logger LOG = LoggerFactory.getLogger(CategoryService.class);

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void updateCategory(Integer id, Category category) {
        Optional<Category> oldCategory = categoryRepository.findById(id);
        if (oldCategory.isPresent()) {
            Category updatedCategory = oldCategory.get();
            updatedCategory.setName(category.getName());
            categoryRepository.save(updatedCategory);
            LOG.info("Category {} updated", category.getName());
        } else {
            LOG.warn("Category {} not found for update", category.getName());
            throw new IllegalArgumentException("No such category with id " + id);
        }
    }

    public void deleteCategory(Integer id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            categoryRepository.delete(category.get());
            LOG.info("Category {} deleted", category.get().getName());
        } else {
            LOG.warn("Category ID {} not found for delete", id);
            throw new IllegalArgumentException("No such category with id " + id);
        }
    }

    public Category addCategory(Category category) {
        Category newCategory = categoryRepository.save(category);
        LOG.info("Category {} added", category.getName());
        return newCategory;
    }

    public List<Category> findAll() {
        List<Category> categories = categoryRepository.findAll();
        categories.sort(Comparator.comparing(Category::getId));
        return categories;
    }

    public Category findById(Integer id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (!category.isPresent())
            throw new IllegalArgumentException("No such category with id " + id);

        return category.get();
    }
}
