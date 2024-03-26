package spring.workshop.expenses.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import spring.workshop.expenses.dto.EmployeeDetailsDTO;
import spring.workshop.expenses.mapper.EmployeeDetailsMapper;
import spring.workshop.expenses.useCases.ReassignEmployeeUc;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {

  @Autowired
  private ReassignEmployeeUc reassignEmployeeUc;

  @Autowired
  private EmployeeDetailsMapper employeeDetailsMapper;

  // Method for reassigning an employee
  @PutMapping(path = "/{id}")
  @ResponseStatus(HttpStatus.OK)
  public EmployeeDetailsDTO reassignEmployee(@PathVariable("id") Long employeeId,
      @RequestParam("superior-id") Long superiorId) {
    return employeeDetailsMapper.toDto(reassignEmployeeUc.reassignEmployee(employeeId, superiorId));
  }

}
