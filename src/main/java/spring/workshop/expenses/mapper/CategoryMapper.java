package spring.workshop.expenses.mapper;

import org.mapstruct.Mapper;

import spring.workshop.expenses.dto.CategoryDTO;
import spring.workshop.expenses.entities.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper extends AbstractMapper<Category, CategoryDTO> {
}
