package spring.workshop.expenses.mapper;

import org.mapstruct.Mapper;

import spring.workshop.expenses.dto.ExpenseDTO;
import spring.workshop.expenses.entities.Expense;

@Mapper(componentModel = "spring")
public interface ExpenseMapper extends AbstractMapper<Expense, ExpenseDTO> {
}
