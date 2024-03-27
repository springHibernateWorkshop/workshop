package spring.workshop.expenses.mapper;

import org.mapstruct.Mapper;

import spring.workshop.expenses.dto.EmployeeDetailsDTO;
import spring.workshop.expenses.entities.Employee;

@Mapper(componentModel = "spring")
public interface EmployeeDetailsMapper extends AbstractMapper<Employee, EmployeeDetailsDTO> {
}
