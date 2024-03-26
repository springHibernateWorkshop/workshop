package spring.workshop.expenses.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import spring.workshop.expenses.dto.ExpenseDTO;
import spring.workshop.expenses.entities.Expense;

@Mapper(componentModel = "spring")
public interface ExpenseMapper {

    ExpenseDTO toDto(Expense expense);

    Expense toEntity(ExpenseDTO expenseDTO);

    List<ExpenseDTO> toDto(List<Expense> expenses);

    List<Expense> toEntity(List<ExpenseDTO> expenseDTOs);
}
