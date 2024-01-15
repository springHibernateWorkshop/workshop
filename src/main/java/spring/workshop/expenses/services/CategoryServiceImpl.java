package spring.workshop.expenses.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import spring.workshop.expenses.entities.Category;
import spring.workshop.expenses.repos.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;
    private static final Logger LOG = LoggerFactory.getLogger(CategoryServiceImpl.class);

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category updateCategory(Category category) {
        return categoryRepository.findById(category.getId()).map(c -> {
            c.setName(category.getName());
            LOG.info("Category {} updated", category.getName());
            return categoryRepository.save(c);
        }).orElseThrow(() -> new IllegalArgumentException("No such category with id " + category.getId()));
    }

    @Override
    public Boolean deleteCategory(Integer id) {
        return categoryRepository.findById(id).map(c -> {
            categoryRepository.delete(c);
            LOG.info("Category {} deleted", c.getName());
            return true;
        }).orElseGet(() -> {
            LOG.warn("Category ID {} not found for delete", id);
            return false;
        });

    }

    @Override
    public Category addCategory(Category category) {
        Category newCategory = categoryRepository.save(category);
        LOG.info("Category {} added", category.getName());
        return newCategory;
    }

    @Override
    public List<Category> findAll() {
        List<Category> categories = categoryRepository.findByOrderById();
        // categories.sort(Comparator.comparing(Category::getId));
        return categories;
    }

    @Override
    public Category findById(Integer id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (!category.isPresent())
            throw new IllegalArgumentException("No such category with id " + id);

        return category.get();
    }
}
