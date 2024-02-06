package spring.workshop.expenses.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import spring.workshop.expenses.entities.Employee;
import spring.workshop.expenses.entities.Superior;
// import spring.workshop.expenses.exception.ForbiddenResourceException;
import spring.workshop.expenses.services.EmployeeService;
// import spring.workshop.expenses.services.SuperiorService;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {

  private EmployeeService employeeService;

  @Autowired
  public EmployeeController(EmployeeService employeeService) {
    this.employeeService = employeeService;
  }

  // private SuperiorService superiorService;

  // @Autowired
  // public EmployeeController(SuperiorService superiorService) {
  // this.superiorService = superiorService;
  // }

  // Method for reassigning an employee
  @PutMapping(path = "/{id}")
  public ResponseEntity<Employee> reassignEmployee(@PathVariable("id") Long employeeId,
      @RequestParam("superior_id") Long superiorId) {
    // Mock for Superior, change to method for getting superior by superior_id
    Superior superior = new Superior(superiorId, "Superior");

    // Get Superior by superior_id
    // Superior superior = superiorService.getSuperiorById(superiorId);

    // Check if Superior.user_id != Null
    // if (!superior.getUser().isPresent)
    // throw new ForbiddenResourceException("User for Superior with id = " +
    // superiorId + "does not exist.");

    // Get Employee by id
    Employee updatedEmployee = employeeService.getEmployeeById(employeeId);

    // Update Superior of Employee and save updated Employee
    updatedEmployee.setSuperior(superior);
    Employee savedEmployee = employeeService.updateEmployee(updatedEmployee);

    // Return updated and saved Employee
    return ResponseEntity.status(HttpStatus.OK)
        .body(savedEmployee);
  }

}
