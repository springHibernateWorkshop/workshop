package spring.workshop.expenses.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import spring.workshop.expenses.dto.EmployeeDTO;
import spring.workshop.expenses.dto.ExpenseDTO;
import spring.workshop.expenses.entities.Employee;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    ExpenseDTO toDto(Employee employee);

    Employee toEntity(EmployeeDTO employeeDTO);

    List<EmployeeDTO> toDto(List<Employee> employees);

    List<Employee> toEntity(List<EmployeeDTO> employeeDTOs);
}
