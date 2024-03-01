package spring.workshop.expenses.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import spring.workshop.expenses.entities.Employee;
import spring.workshop.expenses.useCases.ReassignEmployeeUc;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {

  @Autowired
  private ReassignEmployeeUc reassignEmployeeUc;

  // Method for reassigning an employee
  @PutMapping(path = "/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Employee reassignEmployee(@PathVariable("id") Long employeeId,
      @RequestParam("superior_id") Long superiorId) {

    Employee reassignedEmployee = reassignEmployeeUc.reassignEmployee(employeeId, superiorId);

    return reassignedEmployee;
  }

}
