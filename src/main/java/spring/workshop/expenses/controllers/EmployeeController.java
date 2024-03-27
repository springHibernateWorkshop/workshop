package spring.workshop.expenses.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import spring.workshop.expenses.dto.EmployeeDetailsDTO;
import spring.workshop.expenses.mapper.EmployeeDetailsMapper;
import spring.workshop.expenses.services.EmployeeService;
import spring.workshop.expenses.useCases.ReassignEmployeeUc;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {

  @Autowired
  private ReassignEmployeeUc reassignEmployeeUc;

  @Autowired
  private EmployeeDetailsMapper employeeDetailsMapper;

  @Autowired
  private EmployeeService employeeService;

  // Method for reassigning an employee
  @PutMapping(path = "/{id}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(summary = "Reassign an employee", description = "Reassigns an employee to a new superior")
  public EmployeeDetailsDTO reassignEmployee(
      @PathVariable("id") @Parameter(description = "ID of the employee to be reassigned") Long employeeId,
      @RequestParam("superior-id") @Parameter(description = "ID of the new superior") Long superiorId) {
    return employeeDetailsMapper.toDto(reassignEmployeeUc.reassignEmployee(employeeId, superiorId));
  }

  // Method for getting all employees
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  @Operation(summary = "Get all employees", description = "Fetches all employees from the database")
  public List<EmployeeDetailsDTO> getEmployees() {
    return employeeDetailsMapper.toDto(employeeService.getAllEmployees());
  }

  // Method for getting all employees
  @GetMapping(path = "/{id}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(summary = "Get an employee", description = "Fetche the employee with the given id from the database")
  public EmployeeDetailsDTO getEmployeeById(
      @PathVariable("id") @Parameter(description = "ID of the employee") Long employeeId) {
    return employeeDetailsMapper.toDto(employeeService.getEmployeeById(employeeId));
  }

}
