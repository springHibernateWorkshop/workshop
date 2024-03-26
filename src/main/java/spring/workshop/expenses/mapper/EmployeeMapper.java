package spring.workshop.expenses.mapper;

import org.mapstruct.Mapper;

import spring.workshop.expenses.dto.EmployeeDTO;
import spring.workshop.expenses.entities.Employee;

@Mapper(componentModel = "spring")
public interface EmployeeMapper extends AbstractMapper<Employee, EmployeeDTO> {
}
